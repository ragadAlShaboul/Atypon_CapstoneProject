package com.atypon.bootstrappingNode.cluster.repository;

import com.atypon.bootstrappingNode.cluster.model.Message;
import com.atypon.bootstrappingNode.cluster.model.Observer;

public interface NodeRepo {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyUpdates(Message message);
}
