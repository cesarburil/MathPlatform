package br.com.cesarburil.mathBackend.comment.controller;

import br.com.cesarburil.mathBackend.comment.dto.AnswerRequest;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@Tag(name = "answers", description = "Controller to see and edit answers data")
public class AnswerController {

    private AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @Operation(summary = "New answer", description = "Create a new answer for a comment")
    @ApiResponse(responseCode = "201", description = "Answer created successfully")
    @ApiResponse(responseCode = "401", description = "Authenticated user not found")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<AnswerResponse> createAnswer(@RequestBody AnswerRequest request) {
        return new ResponseEntity<>(service.createAnswer(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update answer", description = "Update an existing answer by id")
    @ApiResponse(responseCode = "200", description = "Answer updated successfully")
    @ApiResponse(responseCode = "401", description = "Authenticated user not found")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<AnswerResponse> updateAnswer(@RequestBody AnswerRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateAnswer(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete answer", description = "Delete an existing answer by id")
    @ApiResponse(responseCode = "204", description = "Answer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteAnswer(id), HttpStatus.NO_CONTENT);
    }

}
