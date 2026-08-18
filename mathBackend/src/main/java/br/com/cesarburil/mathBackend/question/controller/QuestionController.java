package br.com.cesarburil.mathBackend.question.controller;

import br.com.cesarburil.mathBackend.question.dto.QuestionRequest;
import br.com.cesarburil.mathBackend.question.dto.QuestionResponse;
import br.com.cesarburil.mathBackend.question.dto.VerifiedQuestionRequest;
import br.com.cesarburil.mathBackend.question.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@Tag(name = "questions", description = "Controller to see and edit questions data")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "List existing questions", description = "List questions with pagination. Optional request params available.")
    @ApiResponse(responseCode = "200", description = "Questions listed successfully")
    @ApiResponse(responseCode = "404", description = "No questions found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                  @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllQuestions(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get question by id", description = "Return a single question")
    @ApiResponse(responseCode = "200", description = "Question found")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<QuestionResponse> getQuestionbyId(@PathVariable Long id) {
        return new ResponseEntity<>(service.getQuestionById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "New question", description = "Create a new question with 5 alternatives and 1 correct")
    @ApiResponse(responseCode = "201", description = "Question created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid alternatives")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        return new ResponseEntity<>(service.createQuestion(request), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    @Operation(summary = "Update question", description = "Update an existing question by id")
    @ApiResponse(responseCode = "200", description = "Question updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid alternatives")
    @ApiResponse(responseCode = "404", description = "Question or category not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody QuestionRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateQuestion(request, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete question", description = "Delete an existing question by id")
    @ApiResponse(responseCode = "204", description = "Question deleted successfully")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteQuestion(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify answer", description = "Check if the chosen alternative is correct")
    @ApiResponse(responseCode = "200", description = "Verification result")
    @ApiResponse(responseCode = "400", description = "Question has no correct alternative")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Boolean> verifyAnswer(@RequestBody VerifiedQuestionRequest question) {
        return new ResponseEntity<>(service.verify(question), HttpStatus.OK);
    }

}
