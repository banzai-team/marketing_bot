package banz.ai.marketing.bot.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Bean
    @Order(1)
    SecurityWebFilterChain permitAllWebFilterChain(ServerHttpSecurity http) {
        return http
                .securityMatcher(pathMatchers("/permitall/**"))
                .authorizeExchange(c -> c .pathMatchers("/permitall/**").permitAll())
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
                .securityMatcher(c -> c.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)
                        && String.valueOf(c.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).toLowerCase().startsWith("basic") ?
                        ServerWebExchangeMatcher.MatchResult.match() : ServerWebExchangeMatcher.MatchResult.notMatch()
                )
                .authorizeExchange(c -> c.pathMatchers("api/feedback/**", "/api/model/**").authenticated())
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
        return http
                .authorizeExchange(it -> it.pathMatchers("api/feedback/**", "/api/model/**").authenticated().and().oauth2Login(Customizer.withDefaults()))
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
    public ReactiveAuthenticationManager basicAuthenticationManager() {
        return new KeycloakPasswordFlowAuthenticationManager(issuerUri, clientId, clientSecret);
    }

}
