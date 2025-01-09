package com.web.service.presentation.controller;

import com.web.service.domain.exception.*;
import com.web.service.presentation.viewModel.ApiResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.ConnectException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ListEmptyException.class)
    public ResponseEntity<ApiResponse> EmptyListResponse(ListEmptyException e) {
        return ResponseEntity.ok().body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> EntityNotFoundResponse(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ApiResponse> ConnectException(CannotCreateTransactionException e) {
        return ResponseEntity.status(400).body(new ApiResponse("Conexão Perdida com o Servidor de Banco de dados!",e.getMessage()) );
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> UsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> IOException(IOException e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ApiResponse> ServletException(ServletException e) {
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> JwtException(JwtException e) {
        return ResponseEntity.status(401).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> Exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(400).body(new ApiResponse("Erro Inserperado Ocorreu!",e.getMessage()) );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse> ValidationException(ValidationException e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),null) );
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> AlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),null) );
    }

    @ExceptionHandler(UnprocessableAction.class)
    public ResponseEntity<ApiResponse> UnprocessableAction(UnprocessableAction e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),null) );
    }
    @ExceptionHandler(OrderProcessError.class)
    public ResponseEntity<ApiResponse> OrderProcessError(OrderProcessError e) {
        return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(),null) );
    }


}
