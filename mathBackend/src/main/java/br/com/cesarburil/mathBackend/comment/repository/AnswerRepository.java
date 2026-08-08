package br.com.cesarburil.mathBackend.comment.repository;

import br.com.cesarburil.mathBackend.comment.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
