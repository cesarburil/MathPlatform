package br.com.cesarburil.mathBackend.question.service;

import br.com.cesarburil.mathBackend.category.dto.CategoryResponse;
import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.service.CategoryService;
import br.com.cesarburil.mathBackend.question.converter.QuestionConverter;
import br.com.cesarburil.mathBackend.question.dto.AlternativeRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionResponse;
import br.com.cesarburil.mathBackend.question.model.Difficulty;
import br.com.cesarburil.mathBackend.question.model.Question;
import br.com.cesarburil.mathBackend.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository repository;

    private QuestionConverter converter;


    public QuestionService(QuestionRepository repository, QuestionConverter converter, CategoryService categoryService) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<QuestionResponse> getAllQuestions(int pageNum, int quantity) {

        Page<Question> questions = repository.findAll(PageRequest.of(pageNum, quantity));

        return converter.questionListToResponse(questions.stream().toList());
    }

    public QuestionResponse createQuestion(QuestionRequest request) {

        if (request.getAlternatives().size() != 5) {
            throw new RuntimeException("Question needs 5 alternatives");
        }

        if (request.getAlternatives().stream().filter(AlternativeRequest::isCorrect).count() == 1) {
            Question aNewQuestion = converter.requestToQuestion(request);
            Question saved = repository.save(aNewQuestion);
            return converter.questionToResponse(saved);
        } else {
            throw new RuntimeException("Question needs 1 correct alternative");
        }

    }

    public QuestionResponse getQuestionById(Long id) {
        Question question = repository.findById(id).orElseThrow();
        return converter.questionToResponse(question);
    }

    public QuestionResponse updateQuestion(QuestionRequest request, Long id) {

        if (request.getAlternatives().size() != 5) {
            throw new RuntimeException("Question needs 5 alternatives");
        }

        if (request.getAlternatives().stream().filter(AlternativeRequest::isCorrect).count() == 1) {
            Question updatedQuestion = converter.requestToQuestion(request, id);
            Question saved = repository.save(updatedQuestion);
            return converter.questionToResponse(saved);
        } else {
            throw new RuntimeException("Question needs 1 correct alternative");
        }

    }

    public String deleteQuestion(Long id) {
        repository.deleteById(id);
        return id.toString();

    }


}
