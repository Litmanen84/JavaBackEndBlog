package com.example.DenisProj.Comments;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DenisProj.Users.User;
import com.example.DenisProj.Users.UserRepository;
import com.example.DenisProj.Posts.Post;
import com.example.DenisProj.Posts.PostRepository;
import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return repository.findById(id);
    }

    public Comment createComment(String commentContent, Long userId, Long postId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (!userOptional.isPresent() || !postOptional.isPresent()) {
            throw new IllegalArgumentException("User or post not found");
        }

        User user = userOptional.get();
        Post post = postOptional.get();

        Comment newComment = new Comment(user, post, commentContent);

        return repository.save(newComment);
    }

    public Comment updateComment(Long id, String updatedComment) {
        Optional<Comment> existingComment = repository.findById(id);
        if (!existingComment.isPresent()) {
            throw new CommentNotFoundException("Comment not found with id " + id);
        }

        Comment commentToUpdate = existingComment.get();
        commentToUpdate.setContent(updatedComment);
        commentToUpdate.setCreatedAt(LocalDateTime.now());

        return repository.save(commentToUpdate);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
