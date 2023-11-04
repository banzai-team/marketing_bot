package banz.ai.marketing.bot.modelinterceptor.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {

})
@Testcontainers
class InterceptionControllerTest {
    static String RMQ_IMAGE_NAME = "rabbitmq:3";
    static String MOCKSERVER_IMAGE_NAME = "mockserver/mockserver:5.15.0";
    static MockServerContainer mockServerContainer = new MockServerContainer(DockerImageName.parse(MOCKSERVER_IMAGE_NAME));
    @Container
    static RabbitMQContainer container = new RabbitMQContainer(RMQ_IMAGE_NAME);
    static MockServerClient mockServerClient;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        mockServerContainer.start();
        container.start();
        mockServerClient = new MockServerClient(
                mockServerContainer.getHost(),
                mockServerContainer.getServerPort()
        );
        registry.add("model.url", mockServerContainer::getEndpoint);
        registry.add("spring.rabbitmq.host", container::getHost);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
        registry.add("spring.rabbitmq.username", container::getAdminUsername);
        registry.add("spring.rabbitmq.password", container::getAdminPassword);
    }

    @BeforeEach
    void setUp() {
        mockServerClient.reset();
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldInvokeModelThenSendMessageToMqAndReturnCorrectResponse() throws Exception {
        mockServerClient
                .when(request().withMethod("POST").withPath("/base_process"))
                .respond(
                        response().withStatusCode(200)
                                .withBody(json(
                                        """
                                        {
                                            "offerPurchase": true,
                                            "dialogPositivity": 5,
                                            "stopThemes": [
                                                "foo", "bar"
                                            ]
                                        }
                                        """
                                ))
                );

        this.mockMvc.perform(post("/api/model-interceptor/invoke")
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .content(json(
                    """
                        {
                            "dialogId": 9,
                            "messages": [
                                "bam",
                                "bim"
                            ],
                            "isOperator": false,
                            "text": "Smth"
                        }
                    """
                ).toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}