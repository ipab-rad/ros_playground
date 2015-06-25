package com.rad.rosplayground;

import android.content.Context;

import com.rad.rosjava_wrapper.publish.PublisherNode;

import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.node.NodeConfiguration;

import java.net.URI;

import static org.ros.node.NodeConfiguration.newPublic;

public abstract class PublisherModuleView extends ModuleView {

    public PublisherModuleView(Context context) {
        super(context);
    }

    public void executeNode(URI masterURI, PublisherNode publisherNode) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(masterURI);
        NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
        nodeMainExecutorService.execute(publisherNode, nodeConfiguration);
    }
}
