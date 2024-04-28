package com.technicaltest.advice;

import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.exceptions.EntityAlreadyExistsException;
import com.technicaltest.exceptions.GlobalException;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<ResponseDTO>  handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(errors)
                    .build(), HttpStatus.BAD_REQUEST);
     }

     @ExceptionHandler(HttpMessageNotReadableException.class)
     public ResponseEntity<ResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Invalid JSON request")
                    .build(), HttpStatus.BAD_REQUEST);
     }

     @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Data integrity violation exception")
                    .build(), HttpStatus.BAD_REQUEST);
        }

     @ExceptionHandler(EntityAlreadyExistsException.class)
        public ResponseEntity<ResponseDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }

     @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

     @ExceptionHandler(GlobalException.class)
        public ResponseEntity<ResponseDTO> handleGlobalException(GlobalException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

     @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }

     @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<ResponseDTO> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.NOT_FOUND);
        }

     @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<ResponseDTO> handleDataAccessException(DataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     @ExceptionHandler(TransactionException.class)
        public ResponseEntity<ResponseDTO> handleTransactionException(TransactionException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
