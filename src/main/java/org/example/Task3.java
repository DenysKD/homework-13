package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task3 {

    public static void incompletedTasks(int userId) throws IOException, InterruptedException {
        String userTaskReference = "https://jsonplaceholder.typicode.com/users/" + userId +"/todos";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userTaskReference))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

        Tasks[] tasks = gsonBuilder.fromJson(response.body(), Tasks[].class);

        System.out.println("All incompleted tasks:");

        for (Tasks task : tasks) {
            if(!task.isCompleted())
                System.out.println("task id= " + task.getId() + " ," + task.getTitle());
        }
    }

    public static void main(String[] args) {

        try {
            incompletedTasks(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public class Tasks{
        private int userId;
        private int id;
        private String title;
        private boolean completed;

        public Tasks(int userId, int id, String title, boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
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

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
