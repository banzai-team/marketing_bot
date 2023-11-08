package banz.ai.marketing.bot.apigateway;

import banz.ai.marketing.bot.apigateway.config.TestConfig;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.TestSocketUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"management.endpoint.gateway.enabled=true",
		"management.server.port=${test.port}"
})
@Import(TestConfig.class)
class ApiGatewayApplicationTests {
	protected static int managementPort;
	protected String baseUri;
	@Autowired
	private WebTestClient webClient;
	@LocalServerPort
	protected int port = 0;
	int mockServerPort;
	@BeforeAll
	public static void beforeClass() {
		managementPort = TestSocketUtils.findAvailableTcpPort();
		System.setProperty("test.port", String.valueOf(managementPort));
	}

	@AfterAll
	public static void afterClass() {
		System.clearProperty("test.port");
	}

	@BeforeEach
	public void setup() {
		baseUri = "http://localhost:" + port;
		this.webClient = WebTestClient.bindToServer().responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
	}

	@Test
	void contextLoads() {
		webClient.get().uri("/api/jean").exchange().expectStatus().isOk();
	}
	@Test
	void contextLoads2() {
		webClient.get().uri("/juuu/li").exchange().expectStatus().isOk();
	}


	@Test
	void t1() {
		String baseUri = "http://localhost:50002";
		webClient = WebTestClient.bindToServer()
				.baseUrl(baseUri)
				.responseTimeout(Duration.ofSeconds(2))
				.build();
		webClient
				.post().uri("/api/model")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.headers.Hello").isEqualTo("World");
	}

}
