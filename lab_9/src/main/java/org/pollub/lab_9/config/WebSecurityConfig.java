package org.pollub.lab_9.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Konfiguracja menadżera AuthenticationManager tak, aby wiedział, skąd załadować użytkownika w celu dopasowania
        // danych uwierzytelniających. Zastosowano haszowanie hasła za pomocą BCryptPasswordEncoder.
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // W tym przykładzie niepotrzebne jest zabezpieczenie CSRF
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config -> config
                        // te żądania nie wymagają uwierzytelniania
                        .requestMatchers("/authenticate", "/swagger-ui/**","swagger-ui.html", "/v3/api-docs/**").permitAll()
                        // pozostałe żądania wymagają uwierzytelniania
                        .anyRequest().authenticated()
                )
                // zastosowana sesja bezstanowa — sesja nie przechowuje
                // stanu użytkownika.
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(config -> config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Dodanie filtra do walidacji tokena przy każdym żądaniu
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
