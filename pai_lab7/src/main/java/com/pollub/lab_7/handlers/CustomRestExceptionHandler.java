package com.pollub.lab_7.handlers;

import lombok.Builder;
import lombok.Singular;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        var msg = ex.getLocalizedMessage();
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).error(msg).build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.status(), request);
    }
}

@Builder
record ApiError(HttpStatus status, @Singular List<String> errors) {
}