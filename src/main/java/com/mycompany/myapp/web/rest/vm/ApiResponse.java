package com.mycompany.myapp.web.rest.vm;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Standard API Response format for REST endpoints.
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private long count;
    private Map<String, Object> metadata;
    private Instant timestamp;

    public ApiResponse() {
        this.timestamp = Instant.now();
        this.metadata = new HashMap<>();
        this.count = 0;
    }

    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
        setCountFromData(data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public ApiResponse<T> addMetadata(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }

    private void setCountFromData(T data) {
        if (data == null) {
            this.count = 0;
        } else if (data instanceof Collection) {
            this.count = ((Collection<?>) data).size();
        } else {
            this.count = 1;
        }
    }

    public ApiResponse<T> setCount(long count) {
        this.count = count;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        setCountFromData(data);
    }

    public long getCount() {
        return count;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}