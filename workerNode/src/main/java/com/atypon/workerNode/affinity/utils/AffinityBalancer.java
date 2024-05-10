package com.atypon.workerNode.affinity.utils;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.cluster.repository.NodeRepository;


public class AffinityBalancer implements AffinityBalanceStrategy {
    private static final NodeRepository nodeRepository=NodeRepository.getNodeRepository();
    private static final int numOfNodes=nodeRepository.getNumOfNodes();
    private static int numOfDocuments=0;
    public Node getAffinityNode(){
        int nodeNumber = numOfDocuments % numOfNodes;
        numOfDocuments++;
        return nodeRepository.getNode(nodeNumber);
    }
}
