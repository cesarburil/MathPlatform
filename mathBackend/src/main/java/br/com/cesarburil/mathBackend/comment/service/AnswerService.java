package br.com.cesarburil.mathBackend.comment.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import br.com.cesarburil.mathBackend.comment.converter.AnswerConverter;
import br.com.cesarburil.mathBackend.comment.dto.AnswerRequest;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.model.Answer;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.AnswerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private AnswerConverter converter;

    private AnswerRepository repository;

    private final UserRepository userRepository;

    public AnswerService(AnswerConverter converter, AnswerRepository repository, UserRepository userRepository) {
        this.converter = converter;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public AnswerResponse createAnswer(AnswerRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        Answer aNewAnswer = converter.requestToAnswer(request, user);
        Answer saved = repository.save(aNewAnswer);
        return converter.answerToResponse(saved);

    }

    public AnswerResponse updateAnswer(AnswerRequest request, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        Answer updated = converter.requestToAnswer(request, id, user);
        Answer saved = repository.save(updated);
        return converter.answerToResponse(saved);
    }

    public String deleteAnswer(Long id) {
        repository.deleteById(id);
        return id.toString();
    }
}
