package com.example.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Ustawienie dla wszystkich endpointów
                .allowedOrigins("http://localhost:5174") // Podaj poprawny origin frontendu
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Zezwalanie na wybrane metody
                .allowedHeaders("*") // Zezwolenie na wszystkie nagłówki
                .allowCredentials(true); // Jeśli wymagana jest obsługa sesji lub ciasteczek
    }
}
