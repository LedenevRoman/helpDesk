package com.training.rledenev.controllers.exceptions;

import com.training.rledenev.exception.WrongFileSize;
import com.training.rledenev.exception.WrongFileType;
import com.training.rledenev.exception.WrongPasswordException;
import com.training.rledenev.model.error.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorModel> handleRuntimeException(RuntimeException exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorModel> handlePageNotFound(NoHandlerFoundException exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.NOT_FOUND,
                "404 NOT FOUND.", exception.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorModel> handleEntityNotFound(EntityNotFoundException exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.NO_CONTENT,
                 exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorModel> handleWrongLoginOrPassword(WrongPasswordException exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.FORBIDDEN,
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(WrongFileType.class)
    public ResponseEntity<ErrorModel> handleWrongType(WrongFileType exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.BAD_REQUEST,
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongFileSize.class)
    public ResponseEntity<ErrorModel> handleWrongSize(WrongFileSize exception) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.BAD_REQUEST,
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = getMessage(exception);
        ErrorModel errorModel = new ErrorModel(HttpStatus.BAD_REQUEST, message, exception.toString());
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return fieldErrors.stream()
                .findAny()
                .orElseThrow(NoSuchFieldError::new)
                .getDefaultMessage();
    }
}
