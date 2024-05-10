package com.atypon.bootstrappingNode.initialization;


import org.springframework.stereotype.Component;

@Component
public abstract class Initialization {
    public final void initialize(int numOfNodes){
        initializeNodes(numOfNodes);
        updateUsersList();
        broadcastNodesInfo(numOfNodes);
        uniCastNodeInfo();
        broadcastUsersInfo();
        System.out.println("all initialized");
    }
    public abstract void updateUsersList();
    public abstract void initializeNodes(int numOfNodes);
    public abstract void broadcastNodesInfo(int numOfNodes);
    public abstract void uniCastNodeInfo();
    public abstract void broadcastUsersInfo();
}
