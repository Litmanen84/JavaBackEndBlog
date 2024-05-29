package com.example.DenisProj.Comments;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.DenisProj.Users.UserService;
import com.example.DenisProj.Users.User;
import com.example.DenisProj.Posts.Post;
import com.example.DenisProj.Posts.PostService; 

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Comment> getAllComments() {
        return service.getAllComments();
    }

    @GetMapping("/post/{id}")
    public List<Comment> getCommentsByPostId(@PathVariable Long id) {
        return service.getCommentsByPostId(id);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));
    }

    @PostMapping
    public Comment createComment(@RequestBody CreateCommentRequest request) {
        User user = userService.findByUsername(request.getUsername());
        Post post = postService.getPostById(request.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if (user != null && post != null) {
            return service.createComment(request.getComment(), user.getId(), post.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You should login to create a comment");
        }
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest request) {
        User user = userService.findByUsername(request.getUsername());
        Comment existingComment = service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));
        if (!existingComment.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }
        return service.updateComment(id, request.getUpdatedComment());
    }
 
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody DeleteCommentRequest request) {
        User user = userService.findByUsername(request.getUsername());    
        Comment existingComment = service.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + id));
        if (!existingComment.getUser().equals(user) && !user.getIs_admin()) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this comment");
                }
        service.deleteComment(id);
    }
}