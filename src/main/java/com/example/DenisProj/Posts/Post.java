package com.example.DenisProj.Posts;

import java.time.LocalDateTime;
import java.util.Objects;

import com.example.DenisProj.Users.User;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Post() {}

    public Post(User username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return this.id;
      }
    
      public User getUsername() {
        return this.username;
      }
    
      public String getTitle() {
        return this.title;
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
    
      public void setTitle(String title) {
        if (content == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is not valid");
        }
        this.title = title;
      }
    
      public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content is not valid");
        }
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(username, post.username) &&
                Objects.equals(title, post.title) &&
                Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, title, content);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + username +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
