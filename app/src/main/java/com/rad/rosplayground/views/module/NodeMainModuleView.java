package com.rad.rosplayground.views.module;

import android.content.Context;

import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;

import java.net.URI;

import static org.ros.node.NodeConfiguration.newPublic;

public abstract class NodeMainModuleView extends ModuleView {

    public NodeMainModuleView(Context context) {
        super(context);
    }

    public void executeNode(URI masterURI, NodeMain node) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(masterURI);
        NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
        nodeMainExecutorService.execute(node, nodeConfiguration);
    }
}
