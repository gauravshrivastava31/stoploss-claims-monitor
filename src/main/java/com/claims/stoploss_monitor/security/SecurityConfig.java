package com.claims.stoploss_monitor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/health"
                        )
                        .permitAll()

                        .requestMatchers("/api/**")
                        .authenticated()

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(
                        Customizer.withDefaults()
                )

                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder
    ) {

        UserDetails analyst =
                User.builder()
                        .username("analyst")
                        .password(
                                encoder.encode(
                                        "analyst123"
                                )
                        )
                        .roles("ANALYST")
                        .build();

        UserDetails brokerA =
                User.builder()
                        .username("brokerA")
                        .password(
                                encoder.encode(
                                        "broker123"
                                )
                        )
                        .roles("BROKER")
                        .build();

        UserDetails brokerB =
                User.builder()
                        .username("brokerB")
                        .password(
                                encoder.encode(
                                        "broker123"
                                )
                        )
                        .roles("BROKER")
                        .build();

        return new InMemoryUserDetailsManager(
                analyst,
                brokerA,
                brokerB
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }
}