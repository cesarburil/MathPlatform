package br.com.cesarburil.mathBackend.comment.converter;

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


    public Answer requestToAnswer(AnswerRequest request) {

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();

        return Answer
                .builder()
                .comment(comment)
                .title(request.getTitle())
                .build();
    }

    public Answer requestToAnswer(AnswerRequest request, Long answerId) {

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();

        return Answer
                .builder()
                .id(answerId)
                .title(request.getTitle())
                .comment(comment)
                .build();
    }

    public AnswerResponse answerToResponse (Answer answer) {
        return AnswerResponse
                .builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .commentId(answer.getComment().getId())
                .build();
    }
}
