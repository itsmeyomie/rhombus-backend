package com.rhombus.concrete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SecurityCorsConfig implements WebMvcConfigurer {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow both localhost (development), LAN IP, and ngrok URLs (production/external access)
        // Note: ngrok URLs change frequently, update as needed
        config.setAllowedOrigins(List.of(
            "https://50f313082f2e.ngrok-free.app",
            "https://54e10639396f.ngrok-free.app",
            "https://77e1a13c7bc4.ngrok-free.app",
            "https://dd8bd9bb6410.ngrok-free.app",
            "http://localhost:4200",
            "http://192.168.1.2:4200"
        ));
        
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Allow all headers including ngrok-specific headers
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        
        config.setAllowCredentials(true);
        
        // Important: Set max age for preflight requests (1 hour)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // CorsFilter bean that uses the CorsConfigurationSource (works without Spring Security)
    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        return new CorsFilter(corsConfigurationSource);
    }

    // Additional CORS configuration at Spring MVC level
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "https://50f313082f2e.ngrok-free.app",
                    "https://54e10639396f.ngrok-free.app",
                    "https://77e1a13c7bc4.ngrok-free.app",
                    "https://dd8bd9bb6410.ngrok-free.app",
                    "http://localhost:4200",
                    "http://192.168.1.2:4200"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}


