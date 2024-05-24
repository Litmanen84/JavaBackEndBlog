package com.example.DenisProj.Comments;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return repository.findById(id);
    }

    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Optional<Comment> existingComment = repository.findById(id);
        if (!existingComment.isPresent()) {
            throw new CommentNotFoundException("Comment not found with id " + id);
        }
        updatedComment.setId(id);
        return repository.save(updatedComment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
