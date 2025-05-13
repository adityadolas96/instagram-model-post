package com.example.instagram_cloudinary_post.Service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class InstagramService {
    private static final Logger logger = LoggerFactory.getLogger(InstagramService.class);

    private final WebClient webClient;

    @Value("${instagram.access-token}")
    private String accessToken;

    @Value("${instagram.account-id}")
    private String accountId;

    public InstagramService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://graph.instagram.com/v19.0/").build();
    }

    public String createMedia(String imageUrl) {
        logger.info("Creating Instagram media object...");

        Map<String, Object> requestBody = Map.of(
                "image_url", imageUrl,
                "caption", "Posted via API",
                "access_token", accessToken
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri(accountId+"/media")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();  // blocking for simplicity; consider async if preferred

            logger.info("Creation Response: {}", response);
            return response != null ? response.get("id").toString() : null;
        } catch (Exception e) {
            logger.error("Error creating media on Instagram", e);
            return null;
        }
    }

    public String publishMedia(String creationId) {
        logger.info("Publishing Instagram media object...");

        Map<String, Object> requestBody = Map.of(
                "creation_id", creationId,
                "access_token", accessToken
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri(accountId+"/media_publish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();  // blocking for simplicity; consider async if preferred

            logger.info("Posted Successfully!");
            return response != null ? response.get("id").toString() : null;
        } catch (Exception e) {
            logger.error("Error publishing media on Instagram", e);
            return null;
        }
    }
}
