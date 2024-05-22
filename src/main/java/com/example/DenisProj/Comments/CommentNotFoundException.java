package com.example.DenisProj.Comments;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Could not find comment " + id);
      }
}
