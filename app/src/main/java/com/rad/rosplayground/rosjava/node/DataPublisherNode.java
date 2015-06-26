package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.rosjava.msg.data.PublishingData;
import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.apache.commons.lang.StringUtils;
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
        String[] stringList = new String[]{
                "DataPublisherNode",
                topicToPublishTo.getName(),
        };
        return GraphName.of(StringUtils.join(stringList, "_"));
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        publisher = ROSUtils.addPublisher(connectedNode, topicToPublishTo);
        ROSUtils.executeCancellableLoop(connectedNode, publisher, dataToPublish, rateToPublishAt);
    }
}
