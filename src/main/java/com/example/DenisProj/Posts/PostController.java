package com.example.DenisProj.Posts;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.DenisProj.Comments.UnauthorizedException;
import com.example.DenisProj.Users.User;    
import com.example.DenisProj.Users.UserService;


@CrossOrigin(origins = "http://127.0.0.1:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "false")
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return service.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody CreatePostRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && user.getIs_admin()) {
            return service.createPost(request.getTitle(), request.getContent(), user.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can create posts");
        }
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        User user = userService.findByUsername(request.getUsername());
        Post existingPost = service.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        if (user != null && existingPost != null && user.getIs_admin()) {
            existingPost.setContent(request.getUpdatedPost());
            service.updatePost(id, existingPost);
        }
        else {
            throw new UnauthorizedException("User is not authorized to update this post");
        }
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @RequestBody DeletePostRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && user.getIs_admin()) {
        service.deletePost(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete posts");
        }
    }
}
