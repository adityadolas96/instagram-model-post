package com.example.instagram_cloudinary_post.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "✅ App is alive at " + new Date();
    }
}

