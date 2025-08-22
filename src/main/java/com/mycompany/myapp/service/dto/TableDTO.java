package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TableDTO implements Serializable {

    private String id;
    private String name;
    private Integer capacity;
    private Integer status;
    private String area;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public TableDTO() {}

    public TableDTO(
        String id,
        String name,
        Integer capacity,
        Integer status,
        String area,
        Boolean isDeleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
    ) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.area = area;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
        if (o == null || getClass() != o.getClass()) return false;
        TableDTO tableDTO = (TableDTO) o;
        return (
            id.equals(tableDTO.id) &&
            name.equals(tableDTO.name) &&
            capacity.equals(tableDTO.capacity) &&
            status.equals(tableDTO.status) &&
            area.equals(tableDTO.area) &&
            isDeleted.equals(tableDTO.isDeleted) &&
            createdBy.equals(tableDTO.createdBy) &&
            updatedBy.equals(tableDTO.updatedBy) &&
            createdDate.equals(tableDTO.createdDate) &&
            updatedDate.equals(tableDTO.updatedDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, status, area, isDeleted, createdBy, updatedBy, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return (
            "TableDTO{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", capacity=" +
            capacity +
            ", status=" +
            status +
            ", area='" +
            area +
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
