package br.com.cesarburil.mathBackend.category.converter;

import br.com.cesarburil.mathBackend.category.dto.CategoryRequest;
import br.com.cesarburil.mathBackend.category.dto.CategoryResponse;
import br.com.cesarburil.mathBackend.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryResponse categoryToResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }

    public Category requestToCategory(CategoryRequest request) {
        return Category
                .builder()
                .title(request.getTitle())
                .build();
    }



}
