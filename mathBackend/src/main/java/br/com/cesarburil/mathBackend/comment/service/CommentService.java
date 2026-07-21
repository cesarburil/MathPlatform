package br.com.cesarburil.mathBackend.comment.service;

import br.com.cesarburil.mathBackend.comment.converter.CommentConverter;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.CommentRepository;
import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentConverter commentConverter;

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    public List<CommentResponse> getAllComments(int pageNum, int quantity) {

        Page<Comment> comments = commentRepository.findAll(PageRequest.of(pageNum, quantity));

        return comments.stream().map(comment ->
                CommentResponse
                        .builder()
                        .id(comment.getId())
                        .title(comment.getTitle())
                        .answers(comment.getAnswers().stream().map(answer ->
                                AnswerResponse
                                        .builder()
                                        .id(answer.getId())
                                        .commentId(comment.getId())
                                        .title(answer.getTitle())
                                        .build()).toList())

                        .build()).toList();

    }

    public CommentResponse getCommentById(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        return commentConverter.commentToResponse(comment);

    }

    public CommentResponse createComment(CommentRequest request) {

        Comment aNewComment = commentConverter.requestToComment(request);
        Comment saved = commentRepository.save(aNewComment);
        return commentConverter.commentToResponse(saved);

    }


    public CommentResponse updateComment(CommentRequest request, Long id) {
        Comment updated = commentConverter.requestToComment(request, id);
        Comment saved = commentRepository.save(updated);
        return commentConverter.commentToResponse(saved);

    }

    public String deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id.toString();
    }
}
