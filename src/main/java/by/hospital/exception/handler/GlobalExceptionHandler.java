package by.hospital.exception.handler;

import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.time.LocalDateTime;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DataAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleDataAlreadyExistsException(
      DataAlreadyExistsException ex) {
    return buildErrorResponse(ErrorCode.DATA_ALREADY_EXISTS, ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
    return buildErrorResponse(ErrorCode.DATA_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleDataNotFoundException(
      HttpMessageNotReadableException ex) {
    return buildErrorResponse(ErrorCode.JSON_NOT_READABLE, ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    return buildErrorResponse(
        ErrorCode.VALIDATION_ERROR,
        ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList()
            .toString(),
        HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      ErrorCode errorCode, String message, HttpStatus status) {
    return new ResponseEntity<>(new ErrorResponse(errorCode, message, LocalDateTime.now()), status);
  }
}
