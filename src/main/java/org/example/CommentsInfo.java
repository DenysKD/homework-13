package org.example;

public class CommentsInfo {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public CommentsInfo(int postId, String name, int id, String email, String body) {
        this.postId = postId;
        this.name = name;
        this.id = id;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}