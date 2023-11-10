package banz.ai.marketing.bot.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

//    @Bean
//    SecurityWebFilterChain customSecurityFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager) {
//        http
//                .authorizeExchange(c ->
//                        c
//                                .pathMatchers("/api/model/**").authenticated()
//                                .pathMatchers("api/feedback/**").authenticated()
//                                .anyExchange().permitAll()
//                )
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .cors(s ->
//                    s.configurationSource(request -> {
//                        CorsConfiguration configuration = new CorsConfiguration();
//                        configuration.setAllowedOrigins(List.of("*"));
//                        configuration.setAllowedMethods(List.of("*"));
//                        configuration.setAllowedHeaders(List.of("*"));
//                        return configuration;
//                    })
//                )
//                .authenticationManager(authenticationManager)
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new MapReactiveUserDetailsService(User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .authorities("ROLE_USER")
                .build());
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        var m = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        m.setPasswordEncoder(passwordEncoder);
        return m;
    }

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
//
//    @Bean
//    @Order(2)
//    SecurityWebFilterChain basicAuthWebFilterChain(ServerHttpSecurity http,
//                                                   ReactiveUserDetailsService userDetailsService,
//                                                   PasswordEncoder passwordEncoder) {
//        return http
//                .securityMatcher(pathMatchers("api/feedback/**"))
//                .httpBasic(Customizer.withDefaults())
//                .authenticationManager(authenticationManager(userDetailsService, passwordEncoder))
//                .build();
//    }

    @Bean
    @Order(3)
    SecurityWebFilterChain oAuthWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(it -> it.pathMatchers("api/feedback/**", "/api/model/**").authenticated().and().oauth2Login(Customizer.withDefaults()))
                .build();
    }
}
