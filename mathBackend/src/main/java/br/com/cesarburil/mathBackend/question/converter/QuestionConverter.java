package br.com.cesarburil.mathBackend.question.converter;

import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.repository.CategoryRepository;
import br.com.cesarburil.mathBackend.question.dto.AlternativeRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionResponse;
import br.com.cesarburil.mathBackend.question.model.Alternative;
import br.com.cesarburil.mathBackend.question.model.Difficulty;
import br.com.cesarburil.mathBackend.question.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionConverter {

    private CategoryRepository categoryRepository;

    private AlternativeConverter alternativeConverter;

    public QuestionConverter(CategoryRepository categoryRepository, AlternativeConverter alternativeConverter) {
        this.categoryRepository = categoryRepository;
        this.alternativeConverter = alternativeConverter;
    }

    public Question requestToQuestion (QuestionRequest request) {

        //Fazer Alternativas pegarem Questao Tambem

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        Question question = Question
                .builder()
                .title(request.getTitle())
                .video(request.getVideo())
                .difficulty(Difficulty.valueOf(request.getDifficulty()))
                .category(category)
                .alternatives(
                        alternativeConverter.requestToAlternativeList(request.getAlternatives()))
                .build();

        question.getAlternatives().forEach(alternative -> alternative.setQuestion(question));

        return question;
    }

    public Question requestToQuestion (QuestionRequest request, Long id) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();

        return Question
                .builder()
                .title(request.getTitle())
                .id(id)
                .video(request.getVideo())
                .difficulty(Difficulty.valueOf(request.getDifficulty()))
                .category(category)
                .alternatives(alternativeConverter.requestToAlternativeList(request.getAlternatives()))
                .build();
    }


    public QuestionResponse questionToResponse(Question question) {
        return QuestionResponse
                .builder()
                .title(question.getTitle())
                .id(question.getId())
                .categoryName(question.getCategory().getTitle())
                .difficulty(question.getDifficulty().toString())
                .alternatives(alternativeConverter.alternativeToResponse(question.getAlternatives()))
                .video(question.getVideo())
                .build();
    }


    public List<QuestionResponse> questionListToResponse(List<Question> questions){
        return questions.stream().map(this::questionToResponse).toList();
    }
}
