package com.atypon.bootstrappingNode.initialization;


import com.atypon.bootstrappingNode.cluster.communication.ClusterBroadcast;
import com.atypon.bootstrappingNode.cluster.model.Message;
import com.atypon.bootstrappingNode.cluster.repository.NodeRepository;
import com.atypon.bootstrappingNode.user.repository.UsersRepository;

public class InitializationV1 extends Initialization{
    NodeRepository nodeRepository=NodeRepository.getNodeRepository();
    @Override
    public void updateUsersList() {
        UsersRepository.updateUsersList();
    }

    @Override
    public void initializeNodes(int numOfNodes) {
        nodeRepository.initializeNodes(numOfNodes);
    }

    @Override
    public void broadcastNodesInfo(int numOfNodes) {
        ClusterBroadcast.send(new Message(numOfNodes,"/numberOfNodes"));;
    }

    @Override
    public void uniCastNodeInfo() {
        nodeRepository.sendNodeInfo();
    }

    @Override
    public void broadcastUsersInfo() {
        UsersRepository.sendUsersInfo();
    }
}

