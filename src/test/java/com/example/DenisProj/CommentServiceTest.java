package com.example.DenisProj;

import java.util.List;
import com.example.DenisProj.Comments.Comment;
import com.example.DenisProj.Comments.CommentDTO;
import com.example.DenisProj.Users.User;
import com.example.DenisProj.Users.UserRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.DenisProj.Comments.CommentRepository;
import com.example.DenisProj.Comments.CommentService;
import com.example.DenisProj.Posts.PostRepository;
import java.util.Arrays;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.DenisProj.Posts.Post;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService service;

    @Mock
    private CommentRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    public void testGetCommentsByPostId() {
        Long postId = 1L;
        
        User user = new User();
        user.setUsername("testuser");
        Post post = new Post();
        Comment comment = new Comment(user, post, "Test content");
        comment.setId(1L);

        List<Comment> comments = Arrays.asList(comment);
        when(repository.findByPostId(postId)).thenReturn(comments);

        List<CommentDTO> result = service.getCommentsByPostId(postId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        assertEquals("Test content", result.get(0).getContent());
    }

    @Test
    public void testGetCommentById() {
        Long commentId = 1L;
        Comment comment = new Comment();
        when(repository.findById(commentId)).thenReturn(Optional.of(comment));

        Optional<Comment> result = service.getCommentById(commentId);
        assertTrue(result.isPresent());
        assertEquals(comment, result.get());
    }

    @Test
    public void testCreateComment() {
        String content = "This is a comment";
        Long userId = 1L;
        Long postId = 1L;
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment(user, post, content);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(repository.save(any(Comment.class))).thenReturn(comment);

        Comment result = service.createComment(content, userId, postId);
        assertNotNull(result);
        assertEquals(content, result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(post, result.getPost());
    }
}
