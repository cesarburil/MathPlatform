package br.com.cesarburil.mathBackend.comment.controller;

import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.service.CommentService;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
@RestController
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentResponse>> getAllComments(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllComments(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        return new ResponseEntity<>(service.createComment(request), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateComment(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteComment(id), HttpStatus.OK);
    }

}
