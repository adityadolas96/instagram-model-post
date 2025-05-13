package com.example.instagram_cloudinary_post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InstagramCloudinaryPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramCloudinaryPostApplication.class, args);
	}

}
