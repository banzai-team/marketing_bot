package banz.ai.marketing.bot.modelbehavior.controller;

import banz.ai.marketing.bot.commons.ModelBehaviorDTO;
import banz.ai.marketing.bot.commons.ModelRequestDTO;
import banz.ai.marketing.bot.commons.ModelResponseDTO;
import banz.ai.marketing.bot.modelbehavior.AbstractIntegrationTest;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.DialogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class ModelBehaviorListenerIT extends AbstractIntegrationTest {
  static String DATABASE_NAME = "behavior_db";
  static String BEHAVIOR_QUEUE = "behavior-queue";
  static String FEEDBACK_QUEUE = "feedback-queue";
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

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private DialogRepository dialogRepository;

  @Test
  @Transactional()
  void shouldPersistModelBehaviorForNewDialog() {
    var dialogId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    var messages = List.of("Я оформила кредит на покупку авто\nМне нужна ссылка что бы документы предоставить", "Ссылку пришлите для предоставление документов", "Напишите, о чем хотите узнать, и я постараюсь сразу ответить.");
    var stopTopics = List.of("Stop it, get some help", "Enough", "Thank you");
    var capturedAt = new Date();
    var mb = ModelBehaviorDTO.builder()
            .modelRequest(ModelRequestDTO.builder()
                    .dialogId(dialogId)
                    .isOperator(false)
                    .messages(messages)
                    .text("Some text")
                    .build())
            .modelResponse(ModelResponseDTO.builder()
                    .offerPurchase(true)
                    .dialogEvaluation(-3)
                    .stopTopics(stopTopics)
                    .build())
            .capturedAt(capturedAt)
            .build();

    rabbitTemplate.convertAndSend(BEHAVIOR_QUEUE, mb);

    Awaitility.await()
            .ignoreExceptions()
            .atMost(5, TimeUnit.SECONDS)
            .with()
            .pollInterval(Duration.ofSeconds(1))
            .until(() -> dialogRepository.existsById(dialogId));
    var dialog = dialogRepository.getReferenceById(dialogId);
    Assertions.assertEquals(1, dialog.getModelRequests().size());
    Assertions.assertEquals(3, dialog.getModelRequests().get(0).getMessages().size());
    Assertions.assertEquals(3, dialog.getModelRequests().get(0).getModelResponse().getStopTopics().size());
    Assertions.assertEquals(-3, dialog.getModelRequests().get(0).getModelResponse().getDialogEvaluation());
  }
}
