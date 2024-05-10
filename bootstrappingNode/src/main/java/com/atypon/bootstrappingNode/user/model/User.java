package com.atypon.bootstrappingNode.user.model;

import com.atypon.bootstrappingNode.cluster.model.Node;
import com.atypon.bootstrappingNode.loadbalance.LoadBalancer;
import com.atypon.bootstrappingNode.loadbalance.LoadBalanceStrategy;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private static int globalId = 0;
    private final String username;
    private final String password;
    private final int ID;
    private final Node commNode;
    private LoadBalanceStrategy loadBalanceStrategy;

    public User(String username, String password) {
        this.ID = globalId++;
        this.username = username;
        this.password = password;
        setLoadBalanceStrategy(new LoadBalancer());
        this.commNode = loadBalanceStrategy.getUserNode();
    }

    public String getUsername() {
        return username;
    }

    private void setLoadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    public String getPassword() {
        return password;
    }

    public Node getCommNode() {
        return commNode;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", ID);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("commNode", commNode.getURL());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject.toString();
    }

}
