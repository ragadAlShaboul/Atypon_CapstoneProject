package com.atypon.workerNode.cluster.service;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.cluster.repository.NodeRepository;


public class BootstrapService {
    NodeRepository nodeRepository=NodeRepository.getNodeRepository();

    public void setNodes(int numOfNodes){
        System.out.println(numOfNodes);
        nodeRepository.setNodes(numOfNodes);
    }
    public void setCurrentNode(String currentNode){
        Node node=NodeRepository.getNodeRepository().getNodes().get(Integer.parseInt(currentNode));
        System.out.println(node.getURL());
        nodeRepository.setCurrentNode(node);
    }

}
