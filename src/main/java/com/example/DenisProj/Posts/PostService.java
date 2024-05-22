package com.example.DenisProj.Posts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return repository.findById(id);
    }

    public Post createPost(Post post) {
        return repository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost) {
        return repository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return repository.save(post);
                })
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
