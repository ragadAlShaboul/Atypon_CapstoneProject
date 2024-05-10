package com.atypon.workerNode.cluster.communication;

import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.cluster.model.Message;
public class ClusterBroadcast {
    private static NodeRepository nodeRepository=NodeRepository.getNodeRepository();
    public void send(Message message){
        nodeRepository.notifyUpdates(message);
    }
}
