package br.com.cesarburil.mathBackend.lesson.repository;

import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByCategoryId(Long id);

}
