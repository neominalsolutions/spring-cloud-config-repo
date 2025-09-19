package com.mertalptekin.gatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

// Gateway'e Token olmadan gelinmez.

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> {})).csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
}