package com.atypon.bootstrappingNode.cluster.model;


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
        this.message=message;
        this.query=query;
    }
    public void send(String nodeURL) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(nodeURL+query))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(message.toString()))
                    .build();
            System.out.println(nodeURL+query+message);
            client.send(request , HttpResponse.BodyHandlers.ofString());
            
        } catch (URISyntaxException|InterruptedException|IOException e) {
            e.printStackTrace();
        }
    }
}
