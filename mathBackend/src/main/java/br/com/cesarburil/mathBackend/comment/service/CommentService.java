package br.com.cesarburil.mathBackend.comment.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.repository.UserRepository;
import br.com.cesarburil.mathBackend.comment.converter.CommentConverter;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.CommentRepository;
import br.com.cesarburil.mathBackend.infra.exception.AuthenticatedUserNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.CommentNotFoundException;
import br.com.cesarburil.mathBackend.infra.exception.NoCommentsException;
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

        List<CommentResponse> responses = comments.stream().map(comment ->
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

        if (responses.isEmpty()) {
            throw new NoCommentsException("No existing comments");
        }

        return responses;
    }

    public CommentResponse getCommentById(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found: " + id));
        return commentConverter.commentToResponse(comment);

    }

    public CommentResponse createComment(CommentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new AuthenticatedUserNotFoundException("Authenticated user not found");
        }
        Comment aNewComment = commentConverter.requestToComment(request, user);
        Comment saved = commentRepository.save(aNewComment);
        return commentConverter.commentToResponse(saved);

    }


    public CommentResponse updateComment(CommentRequest request, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = (User) userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new AuthenticatedUserNotFoundException("Authenticated user not found");
        }
        Comment updated = commentConverter.requestToComment(request, id, user);
        Comment saved = commentRepository.save(updated);
        return commentConverter.commentToResponse(saved);

    }

    public String deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException("Comment not found: " + id);
        }
        commentRepository.deleteById(id);
        return id.toString();
    }
}
