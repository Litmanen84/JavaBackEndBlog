package com.example.DenisProj.Comments;

import java.time.LocalDateTime;
import java.util.Objects;

import com.example.DenisProj.Posts.Post;
import com.example.DenisProj.Users.User;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {}

    public Comment(User username, Post post, String content) {
        this.username = username;
        this.post = post;
        this.content = content;
    }

    public Long getId() {
        return this.id;
      }
    
      public User getUsername() {
        return this.username;
      }
    
      public Post getPost() {
        return this.post;
      }
    
      public String getContent() {
        return this.content;
      }
    
      public void setId(Long id) {
        this.id = id;
      }
    
      public void setUsername(User username) {
        this.username = username;
      }
    
      public void setPost(Post post) {
        this.post = post;
      }
    
      public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Comment is not valid");
        }
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(username, comment.username) &&
                Objects.equals(post, comment.post) &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, post, content);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + username +
                ", post=" + post +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
