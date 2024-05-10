package com.atypon.workerNode.cluster.communication;

import com.atypon.workerNode.cluster.model.Message;

public interface CommunicationStrategy {
    public void send(Message message, String nodeURL);
}
