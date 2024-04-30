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

/**
 * This class is responsible for handling exceptions globally across the application.
 * It uses Spring's @RestControllerAdvice annotation to provide centralized exception handling across all @RequestMapping methods.
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles validation exceptions when request parameters are not valid.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
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

    /**
     * Handles exceptions when the HTTP request body is not readable.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(HttpMessageNotReadableException.class)
     public ResponseEntity<ResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("La solitud no pudo ser procesada")
                    .build(), HttpStatus.BAD_REQUEST);
     }

    /**
     * Handles exceptions when there is a violation of data integrity.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.CONFLICT);
        }

    /**
     * Handles exceptions when an entity already exists in the database.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(EntityAlreadyExistsException.class)
        public ResponseEntity<ResponseDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.CONFLICT);
        }

    /**
     * Handles exceptions when an entity is not found in the database.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.NOT_FOUND);
        }

    /**
     * Handles global exceptions.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(GlobalException.class)
        public ResponseEntity<ResponseDTO> handleGlobalException(GlobalException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    /**
     * Handles exceptions when an illegal argument is provided.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Argumento inválido")
                    .build(), HttpStatus.BAD_REQUEST);
        }

    /**
     * Handles exceptions when no results are found for a query.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<ResponseDTO> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("No se encontraron resultados")
                    .build(), HttpStatus.NOT_FOUND);
        }

    /**
     * Handles exceptions when there is an error accessing data.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<ResponseDTO> handleDataAccessException(DataAccessException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Error de acceso a los datos")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    /**
     * Handles exceptions when there is a transaction error.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
     @ExceptionHandler(TransactionException.class)
        public ResponseEntity<ResponseDTO> handleTransactionException(TransactionException ex) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .error(true)
                    .response("Lo sentimos, hubo un problema al procesar su solicitud. Por favor, inténtelo de nuevo más tarde.")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    /**
     * Handles all other exceptions not explicitly handled by other @ExceptionHandler methods in this class.
     * @param ex the exception
     * @return a response entity with a custom error message and HTTP status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .error(true)
                .response("Ha ocurrido un error inesperado. Por favor, inténtelo de nuevo más tarde.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
