package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2 {

    public static void lastPostChosenUserComments(int userId) throws IOException, InterruptedException {
        String userReference = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userReference))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

        postsInfo[] allPosts = gsonBuilder.fromJson(response.body(), postsInfo[].class);

        int lastPostId = allPosts[allPosts.length - 1].getId();

        String postCommentsReference = "https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments";

        request = HttpRequest.newBuilder()
                .uri(URI.create(postCommentsReference))
                .header("Content-Type", "application/json")
                .GET()
                .build();



        CommentsInfo[] allComments = gsonBuilder.fromJson(response.body(), CommentsInfo[].class);

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Path path = Path.of("src\\main\\resources\\comments from user id= " + userId + ".txt");

        try(BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (CommentsInfo comment : allComments) {
                if (comment.getId() == lastPostId)
                    bw.append(comment.getBody() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        try {
            lastPostChosenUserComments(9);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static class postsInfo {
        private int userId;
        private int id;
        private String title;
        private String body;

        public postsInfo(int userId, int id, String title, String body) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.body = body;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    static class CommentsInfo {
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
}
