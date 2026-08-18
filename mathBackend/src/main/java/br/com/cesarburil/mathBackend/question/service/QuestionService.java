package br.com.cesarburil.mathBackend.question.service;

import br.com.cesarburil.mathBackend.infra.exception.InvalidQuestionAlternativesException;
import br.com.cesarburil.mathBackend.infra.exception.NoQuestionsException;
import br.com.cesarburil.mathBackend.infra.exception.QuestionNotFoundException;
import br.com.cesarburil.mathBackend.question.converter.QuestionConverter;
import br.com.cesarburil.mathBackend.question.dto.AlternativeRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionResponse;
import br.com.cesarburil.mathBackend.question.dto.VerifiedQuestionRequest;
import br.com.cesarburil.mathBackend.question.model.Alternative;
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


    public QuestionService(QuestionRepository repository, QuestionConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<QuestionResponse> getAllQuestions(int pageNum, int quantity) {

        Page<Question> questions = repository.findAll(PageRequest.of(pageNum, quantity));

        List<QuestionResponse> responses = converter.questionListToResponse(questions.stream().toList());
        if (responses.isEmpty()) {
            throw new NoQuestionsException("No existing questions");
        }
        return responses;
    }

    public QuestionResponse createQuestion(QuestionRequest request) {

        if (request.getAlternatives().size() != 5) {
            throw new InvalidQuestionAlternativesException("Question needs 5 alternatives");
        }

        if (request.getAlternatives().stream().filter(AlternativeRequest::isCorrect).count() == 1) {
            Question aNewQuestion = converter.requestToQuestion(request);
            Question saved = repository.save(aNewQuestion);
            return converter.questionToResponse(saved);
        } else {
            throw new InvalidQuestionAlternativesException("Question needs 1 correct alternative");
        }

    }

    public QuestionResponse getQuestionById(Long id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found: " + id));
        return converter.questionToResponse(question);
    }

    public QuestionResponse updateQuestion(QuestionRequest request, Long id) {

        if (request.getAlternatives().size() != 5) {
            throw new InvalidQuestionAlternativesException("Question needs 5 alternatives");
        }

        if (request.getAlternatives().stream().filter(AlternativeRequest::isCorrect).count() == 1) {
            Question updatedQuestion = converter.requestToQuestion(request, id);
            Question saved = repository.save(updatedQuestion);
            return converter.questionToResponse(saved);
        } else {
            throw new InvalidQuestionAlternativesException("Question needs 1 correct alternative");
        }

    }

    public String deleteQuestion(Long id) {
        if (!repository.existsById(id)) {
            throw new QuestionNotFoundException("Question not found: " + id);
        }
        repository.deleteById(id);
        return id.toString();
    }


    public Boolean verify(VerifiedQuestionRequest question) {
        Question questionFound = repository.findById(question.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found: " + question.getQuestionId()));
        return questionFound.getAlternatives().stream()
                .filter(Alternative::isCorrect)
                .findFirst()
                .orElseThrow(() -> new InvalidQuestionAlternativesException("Question has no correct alternative"))
                .getId()
                .equals(question.getAlternativeId());

    }
}
