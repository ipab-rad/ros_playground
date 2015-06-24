package com.rad.rosplayground.rosjava;

import org.ros.address.InetAddressFactory;
import org.ros.master.client.MasterStateClient;
import org.ros.node.DefaultNodeFactory;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeListener;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.util.ArrayList;

import static org.ros.node.NodeConfiguration.newPublic;

public class MasterStateClientFactory {

    private static MasterStateClient masterStateClient;

    public static MasterStateClient getInstance(NodeMainExecutor nodeMainExecutor, URI masterUri){
        if(masterStateClient == null){
            masterStateClient = makeMasterStateClient(nodeMainExecutor, masterUri);
        }
        return masterStateClient;
    }

    private static MasterStateClient makeMasterStateClient(NodeMainExecutor nodeMainExecutor, URI masterUri) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setDefaultNodeName("MasterStateClient/masterListener");
        nodeConfiguration.setMasterUri(masterUri);
        DefaultNodeFactory defaultNodeFactory = new DefaultNodeFactory(nodeMainExecutor.getScheduledExecutorService());
        Node host = defaultNodeFactory.newNode(nodeConfiguration, new ArrayList<NodeListener>());
        return new MasterStateClient(host, masterUri);
    }
}
