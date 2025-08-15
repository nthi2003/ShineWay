package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CategoryDTO implements Serializable {
	private String id;
	private String name;
	private Boolean isdeleted;
	private Long createdBy;
	private Long updatedBy;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public CategoryDTO() {}

	public CategoryDTO(String id, String name, Boolean isdeleted, Long createdBy, Long updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.name = name;
		this.isdeleted = isdeleted;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Boolean getIsdeleted() { return isdeleted; }
	public void setIsdeleted(Boolean isdeleted) { this.isdeleted = isdeleted; }

	public Long getCreatedBy() { return createdBy; }
	public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

	public Long getUpdatedBy() { return updatedBy; }
	public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }

	public LocalDateTime getCreatedDate() { return createdDate; }
	public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

	public LocalDateTime getUpdatedDate() { return updatedDate; }
	public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CategoryDTO that = (CategoryDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(isdeleted, that.isdeleted) &&
				Objects.equals(createdBy, that.createdBy) &&
				Objects.equals(updatedBy, that.updatedBy) &&
				Objects.equals(createdDate, that.createdDate) &&
				Objects.equals(updatedDate, that.updatedDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, isdeleted, createdBy, updatedBy, createdDate, updatedDate);
	}

	@Override
	public String toString() {
		return "CategoryDTO{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", isdeleted=" + isdeleted +
				", createdBy='" + createdBy + '\'' +
				", updatedBy='" + updatedBy + '\'' +
				", createdDate=" + createdDate +
				", updatedDate=" + updatedDate +
				'}';
	}
}
