package br.com.cesarburil.mathBackend.comment.converter;

import br.com.cesarburil.mathBackend.auth.model.User;
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

        String username = "";

        if (comment.getUser() != null) {
            username = comment.getUser().getUsername();
        }

        return CommentResponse
                .builder()
                .id(comment.getId())
                .title(comment.getTitle())
                .username(username)
                .answers(answerListToResponse(comment.getAnswers()))
                .build();
    }

    private List<AnswerResponse> answerListToResponse(List<Answer> answers) {



        if (answers != null) {
            return answers.stream().map(answer -> {

                        String username = "";

                        if (answer.getUser() != null) {
                            username = answer.getUser().getUsername();
                        }

                return AnswerResponse.builder()
                        .title(answer.getTitle())
                        .username(username)
                        .commentId(answer.getComment().getId())
                        .id(answer.getId())
                        .build();
                    })
                    .toList();
        }

        return List.of(AnswerResponse.builder().build());


    }

    public Comment requestToComment(CommentRequest request, User user) {
        return Comment.builder().user(user).title(request.getTitle()).build();
    }

    public Comment requestToComment(CommentRequest request, Long id, User user) {
        return Comment
                .builder()
                .id(id)
                .user(user)
                .title(request.getTitle())
                .build();
    }

}
