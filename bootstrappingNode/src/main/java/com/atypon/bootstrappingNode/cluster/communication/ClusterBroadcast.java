package com.atypon.bootstrappingNode.cluster.communication;

import com.atypon.bootstrappingNode.cluster.repository.NodeRepository;
import com.atypon.bootstrappingNode.cluster.model.Message;

public class ClusterBroadcast {
    private static final NodeRepository nodeRepository=NodeRepository.getNodeRepository();
    public static void send(Message message){
        nodeRepository.notifyUpdates(message);
    }
}
