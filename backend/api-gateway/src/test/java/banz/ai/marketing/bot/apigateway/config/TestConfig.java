package banz.ai.marketing.bot.apigateway.config;

import com.sun.net.httpserver.HttpServer;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TestConfig {

    @Bean
    public HttpServer mockServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(0),0);
        server.createContext("/api/jean", (exchange) -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            byte[] response = "{}".getBytes("UTF-8");
            exchange.sendResponseHeaders(200,response.length);
            exchange.getResponseBody().write(response);
        });
        server.createContext("/juuu/li", (exchange) -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            byte[] response = "{}".getBytes("UTF-8");
            exchange.sendResponseHeaders(200,response.length);
            exchange.getResponseBody().write(response);
        });

        server.setExecutor(null);
        server.start();
        int mockServerPort = server.getAddress().getPort();
        System.out.println("Mock server port: " + mockServerPort);
        return server;
    }

    @Bean
    public RouteLocator scrubSsnRoute(
            RouteLocatorBuilder builder,
            HttpServer server) {
        int mockServerPort = server.getAddress().getPort();
        return builder.routes()
                .route("model",
                        r -> r.path("/api/jean")
                                .uri("http://localhost:" + mockServerPort ))
                .route("juu",
                        r -> r.path("/juuu/li")
                                .uri("http://localhost:" + mockServerPort ))
                .build();
    }
}
