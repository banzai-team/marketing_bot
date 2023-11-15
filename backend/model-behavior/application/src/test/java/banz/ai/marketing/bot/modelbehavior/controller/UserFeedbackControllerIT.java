package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.modelbehavior.AbstractIntegrationTest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelResponse;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserFeedbackControllerIT extends AbstractIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void shouldCreateFeedbackRecordAndPutMessageInRmq() throws Exception {
    var entityManager = entityManagerFactory.createEntityManager();
    var req = new ModelRequest();
    var resp = new ModelResponse();
    resp.setFeedback(0);
    req.setModelResponse(resp);
    req.setPerformedAt(new Date());
    var dialog = new Dialog();
    dialog.setId(75L);
    dialog.setCreatedAt(new Date());
    dialog.addModelRequest(req);
    entityManager.getTransaction().begin();
    entityManager.persist(dialog);
    entityManager.persist(req);
    entityManager.flush();
    entityManager.getTransaction().commit();

    this.mockMvc.perform(post("/api/feedback")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .content(
                            """
                                        {
                                           "userId": 99,
                                           "modelResponseId": %d,
                                           "isCorrect": false
                                        }
                                    """.formatted(resp.getId())))
            .andDo(print())
            .andExpect(status().isCreated());

    // TODO check db
//    Awaitility.await()
//            .atMost(5, TimeUnit.SECONDS).until(() -> {
//              var msg = rabbitTemplate.receive(FEEDBACK_QUEUE_POST);
//              if (Objects.isNull(msg)) {
//                return false;
//              }
//              var msgBody = new String(msg.getBody(), msg.getMessageProperties().getContentEncoding());
//              var mapper = new ObjectMapper();
//              var dto = mapper.readValue(msgBody, UserFeedbackToApplyDTO.class);
//              Assertions.assertEquals(99, dto.getUserId());
//              Assertions.assertFalse(dto.isCorrect());
//              Assertions.assertEquals(888, dto.getModelResponseId());
//
//              return true;
//            });
  }

  static String DATABASE_NAME = "behavior_db";
  static String BEHAVIOR_QUEUE = "behavior-queue";
  static String FEEDBACK_QUEUE_POST = "feedback-queue-post";
  static String FEEDBACK_QUEUE_RESPONSE = "feedback-queue-response";
  @Container
  static MockServerContainer mockServerContainer = new MockServerContainer(DockerImageName.parse(MOCKSERVER_IMAGE_NAME));
  @Container
  static RabbitMQContainer rmqContainer = new RabbitMQContainer(DockerImageName.parse(RMQ_IMAGE_NAME))
          .withQueue(BEHAVIOR_QUEUE)
          .withQueue(FEEDBACK_QUEUE_RESPONSE)
          .withQueue(FEEDBACK_QUEUE_POST);
  @Container
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE_NAME))
          .withReuse(true)
          .withDatabaseName(DATABASE_NAME);
  static MockServerClient mockServerClient;

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
    registry.add("queues.feedback-post", () -> FEEDBACK_QUEUE_POST);
    registry.add("queues.feedback-response", () -> FEEDBACK_QUEUE_RESPONSE);
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
}
