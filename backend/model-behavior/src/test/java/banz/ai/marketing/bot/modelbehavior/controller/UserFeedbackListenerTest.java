package banz.ai.marketing.bot.modelbehavior.controller;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
})
@Testcontainers
class UserFeedbackListenerTest {

    static String RMQ_IMAGE_NAME = "rabbitmq:3-management";
    static String MOCKSERVER_IMAGE_NAME = "mockserver/mockserver:5.15.0";
    static String POSTGRES_IMAGE_NAME = "postgres:15-alpine";
    static String DATABASE_NAME = "behavior_db";
    static String BEHAVIOR_QUEUE = "behavior-queue";
    static String FEEDBACK_QUEUE = "behavior-queue";
    @Container
    static MockServerContainer mockServerContainer = new MockServerContainer(DockerImageName.parse(MOCKSERVER_IMAGE_NAME));
    @Container
    static RabbitMQContainer rmqContainer = new RabbitMQContainer(DockerImageName.parse(RMQ_IMAGE_NAME))
            .withQueue(BEHAVIOR_QUEUE)
            .withQueue(FEEDBACK_QUEUE);
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE_NAME))
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);
    static MockServerClient mockServerClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        rmqContainer.waitingFor(Wait.forHttp("/"));
        mockServerContainer.waitingFor(Wait.forHttp("/"));
        postgreSQLContainer.waitingFor(Wait.defaultWaitStrategy());
        mockServerClient = new MockServerClient(
                mockServerContainer.getHost(),
                mockServerContainer.getServerPort()
        );
        registry.add("model.url", mockServerContainer::getEndpoint);
        registry.add("spring.rabbitmq.host", rmqContainer::getHost);
        registry.add("spring.rabbitmq.port", rmqContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rmqContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rmqContainer::getAdminPassword);
        registry.add("queues.behavior", () -> BEHAVIOR_QUEUE);
        registry.add("queues.feedback", () -> FEEDBACK_QUEUE);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
        registry.add("spring.primary.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.primary.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.primary.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.primary.datasource.password", () -> postgreSQLContainer.getPassword());
        registry.add("spring.replica.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.replica.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.replica.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.replica.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    public void shouldReceiveNegativeFeedbackAndInvokeModelLearning() {


    }

    @Test
    public void shouldReceivePositiveFeedbackWithoutModelLearningInvocation() {

    }

}