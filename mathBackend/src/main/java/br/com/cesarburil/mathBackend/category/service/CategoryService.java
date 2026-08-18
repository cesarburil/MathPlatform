package br.com.cesarburil.mathBackend.category.service;

import br.com.cesarburil.mathBackend.category.converter.CategoryConverter;
import br.com.cesarburil.mathBackend.category.dto.CategoryRequest;
import br.com.cesarburil.mathBackend.category.dto.CategoryResponse;
import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.repository.CategoryRepository;
import br.com.cesarburil.mathBackend.infra.exception.CategoryNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.NoCategoriesException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository repository;

    private CategoryConverter converter;

    public CategoryService(CategoryRepository repository, CategoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<CategoryResponse> getAllCategories(int quantity, int pageNum, String keyword) {

        List<CategoryResponse> responses = repository.findAllByTitleContainingOrderByTitleAsc(keyword, PageRequest.of(pageNum, quantity)).stream().map(category -> CategoryResponse
                .builder()
                .id(category.getId())
                .title(category.getTitle())
                .build()).toList();

        if (responses.isEmpty()) {
            throw new NoCategoriesException("No existing categories");
        }

        return responses;
    }

    public CategoryResponse createCategory(CategoryRequest request) {

        Category aNewCategory = converter.requestToCategory(request);
        Category saved = repository.save(aNewCategory);
        return converter.categoryToResponse(saved);

    }

    public CategoryResponse updateCategory(CategoryRequest request, Long id) {

        Category editing = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
        editing.setTitle(request.getTitle());
        Category saved = repository.save(editing);

        return converter.categoryToResponse(saved);

    }

    public String deleteCategory (Long id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found: " + id);
        }
        repository.deleteById(id);
        return id.toString();
    }


}
