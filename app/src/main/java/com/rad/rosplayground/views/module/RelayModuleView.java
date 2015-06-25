package com.rad.rosplayground.views.module;

import android.content.Context;

import com.rad.rosplayground.rosjava.node.RelayNode;

import org.ros.master.client.TopicType;

import java.net.URI;

public class RelayModuleView extends NodeMainModuleView {

    private RelayNode relayNode;

    public RelayModuleView(Context context, URI masterURI, TopicType topicToPublishTo, TopicType topicToSubscribeTo) {
        super(context);
        relayNode = new RelayNode(topicToPublishTo, topicToSubscribeTo);
        executeNode(masterURI, relayNode);
    }
}
