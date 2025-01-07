package com.example.moviepopularitybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) settings accross the whole application.
 */
@Configuration
public class WebConfig {
    /**
     * Bean definition for configuring CORS settings.
     *
     * @return a {@link WebMvcConfigurer} instance that customizes the CORS mappings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
                        /**
             * Configures CORS mappings for the application.
             *
             * @param registry the {@link CorsRegistry} to which CORS configurations are added.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS for all paths in the applicatio
                        .allowedOrigins("http://localhost:5173") // Allow requests from the specified origin (frontend application)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
