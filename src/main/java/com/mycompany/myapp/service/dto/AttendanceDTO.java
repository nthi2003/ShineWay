package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceDTO implements Serializable {
    private String id;
    private Long userId;
    private String checkIn;
    private String checkOut;
    private LocalDate day;
    private Boolean isDeleted;
	private Long createdBy;
	private Long updatedBy;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;


    public AttendanceDTO() {}
    public AttendanceDTO(String id, Long userId, String checkIn, String checkOut, String note, LocalDate day , Boolean isDeleted, Long createdBy, Long updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.day = day;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
    public String getId(){
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }


    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, checkIn, checkOut, day, isDeleted, createdBy, updatedBy, createdDate, updatedDate);
    }
    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", day='" + day + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

}
