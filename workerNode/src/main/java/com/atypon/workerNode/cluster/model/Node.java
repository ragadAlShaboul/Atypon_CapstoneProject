package com.atypon.workerNode.cluster.model;

public class Node implements Observer {
    private final String name;
    private final int port;
    public Node(String name, int port) {
        this.name = name;
        this.port = port;
    }
    @Override
    public void update(Message message) {
        message.send(getURL());
    }
    public String getURL(){
        return "http://"+name+":"+port;
    }
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\''+
                ", port='" + port + '\'' +
                '}';
    }
}
