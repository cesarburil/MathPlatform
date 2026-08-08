package br.com.cesarburil.mathBackend.category.repository;

import br.com.cesarburil.mathBackend.category.model.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"lessons", "questions"})
    List<Category> findAll();

    List<Category> findAllByTitleContainingOrderByTitleAsc(String keyword, PageRequest of);
}
