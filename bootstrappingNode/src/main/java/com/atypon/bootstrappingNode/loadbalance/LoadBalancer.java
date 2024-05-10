package com.atypon.bootstrappingNode.loadbalance;

import com.atypon.bootstrappingNode.cluster.repository.NodeRepository;
import com.atypon.bootstrappingNode.cluster.model.Node;

public class LoadBalancer implements LoadBalanceStrategy {
    private static int numOfUsers = 0;
    private static final NodeRepository nodeRepository=NodeRepository.getNodeRepository();
    private static final int numOfNodes = nodeRepository.getNumOfNodes();
    public Node getUserNode(){
        int nodeNumber = numOfUsers++ % numOfNodes;
        return nodeRepository.getNode(nodeNumber);
    }
}
