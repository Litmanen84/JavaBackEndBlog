package com.example.DenisProj.Posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.DenisProj.Users.User;    
import com.example.DenisProj.Users.UserService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return service.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Post createPost(@RequestBody CreatePostRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && user.getIs_admin()) {
            return service.createPost(request.getTitle(), request.getContent(), request.getUsername());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can create posts");
        }
    }

    @PutMapping("/{id}")
    public Post updatePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody Post updatedPost) {
        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can update posts");
        }
        return service.updatePost(id, updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can delete posts");
        }
        service.deletePost(id);
    }
}
