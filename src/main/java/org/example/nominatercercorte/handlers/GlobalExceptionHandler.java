package org.example.nominatercercorte.handlers;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.example.nominatercercorte.dto.ExceptionDTO;
import org.example.nominatercercorte.exception.AlreadyExistsException;
import org.example.nominatercercorte.exception.BadAuthenticationException;
import org.example.nominatercercorte.exception.BadRequestException;
import org.example.nominatercercorte.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {
      AlreadyExistsException.class,
      BadRequestException.class
  })
  public ResponseEntity<ExceptionDTO> handleUsernameAlreadyExistsException(AlreadyExistsException e) {
    return ResponseEntity.badRequest()
        .body(
            new ExceptionDTO(e.getMessage(),
                e.getClass().getSimpleName(),
                400));
  }

  @ExceptionHandler(value = {
      NotFoundException.class
  })
  public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(404)
        .body(
            new ExceptionDTO(e.getMessage(),
                e.getClass().getSimpleName(),
                404));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation ->
        errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(BadAuthenticationException.class)
  public ResponseEntity<ExceptionDTO> handleCustomAuthenticationException(BadAuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            new ExceptionDTO(e.getMessage(),
                e.getClass().getSimpleName(),
                404));
  }
}
