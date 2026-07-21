package br.com.cesarburil.mathBackend.comment.converter;

import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.model.Answer;
import br.com.cesarburil.mathBackend.comment.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentConverter {

    public CommentResponse commentToResponse(Comment comment) {
        return CommentResponse
                .builder()
                .id(comment.getId())
                .title(comment.getTitle())
                .answers(answerListToResponse(comment.getAnswers()))
                .build();
    }

    private List<AnswerResponse> answerListToResponse(List<Answer> answers) {

        if (answers != null) {
            return answers.stream().map(answer -> AnswerResponse.builder()
                            .title(answer.getTitle())
                            .commentId(answer.getComment().getId())
                            .id(answer.getId())
                            .build())
                    .toList();
        }

        return List.of(AnswerResponse.builder().build());


    }

    public Comment requestToComment(CommentRequest request) {
        return Comment.builder().title(request.getTitle()).build();
    }

    public Comment requestToComment(CommentRequest request, Long id) {
        return Comment.builder().id(id).title(request.getTitle()).build();
    }

}
