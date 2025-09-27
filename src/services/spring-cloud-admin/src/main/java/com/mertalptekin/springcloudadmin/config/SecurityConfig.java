package com.mertalptekin.springcloudadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // REST API çağrıları için CSRF kapatılır
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/assets/**", "/login").permitAll() // UI login sayfası ve statik dosyalar
                        .requestMatchers("/instances", "/actuator/**").hasRole("CLIENT") // Admin client için gerekli endpoint'ler
                        .anyRequest().authenticated() // Diğer her şey kimlik doğrulama ister
                )
                .formLogin(Customizer.withDefaults()) // Web arayüz kullanıcıları için login ekranı
                .httpBasic(Customizer.withDefaults()); // Admin client servisleri için basic auth

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails client = User.withUsername("admin")
                .password("{noop}P@ssword1")
                .roles("CLIENT")
                .build();

        return new InMemoryUserDetailsManager(client);
    }
}
