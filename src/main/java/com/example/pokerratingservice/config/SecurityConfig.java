package com.example.pokerratingservice.config;

import com.example.pokerratingservice.service.CustomerDetailsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SecurityConfig {
    final CustomerDetailsService customerDetailsService;

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {

        return http

                .authorizeHttpRequests(auth -> {
                    auth

                            .requestMatchers("/auth/process-login").permitAll()
                            .anyRequest().authenticated();

                })
                .csrf(AbstractHttpConfigurer::disable)

                .build();
    }
    /*@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }
*/


}
