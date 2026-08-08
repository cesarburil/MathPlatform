package br.com.cesarburil.mathBackend.question.repository;

import br.com.cesarburil.mathBackend.question.model.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @EntityGraph(attributePaths = {"alternatives"})
    List<Question> findAll();

}
