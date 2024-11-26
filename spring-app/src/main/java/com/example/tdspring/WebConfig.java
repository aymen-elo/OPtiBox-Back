package com.example.tdspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applique à toutes les routes
                .allowedOrigins("http://sx04nellt0200.ad.ponet:4201") // Frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Autoriser tous les en-têtes
                .allowCredentials(true); // Permet d'envoyer des cookies, si nécessaire
    }
}
*/