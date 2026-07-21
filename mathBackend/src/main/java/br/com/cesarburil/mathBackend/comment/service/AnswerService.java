package br.com.cesarburil.mathBackend.comment.service;

import br.com.cesarburil.mathBackend.comment.converter.AnswerConverter;
import br.com.cesarburil.mathBackend.comment.dto.AnswerRequest;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.model.Answer;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private AnswerConverter converter;

    private AnswerRepository repository;

    public AnswerService(AnswerConverter converter, AnswerRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }

    public AnswerResponse createAnswer(AnswerRequest request) {
        Answer aNewAnswer = converter.requestToAnswer(request);
        Answer saved = repository.save(aNewAnswer);
        return converter.answerToResponse(saved);

    }

    public AnswerResponse updateAnswer(AnswerRequest request, Long id) {
        Answer updated = converter.requestToAnswer(request, id);
        Answer saved = repository.save(updated);
        return converter.answerToResponse(saved);
    }

    public String deleteAnswer(Long id) {
        repository.deleteById(id);
        return id.toString();
    }
}
