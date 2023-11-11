package banz.ai.marketing.bot.apigateway.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

public class KeycloakPasswordFlowAuthenticationManager implements ReactiveAuthenticationManager {
    private static Logger log = LoggerFactory.getLogger(KeycloakPasswordFlowAuthenticationManager.class);
    private final String issuerUri;
    private final String clientId;
    private final String clientSecret;
    private WebClient webClient;

    public KeycloakPasswordFlowAuthenticationManager(
            String issuerUri,
            String clientId,
            String clientSecret
            ) {
        this.issuerUri = issuerUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        try {
            return getWebClient().post()
                    .body(
                            BodyInserters.fromFormData("grant_type", "password")
                                    .with("client_id", clientId)
                                    .with("client_secret", clientSecret)
                                    .with("username", authentication.getName())
                                    .with("password", authentication.getCredentials().toString())
                    )
                    .retrieve()
                    .bodyToMono(TokenResponseDto.class)
                    .map(t -> (Authentication) UsernamePasswordAuthenticationToken.authenticated(User.builder()
                            .username("test")
                            .password("test")
                            .authorities("USER")
                            .build(), "", List.of(new SimpleGrantedAuthority("user"))))
                    .onErrorReturn(authentication)
            ;
        } catch (Throwable e) {
            throw new AuthenticationFailureException(e);
        }
    }

    private WebClient getWebClient() {
        if (Objects.isNull(webClient)) {
            final var builder = WebClient.builder()
                    .baseUrl("%s/protocol/openid-connect/token".formatted(issuerUri));
            if (StringUtils.hasText(clientSecret)) {
                builder.filter(ExchangeFilterFunctions.basicAuthentication(clientId, clientSecret));
            }
            builder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            webClient = builder.build();
        }
        return webClient;
    }

    static record TokenResponseDto(@JsonProperty("access_token") String accessToken) {
    }

    static class AuthenticationFailureException extends RuntimeException {
        private static final long serialVersionUID = -96469109512884829L;

        public AuthenticationFailureException(Throwable e) {
            super(e);
        }

        public AuthenticationFailureException(String e) {
            super(e);
        }
    }
}
