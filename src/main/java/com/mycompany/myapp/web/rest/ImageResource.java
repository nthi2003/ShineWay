package com.mycompany.myapp.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.myapp.service.CloudinaryService;

@RestController
@RequestMapping("/api/images")
public class ImageResource {
    private static final Logger log = LoggerFactory.getLogger(ImageResource.class.getName());
    private final CloudinaryService cloudinaryService;

    public ImageResource(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "uploads") String folder) {
        log.debug("REST request to upload image: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "File is empty");
            return ResponseEntity.badRequest().body(response);
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid file type. Only images are allowed.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file, folder);

            Map<String, Object> response = new HashMap<>();
            response.put("public_id", uploadResult.get("public_id"));
            response.put("url", uploadResult.get("secure_url")); 

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error uploading image: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error uploading image: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteImage(@RequestParam("publicId") String publicId) {
        log.debug("REST request to delete image: {}", publicId);
        try {
            Map<String, Object> deleteResult = cloudinaryService.deleteImage(publicId);
            return ResponseEntity.ok(deleteResult);
        } catch (Exception e) {
            log.error("Error deleting image: {}", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting image: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/url")
    public ResponseEntity<Map<String, Object>> getImageUrlByQuery(
            @RequestParam("publicId") String publicId,
            @RequestParam(value = "width", required = false) Integer width,
            @RequestParam(value = "height", required = false) Integer height) {
        log.debug("REST request to get image URL: {}", publicId);

        try {
            String url;
            if (width != null && height != null) {
                url = cloudinaryService.getOptimizedImageUrl(publicId, width, height);
            } else {
                url = cloudinaryService.getImageUrl(publicId);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("public_id", publicId);
            response.put("url", url);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting image URL from Cloudinary", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to get image URL: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
