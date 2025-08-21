package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CategoryQueryService;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.criteria.CategoryCriteria;
import com.mycompany.myapp.service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryResource {
	private final CategoryService categoryService;
	private final CategoryQueryService categoryQueryService;

	public CategoryResource(CategoryService categoryService , CategoryQueryService categoryQueryService) {
		this.categoryService = categoryService;
		this.categoryQueryService = categoryQueryService;
	}
    @PreAuthorize("@permissionEvaluator.hasAuthority('CATEGORY_CREATE')")
	@PostMapping("/category")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok(result);
	}
	@PreAuthorize("@permissionEvaluator.hasAuthority('CATEGORY_UPDATE')")
	@PutMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO result = categoryService.updateCategory(categoryDTO);
		return ResponseEntity.ok(result);
	}
	@PreAuthorize("@permissionEvaluator.hasAuthority('CATEGORY_ID')")
    @GetMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable String id) {
		CategoryDTO result = categoryService.getCategory(id);
		return ResponseEntity.ok(result);
	}
	    @PreAuthorize("@permissionEvaluator.hasAuthority('CATEGORY_ALL')")
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(
        CategoryCriteria criteria,
         Pageable pageable
    ) {
        Page<CategoryDTO> page = categoryQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok(page);
    }
	@PreAuthorize("@permissionEvaluator.hasAuthority('CATEGORY_DELETE')")
    @DeleteMapping("/category/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}