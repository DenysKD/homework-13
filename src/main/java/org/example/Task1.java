package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task1 {
    public static final String SOURCE_USERS_URL = "https://jsonplaceholder.typicode.com/users";

    public static void registerUser(User user) throws IOException, InterruptedException {
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SOURCE_USERS_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gsonBuilder.toJson(user)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println("response.statusCode() = " + response.statusCode());
        //System.out.println("response.body() = " + response.body());

    }

    public static void updateUser(int userId, String parametr, String update) throws IOException, InterruptedException {
        String userReference = "https://jsonplaceholder.typicode.com/users/" + userId;
        HttpClient client = HttpClient.newHttpClient();

        String updateJson = String.format("""
        {
          "%s": "%s"
        }
        """, parametr, update);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userReference))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(updateJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void deleteUser(int userId) throws IOException, InterruptedException {
        String SOURCE_user_URL = "https://jsonplaceholder.typicode.com/users/" + userId;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SOURCE_user_URL))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void allUsersInfo() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SOURCE_USERS_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void userInfoId(int userId) throws IOException, InterruptedException {
        String userReference = "https://jsonplaceholder.typicode.com/users/" + userId;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userReference))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void userInfoUsername(String userName) throws IOException, InterruptedException {
        String userReference = "https://jsonplaceholder.typicode.com/users?username=" + userName;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(userReference))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    static void main(String[] args) {

        User user = new User("Ivan", "Ivanov", 95);

        try {
            registerUser(user);
            updateUser(5,"email", "qwdqwdqdqw");
            deleteUser(3);
            allUsersInfo();
            userInfoId(4);
            userInfoUsername("Antonette");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class User{
        private String name;
        private String surname;
        private int age;

        public User(String name, String surname, int age) {
            this.name = name;
            this.surname = surname;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}