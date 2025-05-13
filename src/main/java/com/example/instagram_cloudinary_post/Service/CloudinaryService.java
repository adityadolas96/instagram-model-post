package com.example.instagram_cloudinary_post.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @PostConstruct
    public void initCloudinary() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public List<String> fetchImageUrls() {
        logger.info("Fetching image list from Cloudinary...");
        List<String> urls = new ArrayList<>();
        try {
            Map result = cloudinary.api().resources(ObjectUtils.asMap("type", "upload", "max_results", 100));
            List<Map> resources = (List<Map>) result.get("resources");
            for (Map resource : resources) {
                urls.add((String) resource.get("url"));
            }
        } catch (Exception e) {
            logger.error("Error fetching images from Cloudinary", e);
        }
        return urls;
    }
}
