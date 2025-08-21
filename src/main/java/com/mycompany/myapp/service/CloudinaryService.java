package com.mycompany.myapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryService.class);
    // This service can be used to interact with Cloudinary for image upload and
    // management
    // You can inject Cloudinary client here and implement methods for uploading,
    // deleting images, etc.
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, Object> uploadImage(MultipartFile file, String folder) throws IOException {
        log.debug("Uploading image to Cloudinary:   {}", file.getOriginalFilename());
        Map<String, Object> uploadParams = ObjectUtils.asMap(
            "folder",
            folder,
            "resource_type",
            "image",
            "user_filename",
            true,
            "unique_filename",
            true
        );

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
        log.debug("Image uploaded successfully: {}", uploadResult);
        return uploadResult;
    }

    public Map<String, Object> deleteImage(String publicId) throws IOException {
        log.debug("Deleting image from Cloudinary: {}", publicId);
        Map<String, Object> deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        log.debug("Image deleted successfully: {}", deleteResult);
        return deleteResult;
    }

    public String getImageUrl(String publicId) {
        return cloudinary.url().secure(true).generate(publicId);
    }

    public String getOptimizedImageUrl(String publicId, int width, int height) {
        return cloudinary
            .url()
            .transformation(new Transformation().width(width).height(height).crop("fill").quality("auto").fetchFormat("auto"))
            .secure(true)
            .generate(publicId);
    }
}
