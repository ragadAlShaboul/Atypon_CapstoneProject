package com.atypon.workerNode.cluster.communication;

import com.atypon.workerNode.cluster.model.Message;

public class ClusterUniCast implements CommunicationStrategy{
    public void send(Message message, String nodeURL){
        message.send(nodeURL);
    }
}
