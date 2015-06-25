package com.rad.rosplayground.views.module;

import android.content.Context;

import com.rad.rosplayground.rosjava.msg.Actor;
import com.rad.rosplayground.rosjava.node.SubscriberNode;

import org.ros.master.client.TopicType;

import java.net.URI;

public class SubscriberModuleView extends NodeMainModuleView {

    private SubscriberNode subscriberNode;

    public SubscriberModuleView(Context context, URI masterURI, TopicType topicToSubscribeTo, Actor actor) {
        super(context);
        subscriberNode = new SubscriberNode(topicToSubscribeTo, actor);
        executeNode(masterURI, subscriberNode);
    }
}
