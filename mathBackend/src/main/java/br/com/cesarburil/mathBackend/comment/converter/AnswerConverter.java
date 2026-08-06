package br.com.cesarburil.mathBackend.comment.converter;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.comment.dto.AnswerRequest;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.model.Answer;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import br.com.cesarburil.mathBackend.comment.repository.CommentRepository;
import org.springframework.stereotype.Component;

@Component
public class AnswerConverter {

    public CommentRepository commentRepository;

    public AnswerConverter(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Answer requestToAnswer(AnswerRequest request, User user) {

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();

        return Answer
                .builder()
                .user(user)
                .comment(comment)
                .title(request.getTitle())
                .build();
    }

    public Answer requestToAnswer(AnswerRequest request, Long answerId, User user) {

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();

        return Answer
                .builder()
                .id(answerId)
                .user(user)
                .title(request.getTitle())
                .comment(comment)
                .build();
    }

    public AnswerResponse answerToResponse (Answer answer) {
        return AnswerResponse
                .builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .username(answer.getUser().getUsername())
                .commentId(answer.getComment().getId())
                .build();
    }
}
