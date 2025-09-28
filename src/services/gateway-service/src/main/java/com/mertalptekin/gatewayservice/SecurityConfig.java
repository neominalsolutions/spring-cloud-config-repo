package com.mertalptekin.gatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.*;
import java.util.stream.Collectors;

// Gateway'e Token olmadan gelinmez.

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // user-realm-role bazlı servis erişimi kontrolü
    // ali ve ahmet ile test edildi.
    // role bazlı servis erişim kontrolü kontrol edildi.
    // Erişim yoksa 403 verir.

    private ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverter() {

        // Converter tipleri: Jwt -> AbstractAuthenticationToken
        Converter<Jwt, AbstractAuthenticationToken> jwtAuthConverter = jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // 1️⃣ realm_access.roles kontrolü
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) realmAccess.get("roles");
                authorities.addAll(
                        roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                );
            }

            return new JwtAuthenticationToken(jwt, authorities);
        };

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter);
    }


        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

            http.authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/product-service/**")
                            .hasAuthority("product-service-access")

                            // order-service route => order-service-access scope
                            .pathMatchers("/order-service/**")
                            .hasAuthority("order-service-access")
                            .anyExchange().authenticated()
                    ).oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter()))
                    )
                    .csrf(ServerHttpSecurity.CsrfSpec::disable);
            return http.build();
        }

}