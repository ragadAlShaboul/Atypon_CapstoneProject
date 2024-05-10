package com.atypon.workerNode.cluster.repository;

import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.model.Observer;

public interface NodeRepo {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyUpdates(Message message);
}
