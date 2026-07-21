package br.com.cesarburil.mathBackend.question.controller;

import br.com.cesarburil.mathBackend.question.converter.QuestionConverter;
import br.com.cesarburil.mathBackend.question.dto.QuestionRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionResponse;
import br.com.cesarburil.mathBackend.question.service.QuestionService;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                  @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllQuestions(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionbyId(@PathVariable Long id) {
        return new ResponseEntity<>(service.getQuestionById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        return new ResponseEntity<>(service.createQuestion(request), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody QuestionRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateQuestion(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteQuestion(id), HttpStatus.OK);
    }



}
