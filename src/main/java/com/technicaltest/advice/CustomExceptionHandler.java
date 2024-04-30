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
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(", ")
        );

        // Remove the last comma and space
        if (errors.length() > 0) {
            errors.setLength(errors.length() - 2);
        }
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(errors.toString())
                    .build(), HttpStatus.BAD_REQUEST);
     }

     @ExceptionHandler(HttpMessageNotReadableException.class)
     public ResponseEntity<ResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("La solitud no pudo ser procesada")
                    .build(), HttpStatus.BAD_REQUEST);
     }

     @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.CONFLICT);
        }

     @ExceptionHandler(EntityAlreadyExistsException.class)
        public ResponseEntity<ResponseDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.CONFLICT);
        }

     @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.NOT_FOUND);
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
                    .response("Argumento inválido")
                    .build(), HttpStatus.BAD_REQUEST);
        }

     @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<ResponseDTO> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("No se encontraron resultados")
                    .build(), HttpStatus.NOT_FOUND);
        }

     @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<ResponseDTO> handleDataAccessException(DataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Error de acceso a los datos")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     @ExceptionHandler(TransactionException.class)
        public ResponseEntity<ResponseDTO> handleTransactionException(TransactionException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Lo sentimos, hubo un problema al procesar su solicitud. Por favor, inténtelo de nuevo más tarde.")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .error(true)
                .response("Ha ocurrido un error inesperado. Por favor, inténtelo de nuevo más tarde.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
