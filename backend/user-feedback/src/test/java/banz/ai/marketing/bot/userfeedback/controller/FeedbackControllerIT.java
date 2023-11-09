package banz.ai.marketing.bot.userfeedback.controller;

import banz.ai.marketing.bot.commons.mq.UserFeedbackToApplyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedbackControllerIT {

  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void shouldCreateFeedbackRecordAndPutMessageInRmq() throws Exception {

    this.mockMvc.perform(post("/api/feedback")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .content(
                            """
                                        {
                                           "userId": 99,
                                           "modelRequestId": 888,
                                           "isCorrect": false
                                        }
                                    """))
            .andDo(print())
            .andExpect(status().isCreated());

    Awaitility.await()
            .atMost(5, TimeUnit.SECONDS).until(() -> {
                var msg = rabbitTemplate.receive(FEEDBACK_QUEUE);
                if (Objects.isNull(msg)) {
                  return false;
                }
                var msgBody = new String(msg.getBody(), msg.getMessageProperties().getContentEncoding());
                var mapper = new ObjectMapper();
                var dto = mapper.readValue(msgBody, UserFeedbackToApplyDTO.class);
                Assertions.assertEquals(99, dto.getUserId());
                Assertions.assertFalse(dto.isCorrect());
                Assertions.assertEquals(888, dto.getModelRequestId());

                return true;
            });
  }

  public static final String RMQ_IMAGE_NAME = "rabbitmq:3-management";
  public static final String POSTGRES_IMAGE_NAME = "postgres:15-alpine";

  private static String FEEDBACK_QUEUE = "feedback-queue";
  private static String DATABASE_NAME = "feedback_db";

  @Container
  static RabbitMQContainer rmqContainer = new RabbitMQContainer(DockerImageName.parse(RMQ_IMAGE_NAME))
          .withQueue(FEEDBACK_QUEUE);
  @Container
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE_NAME))
          .withReuse(true)
          .withDatabaseName(DATABASE_NAME);

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    rmqContainer.waitingFor(Wait.forHttp("/"));
    postgreSQLContainer.waitingFor(Wait.defaultWaitStrategy());
    registry.add("spring.rabbitmq.host", rmqContainer::getHost);
    registry.add("spring.rabbitmq.port", rmqContainer::getAmqpPort);
    registry.add("spring.rabbitmq.username", rmqContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rmqContainer::getAdminPassword);
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
    registry.add("spring.rabbitmq.template.routing-key", () -> FEEDBACK_QUEUE);
  }
}
