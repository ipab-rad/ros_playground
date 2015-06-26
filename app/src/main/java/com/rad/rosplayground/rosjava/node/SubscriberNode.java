package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.rosjava.msg.actors.Actor;
import com.rad.rosplayground.rosjava.msg.listeners.MessageActor;

import org.ros.master.client.TopicType;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

public class SubscriberNode extends AbstractNodeMain{
    private TopicType topicToSubscribeTo;
    private Actor actor;

    public SubscriberNode(TopicType topicToSubscribeTo, Actor actor) {
        this.topicToSubscribeTo = topicToSubscribeTo;
        this.actor = actor;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SubscriberNode_" + topicToSubscribeTo.getName());
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<Object> subscriber = connectedNode.newSubscriber(topicToSubscribeTo.getName(), topicToSubscribeTo.getMessageType());
        addMessageActor(subscriber);
    }

    private void addMessageActor(Subscriber<Object> subscriber) {
        subscriber.addMessageListener(new MessageActor<>(actor));
    }
}
