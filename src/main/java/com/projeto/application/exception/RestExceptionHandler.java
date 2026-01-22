package com.projeto.application.exception;

import jakarta.transaction.TransactionalException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(RuntimeException runtimeException){
        return ResponseEntity.internalServerError().body(runtimeException.getMessage());
    }

    @ExceptionHandler(value = TransactionalException.class)
    public ResponseEntity<String> transactionalExceptionHandler(TransactionalException transactionalException){
        return ResponseEntity.internalServerError().body(transactionalException.getMessage());
    }
}
