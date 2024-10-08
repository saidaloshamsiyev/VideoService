package org.example.videoservice.config;


import org.example.videoservice.filter.VideoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/api/channels").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new VideoFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}


