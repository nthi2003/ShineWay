package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserPermissionDTO implements Serializable {

    private String id;
    private Long userId;
    private String permissionId;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserPermissionDTO() {
        // Default constructor
    }

    public UserPermissionDTO(
        String id,
        Long userId,
        String permissionId,
        Boolean isDeleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
    ) {
        this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPermissionDTO)) return false;

        UserPermissionDTO that = (UserPermissionDTO) o;

        if (!id.equals(that.id)) return false;
        if (!userId.equals(that.userId)) return false;
        if (!permissionId.equals(that.permissionId)) return false;
        if (!isDeleted.equals(that.isDeleted)) return false;
        if (!createdBy.equals(that.createdBy)) return false;
        if (!updatedBy.equals(that.updatedBy)) return false;
        if (!createdDate.equals(that.createdDate)) return false;
        return updatedDate.equals(that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, permissionId, isDeleted, createdBy, updatedBy, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return (
            "UserPermissionDTO{" +
            "id='" +
            id +
            '\'' +
            ", userId=" +
            userId +
            ", permissionId='" +
            permissionId +
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
