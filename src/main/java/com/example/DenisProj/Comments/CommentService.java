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
        return repository.findById(id)
                .map(comment -> {
                    comment.setContent(updatedComment.getContent());
                    return repository.save(comment);
                })
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
