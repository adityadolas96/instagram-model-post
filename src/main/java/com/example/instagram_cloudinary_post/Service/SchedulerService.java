package com.example.instagram_cloudinary_post.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(InstagramService.class);

    private final CloudinaryService cloudinaryService;
    private final InstagramService instagramService;

    public SchedulerService(CloudinaryService cloudinaryService, InstagramService instagramService) {
        this.cloudinaryService = cloudinaryService;
        this.instagramService = instagramService;
    }

    // ⏰ Run every 15 minutes
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void syncToInstagram() {
        var imageUrls = cloudinaryService.fetchImageUrls();
        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size()-1);
        instagramService.publishMedia(instagramService.createMedia(imageUrls.get(randomIndex)));
    }

    // ⏰ Run every 15 minutes
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void status() {
        logger.info("Active...");
    }
}
