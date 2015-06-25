package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.PublishingData;

import org.ros.concurrent.CancellableLoop;
import org.ros.master.client.TopicType;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

//TODO shutdown node properly
//TODO test
public class DataPublisherNode extends AbstractNodeMain {

    private TopicType topicToPublishTo;
    private Publisher<Object> publisher;
    private int rateToPublishAt;
    PublishingData dataToPublish;

    public DataPublisherNode(TopicType topicToPublishTo, int rateToPublishAt, PublishingData dataToPublish){
        this.topicToPublishTo = topicToPublishTo;
        this.rateToPublishAt = rateToPublishAt;
        this.dataToPublish = dataToPublish;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("DataPublisherNode_" + topicToPublishTo.getName());
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher(GraphName.of(topicToPublishTo.getName()), topicToPublishTo.getMessageType());
        CancellableLoop loop = new CancellableLoop() {
            protected void loop() throws InterruptedException {
                publisher.publish(dataToPublish.getDataMsg());
                Thread.sleep(rateToPublishAt);
            }
        };
        connectedNode.executeCancellableLoop(loop);
    }
}
