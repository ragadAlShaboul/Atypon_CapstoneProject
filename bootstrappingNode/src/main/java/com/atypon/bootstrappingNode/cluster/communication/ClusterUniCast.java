package com.atypon.bootstrappingNode.cluster.communication;

import com.atypon.bootstrappingNode.cluster.model.Message;

public class ClusterUniCast {
    public static void send(Message message, String nodeURL){
        message.send(nodeURL);
    }
}
