package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.modelbehavior.AbstractIntegrationTest;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"classpath:init-query-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:trucate-init-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class QueryControllerTest extends AbstractIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  void shouldReturnListing() throws Exception {
    var response = this.mockMvc.perform(get("/api/model/query/model-request?page=0&size=10")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("$.content[1].request.text").value("hello"),
                    jsonPath("$.content[1].request.performedAt").isNotEmpty(),
                    jsonPath("$.content[1].request.messages").isArray(),
                    jsonPath("$.content[1].request.messages[0]").value("Hello"),
                    jsonPath("$.content[1].request.messages[1]").value("What is your name?"),
                    jsonPath("$.content[1].request.messages[2]").value("John"),
                    jsonPath("$.content[1].request.response.offerPurchase").value(true),
                    jsonPath("$.content[1].request.response.dialogEvaluation").value(0.5),
                    jsonPath("$.content[1].request.response.stopTopics[0]").value("f*ck"),
                    jsonPath("$.content[1].request.response.feedback").value(-4)

            )
            .andReturn();
  }

  @Test
  void shouldGetRequestById() throws Exception {
    this.mockMvc.perform(get("/api/model/query/model-request/c6c0206e-7322-4293-8727-cfafd4edc977")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("$.request.text").value("hello"),
                    jsonPath("$.request.performedAt").isNotEmpty(),
                    jsonPath("$.request.messages").isArray(),
                    jsonPath("$.request.messages[0]").value("Hello"),
                    jsonPath("$.request.messages[1]").value("What is your name?"),
                    jsonPath("$.request.messages[2]").value("John"),
                    jsonPath("$.request.response.offerPurchase").value(true),
                    jsonPath("$.request.response.dialogEvaluation").value(0.5),
                    jsonPath("$.request.response.feedback").value(-4)

            );
  }

  @Test
  void shouldFindByDialogId() throws Exception {
    this.mockMvc.perform(get("/api/model/query/model-request?page=0&size=10&dialogId=1")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("$.content[0].request.text").value("hello"),
                    jsonPath("$.content[0].request.performedAt").value("2023-11-10T11:30:00.000+00:00"),
                    jsonPath("$.content[0].request.messages").isArray(),
                    jsonPath("$.content[0].request.messages[0]").value("Hello"),
                    jsonPath("$.content[0].request.messages[1]").value("What is your name?"),
                    jsonPath("$.content[0].request.messages[2]").value("John"),
                    jsonPath("$.content[0].request.response.offerPurchase").value(true),
                    jsonPath("$.content[0].request.response.dialogEvaluation").value(0.5),
                    jsonPath("$.content[0].request.response.feedback").value(-4)
            );
  }

  @Test
  void shouldOrderByRequestDates() throws Exception {
   this.mockMvc.perform(get("/api/model/query/model-request?page=0&size=10&sortBy=performedAt&sort=asc")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("$.content[0].request.performedAt").value("2023-11-09T12:30:00.000+00:00"),
                    jsonPath("$.content[1].request.performedAt").value("2023-11-10T11:30:00.000+00:00"),
                    jsonPath("$.content[2].request.performedAt").value("2023-11-11T12:00:00.000+00:00")
            );
    this.mockMvc.perform(get("/api/model/query/model-request?page=0&size=10&sortBy=performedAt&sort=desc")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath("$.content[2].request.performedAt").value("2023-11-09T12:30:00.000+00:00"),
                    jsonPath("$.content[1].request.performedAt").value("2023-11-10T11:30:00.000+00:00"),
                    jsonPath("$.content[0].request.performedAt").value("2023-11-11T12:00:00.000+00:00")
            );
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
          .withDatabaseName(DATABASE_NAME)
          ;
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
