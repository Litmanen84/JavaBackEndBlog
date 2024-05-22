package com.example.DenisProj.Comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping
    public List<Comment> getAllComments() {
        return service.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @PostMapping
    public Comment createComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Comment comment) {
        return service.createComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody Comment updatedComment) {
        return service.updateComment(id, updatedComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete comments");
        }
        service.deleteComment(id);
    }
}
