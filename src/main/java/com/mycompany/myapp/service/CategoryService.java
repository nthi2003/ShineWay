package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Category;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.CategoryDTO;
import com.mycompany.myapp.service.mapper.CategoryMapper;

@Service
@Transactional
public class CategoryService {
    // Inject the CategoryRepository
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    public CategoryDTO createCategory(CategoryDTO categoryDTO)
    {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        Long currentUserId = SecurityUtils.getCurrentUserId()
        .orElseThrow(() -> new RuntimeException("Not logged in"));

        category.setCreatedBy(currentUserId);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(category);
    }
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
         Category category = categoryRepository.findById(categoryDTO.getId())
        .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDTO.getName());
        category.setUpdatedBy(SecurityUtils.getCurrentUserId().orElseThrow(() -> new RuntimeException("Not logged in")));
        category.setUpdatedDate(LocalDateTime.now());

        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(category);
    }
    public CategoryDTO getCategory(String id) {
        return categoryRepository.findByIdAndIsdeletedFalse(id)
            .map(categoryMapper::categoryToCategoryDTO)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.categoriesToCategoryDTOs(categoryRepository.findAllByIsdeletedFalse());
    }
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setIsdeleted(true);
        categoryRepository.save(category);
    }
}
