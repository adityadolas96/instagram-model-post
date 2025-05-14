package com.example.instagram_cloudinary_post.Service;

import com.example.instagram_cloudinary_post.Model.FileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Service
public class ImageKitService {
    private static final Logger logger = LoggerFactory.getLogger(ImageKitService.class);


    private final WebClient webClient;

    public ImageKitService(@Value("${imagekit.api.url}") String imageKitUrl,
                           @Value("${imagekit.api.key}") String apiKey) {

        this.webClient = WebClient.builder()
                .baseUrl(imageKitUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodeAuth(apiKey))
                .build();
    }

    private String encodeAuth(String apiKey) {
        return java.util.Base64.getEncoder().encodeToString((apiKey + ":").getBytes());
    }




    public String getRandomFileUrl() {
        logger.info("Fetching image list from Cloudinary...");

        FileResponse[] files = webClient.get()
                .retrieve()
                .bodyToMono(FileResponse[].class)
                .block(); // Blocking for simplicity

        if (files != null && files.length > 0) {
            int randomIndex = new Random().nextInt(files.length);
            return files[randomIndex].getUrl();
        }

        return "No files found";
    }
}
