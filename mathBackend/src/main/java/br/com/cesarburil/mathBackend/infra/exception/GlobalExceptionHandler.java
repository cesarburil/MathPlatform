package br.com.cesarburil.mathBackend.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            NoCategoriesException.class,
            CategoryNotFoundException.class,
            NoLessonsException.class,
            LessonNotFoundException.class,
            NoQuestionsException.class,
            QuestionNotFoundException.class,
            NoCommentsException.class,
            CommentNotFoundException.class,
            AnswerNotFoundException.class,
            UserNotFoundException.class,
            ProfileNotFoundException.class
    })
    public ResponseEntity<ApiError> notFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> userAlreadyExists(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            AuthenticatedUserNotFoundException.class
    })
    public ResponseEntity<ApiError> unauthorized(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(InvalidQuestionAlternativesException.class)
    public ResponseEntity<ApiError> invalidQuestionAlternatives(InvalidQuestionAlternativesException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ApiError> paymentProcessing(PaymentProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> runtimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage()));
    }
}
