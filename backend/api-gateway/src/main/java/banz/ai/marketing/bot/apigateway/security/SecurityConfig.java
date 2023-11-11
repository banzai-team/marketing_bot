package banz.ai.marketing.bot.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Bean
    @Order(1)
    SecurityWebFilterChain permitAllWebFilterChain(ServerHttpSecurity http) {
        return http
                .securityMatcher(pathMatchers(
                        "/model-interceptor/v3/api-docs",
                        "/model-behavior/v3/api-docs",
                        "/actuator/**",
                        "/permitall/**",
                        "/api/model/query/**"
                ))
                .authorizeExchange(c -> c.pathMatchers(
                        "/model-interceptor/v3/api-docs",
                        "/model-behavior/v3/api-docs",
                        "/actuator/**",
                        "/permitall/**",
                        "/api/model/query/**"
                ).permitAll())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(s ->
                        s.configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("*"));
                            configuration.setAllowedMethods(List.of("*"));
                            configuration.setAllowedHeaders(List.of("*"));
                            return configuration;
                        })
                )
                .build();
    }

    @Bean
    @Order(2)
    SecurityWebFilterChain basicAuthWebFilterChain(ServerHttpSecurity http) {
        return http
                .securityMatcher(c -> !c.getRequest().getMethod().matches("OPTIONS")
                        && c.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)
                        && String.valueOf(c.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).toLowerCase().contains("basic") ?
                        ServerWebExchangeMatcher.MatchResult.match() : ServerWebExchangeMatcher.MatchResult.notMatch()
                )
                .authorizeExchange(c -> c.pathMatchers("/api/feedback/**", "/api/model/**").authenticated())
                .httpBasic(c -> c.authenticationManager(basicAuthenticationManager()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(s ->
                        s.configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("*"));
                            configuration.setAllowedMethods(List.of("*"));
                            configuration.setAllowedHeaders(List.of("*"));
                            return configuration;
                        })
                )
                .build();
    }

    @Bean
    @Order(3)
    SecurityWebFilterChain oAuthWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(authorize -> authorize.anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(s ->
                        s.configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("*"));
                            configuration.setAllowedMethods(List.of("*"));
                            configuration.setAllowedHeaders(List.of("*"));
                            return configuration;
                        })
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager basicAuthenticationManager() {
        return new KeycloakPasswordFlowAuthenticationManager(issuerUri, clientId, clientSecret);
    }

}
