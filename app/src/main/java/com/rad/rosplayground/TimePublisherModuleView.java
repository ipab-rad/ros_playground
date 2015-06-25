package com.rad.rosplayground;

import android.content.Context;

import com.rad.rosjava_wrapper.publish.TimePublisherNode;

import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.node.NodeConfiguration;

import java.net.URI;

import static org.ros.node.NodeConfiguration.newPublic;

public class TimePublisherModuleView extends ModuleView {

    private TimePublisherNode timePublisherNode;

    public TimePublisherModuleView(Context context, URI masterURI) {
        super(context);
        timePublisherNode = new TimePublisherNode();
        executeNode(masterURI, timePublisherNode);
    }

    private void executeNode(URI masterURI, TimePublisherNode publisherNode) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(masterURI);
        NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
        nodeMainExecutorService.execute(publisherNode, nodeConfiguration);
    }
}
