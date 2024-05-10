package com.atypon.bootstrappingNode.cluster.repository;


import com.atypon.bootstrappingNode.cluster.model.Node;
import com.atypon.bootstrappingNode.cluster.model.Message;
import com.atypon.bootstrappingNode.cluster.model.Observer;

import java.util.ArrayList;


public class NodeRepository implements NodeRepo{
    private final ArrayList<Node> nodes=new ArrayList<>();
    private static final NodeRepository nodeRepository=new NodeRepository();
    private int numOfNodes;
    private NodeRepository(){}
    public void registerObserver(Observer observer) {
        nodes.add((Node) observer);
    }

    public void removeObserver(Observer observer) {
        nodes.remove(observer);
    }
    @Override
    public void notifyUpdates(Message message) {
        for (Node node:nodes)
            node.update(message);
    }
    public void initializeNodes(int numOfNodes){
        this.numOfNodes=numOfNodes;
        for (int i = 0; i< numOfNodes; i++){
            Node node = new Node("workernode"+i,8080);
            node.setUserURL("http://localhost:808"+i);
            registerObserver(node);
        }
    }
    public void sendNodeInfo(){
        for (Node node:nodes)
            node.update(new Message(nodes.indexOf(node),"/nodeInfo"));
    }
    public Node getNode(int index){
        return nodes.get(index);
    }
    public static NodeRepository getNodeRepository(){
        return nodeRepository;
    }
    public int getNumOfNodes() {
        return numOfNodes;
    }
}
