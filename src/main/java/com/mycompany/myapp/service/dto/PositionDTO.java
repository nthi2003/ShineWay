package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PositionDTO implements Serializable {


    private String positionId;
    private String name;
    private String description;
    private Boolean isDeleted = false; // Default value for isDeleted
    private Long createdBy;
    private Long updatedBy; 
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PositionDTO() {
        // Default constructor
    }

    public PositionDTO(String positionId, String name, String description, Long createdBy, Long updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, Boolean isDeleted) {
        this.positionId = positionId;
        this.name = name;
        this.isDeleted = isDeleted;
        this.description = description;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy; 
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
		if (o == null || getClass() != o.getClass()) return false;
        PositionDTO that = (PositionDTO) o;
        return Objects.equals(positionId, that.positionId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId, name, description, isDeleted, createdBy, updatedBy, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "positionId='" + positionId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

}
