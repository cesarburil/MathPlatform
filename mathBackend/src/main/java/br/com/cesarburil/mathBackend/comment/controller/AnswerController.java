package br.com.cesarburil.mathBackend.comment.controller;

import br.com.cesarburil.mathBackend.comment.dto.AnswerRequest;
import br.com.cesarburil.mathBackend.comment.dto.AnswerResponse;
import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.service.AnswerService;
import br.com.cesarburil.mathBackend.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<AnswerResponse> createAnswer(@RequestBody AnswerRequest request) {
        return new ResponseEntity<>(service.createAnswer(request), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnswerResponse> updateAnswer(@RequestBody AnswerRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateAnswer(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteAnswer(id), HttpStatus.OK);
    }

}
