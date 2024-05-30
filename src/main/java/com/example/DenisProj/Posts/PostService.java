package com.example.DenisProj.Posts;

import java.util.List;
import java.util.Optional;
import com.example.DenisProj.Users.User;
import com.example.DenisProj.Users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return repository.findById(id);
    }

    public Post createPost(String title, String content, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found for userId: " + userId);
        }
    
        User user = userOptional.get();
    
        Post post = new Post();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
    
        return repository.save(post);
    }

    public Post updatePost(Long id, String title, String content) {
        return repository.findById(id)
                .map(post -> {
                    post.setTitle(title);
                    post.setContent(content);
                    return repository.save(post);
                })
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public void deletePost(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new PostNotFoundException(id);
        }
    }
}
