package br.com.cesarburil.mathBackend.question.converter;

import br.com.cesarburil.mathBackend.question.dto.AlternativeRequest;
import br.com.cesarburil.mathBackend.question.dto.AlternativeResponse;
import br.com.cesarburil.mathBackend.question.model.Alternative;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlternativeConverter {

    public List<Alternative> requestToAlternativeList(List<AlternativeRequest> requests) {

        //Fazer Alternativas Guardarem Questao

        return requests.stream().map(request -> Alternative
                .builder()
                .title(request.getTitle())
                .correct(request.isCorrect())
                .build()).toList();
    }

    public List<AlternativeResponse> alternativeToResponse(List<Alternative> alternatives) {

        return alternatives.stream().map(alternative ->
                AlternativeResponse
                        .builder()
                        .id(alternative.getId())
                        .title(alternative.getTitle())
                        .build())
                .toList();

    }

}
