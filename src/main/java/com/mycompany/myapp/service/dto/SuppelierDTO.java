package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;

public class SuppelierDTO implements Serializable {

    private String suppelierId;
    private String name;
    private String address;
    private String phone;

    private LocalDateTime updatedDate;
    private Long updatedBy;  
    private LocalDateTime createdDate;
    private Long createdBy;   

    private Boolean isDeleted = false;
    private Long userId;

    public String getSuppelierId() { return suppelierId; }
    public void setSuppelierId(String suppelierId) { this.suppelierId = suppelierId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
