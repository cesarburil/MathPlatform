package br.com.cesarburil.mathBackend.comment.controller;

import br.com.cesarburil.mathBackend.comment.dto.CommentRequest;
import br.com.cesarburil.mathBackend.comment.dto.CommentResponse;
import br.com.cesarburil.mathBackend.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
@RestController
@Tag(name = "comments", description = "Controller to see and edit comments data")
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "List existing comments", description = "List comments with pagination. Optional request params available.")
    @ApiResponse(responseCode = "200", description = "Comments listed successfully")
    @ApiResponse(responseCode = "404", description = "No comments found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<CommentResponse>> getAllComments(@RequestParam(defaultValue = "5") @Max(15) int quantity,
                                                                @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(service.getAllComments(pageNum, quantity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment by id", description = "Return a single comment")
    @ApiResponse(responseCode = "200", description = "Comment found")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "New comment", description = "Create a new comment")
    @ApiResponse(responseCode = "201", description = "Comment created successfully")
    @ApiResponse(responseCode = "401", description = "Authenticated user not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        return new ResponseEntity<>(service.createComment(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update comment", description = "Update an existing comment by id")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "401", description = "Authenticated user not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateComment(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete comment", description = "Delete an existing comment by id")
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteComment(id), HttpStatus.NO_CONTENT);
    }

}
