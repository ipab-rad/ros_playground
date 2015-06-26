package com.rad.rosplayground.rosjava.utils;

import com.rad.rosplayground.rosjava.msg.data.PublishingData;

import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.concurrent.CancellableLoop;
import org.ros.master.client.TopicType;
import org.ros.message.MessageFactory;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import java.net.URI;

import static org.ros.node.NodeConfiguration.newPublic;

public class ROSUtils {
    public static MessageFactory getMessageFactory() {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPrivate();
        return nodeConfiguration.getTopicMessageFactory();
    }

    public static Publisher<Object> addPublisher(ConnectedNode connectedNode, TopicType topicToPublishTo) {
        return connectedNode.newPublisher(GraphName.of(topicToPublishTo.getName()), topicToPublishTo.getMessageType());
    }

    public static Subscriber<Object> addSubscriber(ConnectedNode connectedNode, TopicType topicToSubscribeTo) {
        return connectedNode.newSubscriber(topicToSubscribeTo.getName(), topicToSubscribeTo.getMessageType());
    }

    public static void executeCancellableLoop(ConnectedNode connectedNode, final Publisher<Object> publisher, final PublishingData dataToPublish, final int rateToPublishAt) {
        CancellableLoop loop = new CancellableLoop() {
            protected void loop() throws InterruptedException {
                publisher.publish(dataToPublish.getDataMsg());
                Thread.sleep(rateToPublishAt);
            }
        };
        connectedNode.executeCancellableLoop(loop);
    }

    public static void executeNode(URI masterURI, NodeMain node) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(masterURI);
        NodeMainExecutorService nodeMainExecutorService = new NodeMainExecutorService();
        nodeMainExecutorService.execute(node, nodeConfiguration);
    }
}
