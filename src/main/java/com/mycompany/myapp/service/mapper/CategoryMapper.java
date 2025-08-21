package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.service.dto.CategoryDTO;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    // CategoryEntiry -> CategoryDTO
    public List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories) {
        return categories.stream().filter(Objects::nonNull).map(this::categoryToCategoryDTO).toList();
    }

    public CategoryDTO categoryToCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIsDeleted(category.getIsDeleted());
        dto.setCreatedBy(category.getCreatedBy());
        dto.setUpdatedBy(category.getUpdatedBy());
        dto.setCreatedDate(category.getCreatedDate());
        dto.setUpdatedDate(category.getUpdatedDate());
        return dto;
    }

    //Chuyển CategoryEntity -> CategoryDTO
    public List<Category> categoryDTOsToCategories(List<CategoryDTO> dtos) {
        return dtos.stream().filter(Objects::nonNull).map(this::categoryDTOToCategory).toList();
    }

    public Category categoryDTOToCategory(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        // Không set id, để JPA tự sinh
        category.setName(dto.getName());
        category.setIsDeleted(false); // Mặc định chưa xóa
        category.setCreatedBy(null); // Sẽ set ở Service
        category.setCreatedDate(java.time.LocalDateTime.now());
        category.setUpdatedBy(null);
        category.setUpdatedDate(null);
        return category;
    }

    public Category categoryFromId(String id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
