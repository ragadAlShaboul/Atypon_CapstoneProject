package com.atypon.bootstrappingNode.loadbalance;

import com.atypon.bootstrappingNode.cluster.model.Node;

public interface LoadBalanceStrategy {
    public Node getUserNode();
}
