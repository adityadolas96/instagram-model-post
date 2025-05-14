package com.example.instagram_cloudinary_post.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(InstagramService.class);

    private final ImageKitService imageKitService;
    private final InstagramService instagramService;

    public SchedulerService(ImageKitService imageKitService, InstagramService instagramService) {
        this.imageKitService = imageKitService;
        this.instagramService = instagramService;
    }

    // ⏰ Run every 15 minutes
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void syncToInstagram() {
        var imageUrls = imageKitService.getRandomFileUrl();
        instagramService.publishMedia(instagramService.createMedia(imageUrls));
    }

    // ⏰ Run every 15 minutes
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void status() {
        logger.info("Active...");
    }
}
