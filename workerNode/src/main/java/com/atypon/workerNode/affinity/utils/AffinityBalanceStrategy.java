package com.atypon.workerNode.affinity.utils;

import com.atypon.workerNode.cluster.model.Node;

public interface AffinityBalanceStrategy {
    public Node getAffinityNode();
}
