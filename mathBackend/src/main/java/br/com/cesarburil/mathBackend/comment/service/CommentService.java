package br.com.cesarburil.mathBackend.comment.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import br.com.cesarburil.mathBackend.comment.converter.CommentConverter;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.CommentRepository;
import jakarta.validation.constraints.Max;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final UserRepository userRepository;

    private CommentConverter commentConverter;

    private CommentRepository commentRepository;

    public CommentService(UserRepository userRepository, CommentRepository commentRepository, CommentConverter commentConverter) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    public List<CommentResponse> getAllComments(int pageNum, int quantity) {

        Page<Comment> comments = commentRepository.findAll(PageRequest.of(pageNum, quantity));



        return comments.stream().map(comment ->
                {
                    String username = "";

                    if (comment.getUser() != null) {
                        username = comment.getUser().getUsername();
                    }

                    return CommentResponse
                            .builder()
                            .id(comment.getId())
                            .title(comment.getTitle())
                            .username(username)
                            .answers(comment.getAnswers().stream().map(answer ->
                                    AnswerResponse
                                            .builder()
                                            .id(answer.getId())
                                            .commentId(comment.getId())
                                            .title(answer.getTitle())
                                            .build()).toList())

                            .build();
                }

        ).toList();

    }

    public CommentResponse getCommentById(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        return commentConverter.commentToResponse(comment);

    }

    public CommentResponse createComment(CommentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        Comment aNewComment = commentConverter.requestToComment(request, user);
        Comment saved = commentRepository.save(aNewComment);
        return commentConverter.commentToResponse(saved);

    }


    public CommentResponse updateComment(CommentRequest request, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        Comment updated = commentConverter.requestToComment(request, id, user);
        Comment saved = commentRepository.save(updated);
        return commentConverter.commentToResponse(saved);

    }

    public String deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id.toString();
    }
}
