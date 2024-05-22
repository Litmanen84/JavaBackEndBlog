package com.example.DenisProj.Comments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentNotFoundAdvice {
    @ExceptionHandler(CommentNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String commentNotFoundHandler(CommentNotFoundException ex) {
    return ex.getMessage();
  }
}
