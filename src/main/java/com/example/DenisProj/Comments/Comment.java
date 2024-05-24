package com.example.DenisProj.Comments;

import java.time.LocalDateTime;
import java.util.Objects;

import com.example.DenisProj.Posts.Post;
import com.example.DenisProj.Users.User;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {}

    public Comment(User user_id, Post post, String content) {
        this.user_id = user_id;
        this.post = post;
        this.content = content;
    }

    public Long getId() {
        return this.id;
      }
    
      public User getUser_id() {
        return this.user_id;
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
    
      public void setUser_id(User user_id) {
        this.user_id = user_id;
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
                Objects.equals(user_id, comment.user_id) &&
                Objects.equals(post, comment.post) &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, post, content);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user_id +
                ", post=" + post +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
