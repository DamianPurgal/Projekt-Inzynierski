package pl.damian.demor.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.damian.demor.exception.BusinessException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequiredProperties(BusinessException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(
                        ErrorResponse.builder()
                                .status(exception.getStatus())
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleMissingRequiredProperties(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message(exception.getMessage())
                                .build()
                );
    }
}
