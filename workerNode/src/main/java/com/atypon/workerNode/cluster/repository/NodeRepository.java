package com.atypon.workerNode.cluster.repository;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.model.Observer;

import java.util.ArrayList;
import java.util.List;

public class NodeRepository implements NodeRepo {
    private final ArrayList<Node> nodes = new ArrayList<Node>();
    private static final NodeRepository nodeRepository = new NodeRepository();
    private static Node currentNode=new Node("n",0);
    private int numOfNodes=0;
    private NodeRepository() {
    }
    public void setNodes(int n) {
        numOfNodes=n;
        for (int i = 0; i < numOfNodes; i++) {
            registerObserver(new Node("workernode" + i, 8080));
        }
    }
    public void registerObserver(Observer observer) {
        nodes.add((Node) observer);
    }

    public void removeObserver(Observer observer) {
        nodes.remove(observer);
    }
    public void setCurrentNode(Node node){
        currentNode=node;
    }
    public List<Node> getNodes() {
        return nodes;
    }
    public static String getCurrentNode() {
        return currentNode.getURL();
    }

    public static NodeRepository getNodeRepository() {
        return nodeRepository;
    }
    public int getNodeNumber(Node node){
        return nodes.indexOf(node);
    }

    public int getNumOfNodes() {
        return numOfNodes;
    }
    public Node getNode(int num){
        return nodes.get(num);
    }
    @Override
    public void notifyUpdates(Message message) {
        for (Node node:nodes)
            if (!node.getURL().equals(currentNode.getURL())) {
                node.update(message);
                System.out.println(node.getURL()+" notified");
            }
    }
}
