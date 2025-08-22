package com.mycompany.myapp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    private final CloudinaryProperties cloudinaryProperties;

    public CloudinaryConfiguration(CloudinaryProperties cloudinaryProperties) {
        this.cloudinaryProperties = cloudinaryProperties;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name",
                cloudinaryProperties.getCloudName(),
                "api_key",
                cloudinaryProperties.getApiKey(),
                "api_secret",
                cloudinaryProperties.getApiSecret(),
                "secure",
                cloudinaryProperties.isSecure()
            )
        );
    }
}
