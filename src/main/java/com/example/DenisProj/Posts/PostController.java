package com.example.DenisProj.Posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.DenisProj.config.CustomUserDetails;

import org.springframework.http.HttpStatus;

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

    @PostMapping
    public Post createPost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Post post) {
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            if (!customUserDetails.isAdmin()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can create posts");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User details are not of expected type");
        }
        return service.createPost(post);
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
