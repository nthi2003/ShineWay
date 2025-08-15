package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.dto.CategoryDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class CategoryResource {
	private final CategoryService categoryService;

	public CategoryResource(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/categories")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO result = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok(result);
	}
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
		categoryDTO.setId(id);
		CategoryDTO result = categoryService.updateCategory(categoryDTO);
		return ResponseEntity.ok(result);
	}
    @GetMapping("/categories/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable String id) {
		CategoryDTO result = categoryService.getCategory(id);
		return ResponseEntity.ok(result);
	}
    @GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> result = categoryService.getAllCategories();
		return ResponseEntity.ok(result);
	}
    @DeleteMapping("/categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}