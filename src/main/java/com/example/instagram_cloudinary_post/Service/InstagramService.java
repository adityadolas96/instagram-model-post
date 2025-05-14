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

    private WebClient webClient;

    @Value("${instagram.access-token}")
    private String accessToken;

    @Value("${instagram.account-id}")
    private String accountId;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl("https://graph.instagram.com/v19.0/")
                .build();
    }

    /**
     * Step 1: Create media container
     */
    public String createMedia(String imageUrl) {
        logger.info("Creating Instagram media object with image URL: {}", imageUrl);

        Map<String, Object> requestBody = Map.of(
                "image_url", imageUrl,
                "caption", "Indian Bikini Models! #indianmodels #bikinimodels #hotmodels #indianbikinimodels",
                "access_token", accessToken
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri(accountId + "/media")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            logger.info("Media creation response: {}", response);
            return response != null ? response.get("id").toString() : null;
        } catch (Exception e) {
            logger.error("Error creating media on Instagram", e);
            return null;
        }
    }

    /**
     * Step 2: Publish the created media
     */
    public String publishMedia(String creationId) {
        logger.info("Publishing Instagram media object with creation ID: {}", creationId);

        Map<String, Object> requestBody = Map.of(
                "creation_id", creationId,
                "access_token", accessToken
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri(accountId + "/media_publish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            logger.info("Publish response: {}", response);
            return response != null ? response.get("id").toString() : null;
        } catch (Exception e) {
            logger.error("Error publishing media on Instagram", e);
            return null;
        }
    }
}
