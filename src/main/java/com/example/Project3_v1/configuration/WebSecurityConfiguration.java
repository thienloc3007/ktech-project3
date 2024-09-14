package com.example.Project3_v1.configuration;

import com.example.Project3_v1.jwt.JwtTokenFilter;
import com.example.Project3_v1.jwt.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userService;
    public WebSecurityConfiguration(
            JwtTokenUtils tokenUtils,
            UserDetailsService userService
    ) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error", "/token/issue", "/views/**", "/static/**", "auth/login",
                                    "/users/**", "/stores/**", "/admin/**", "/classification/**")
                            .permitAll();
//                    auth.requestMatchers("/users/**", "/store/**")
//                            .authenticated();
//                    auth.anyRequest()
//                            .authenticated();
                })
                .addFilterBefore(
                        new JwtTokenFilter(
                                tokenUtils,
                                userService
                        ),
                        AuthorizationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }



//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
}
