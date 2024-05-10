package com.atypon.workerNode.cluster.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Message {
    private final Object message;
    private final String query;

    public Message(Object message, String query) {
        this.message = message;
        this.query = query;
    }

    public void send(String toNode){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        System.out.println(toNode+query);
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(toNode+query))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(message.toString()))
                    .build();
            System.out.println(toNode+query+message);
            client.send(request , HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
