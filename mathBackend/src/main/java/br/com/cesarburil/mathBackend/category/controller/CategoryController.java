package br.com.cesarburil.mathBackend.category.controller;

import br.com.cesarburil.mathBackend.category.dto.CategoryRequest;
import br.com.cesarburil.mathBackend.category.dto.CategoryResponse;
import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.service.CategoryService;
import jakarta.validation.constraints.Max;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> categories(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                             @RequestParam(defaultValue = "0") int pageNum,
                                                             @RequestParam(defaultValue = "") String keyword) {

        List<CategoryResponse> responses = service.getAllCategories(quantity, pageNum, keyword);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return new ResponseEntity<>(service.createCategory(request), HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateCategory(request, id), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteCategory(id), HttpStatus.OK);
    }



}
