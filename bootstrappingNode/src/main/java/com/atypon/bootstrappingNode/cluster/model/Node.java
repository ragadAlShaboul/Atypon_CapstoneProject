package com.atypon.bootstrappingNode.cluster.model;

public class Node implements Observer{
    private final String name;
    private final int port;
    private String userURL;
    public Node(String name, int port) {
        this.name = name;
        this.port = port;
    }
    public void setUserURL(String userURL){
        this.userURL=userURL;
    }
    public String getUserURL() {
        return userURL;
    }
    public String getURL(){
        return "http://"+name+":"+port;
    }
    @Override
    public String toString() {
        return name+","+port;
    }

    @Override
    public void update(Message message) {
        message.send(getURL());
    }
}
