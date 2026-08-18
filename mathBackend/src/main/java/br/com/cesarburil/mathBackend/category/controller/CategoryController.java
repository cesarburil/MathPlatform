package br.com.cesarburil.mathBackend.category.controller;

import br.com.cesarburil.mathBackend.category.dto.CategoryRequest;
import br.com.cesarburil.mathBackend.category.dto.CategoryResponse;
import br.com.cesarburil.mathBackend.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "categories", description = "Controller to see and edit categories data")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "List existing categories", description = "List categories with pagination. Optional request params available.")
    @ApiResponse(responseCode = "200", description = "Categories listed successfully")
    @ApiResponse(responseCode = "404", description = "No categories found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<CategoryResponse>> categories(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                             @RequestParam(defaultValue = "0") int pageNum,
                                                             @RequestParam(defaultValue = "") String keyword) {

        List<CategoryResponse> responses = service.getAllCategories(quantity, pageNum, keyword);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "New category", description = "Create a new category for lessons and questions")
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return new ResponseEntity<>(service.createCategory(request), HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    @Operation(summary = "Update category", description = "Update an existing category by id")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateCategory(request, id), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete category", description = "Delete an existing category by id")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteCategory(id), HttpStatus.NO_CONTENT);
    }



}
