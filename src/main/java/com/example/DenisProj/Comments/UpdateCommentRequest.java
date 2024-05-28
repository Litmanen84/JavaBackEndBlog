package com.example.DenisProj.Comments;

public class UpdateCommentRequest {
        private String updatedComment;
        private String username;
    
        public String getUpdatedComment() {
            return updatedComment;
        }
    
        public void setUpdatedComment(String updatedComment) {
            this.updatedComment = updatedComment;
        }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
}
