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

        PostInfo[] allPosts = gsonBuilder.fromJson(response.body(), PostInfo[].class);

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
}
