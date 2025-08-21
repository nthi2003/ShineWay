package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PermissionDTO implements Serializable {

    private String id;
    private String code;
    private String resource;
    private String description;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PermissionDTO() {}

    public PermissionDTO(
        String id,
        String code,
        String resource,
        String description,
        Boolean isDeleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
    ) {
        this.id = id;
        this.code = code;
        this.resource = resource;
        this.description = description;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Override equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionDTO)) return false;
        PermissionDTO that = (PermissionDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(resource, that.resource) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate)
        );
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, code, resource, description, isDeleted, createdBy, updatedBy, createdDate, updatedDate);
    }

    // Override toString
    @Override
    public String toString() {
        return (
            "PermissionDTO{" +
            "id='" +
            id +
            '\'' +
            ", code='" +
            code +
            '\'' +
            ", resource='" +
            resource +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", isDeleted=" +
            isDeleted +
            ", createdBy=" +
            createdBy +
            ", updatedBy=" +
            updatedBy +
            ", createdDate=" +
            createdDate +
            ", updatedDate=" +
            updatedDate +
            '}'
        );
    }
}
