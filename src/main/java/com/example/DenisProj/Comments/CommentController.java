package com.example.DenisProj.Comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.DenisProj.Users.UserService;
import com.example.DenisProj.Users.User;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Comment> getAllComments() {
        return service.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));
    }

    @PostMapping
    public Comment createComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Comment comment) {
        User user = userService.findByUsername(userDetails.getUsername());
        comment.setUser_id(user);
        return service.createComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody Comment updatedComment) {
        Comment existingComment = service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));

        User user = userService.findByUsername(userDetails.getUsername());

        if (!existingComment.getUser_id().equals(user)) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }

        updatedComment.setId(id);
        updatedComment.setUser_id(user); 
        return service.updateComment(id, updatedComment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Comment existingComment = service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));

        User user = userService.findByUsername(userDetails.getUsername());

        if (!existingComment.getUser_id().equals(user) && !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this comment");
        }

        service.deleteComment(id);
    }
}
