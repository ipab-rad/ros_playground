package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.rosjava.msg.listeners.MessageRelay;
import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.apache.commons.lang.StringUtils;
import org.ros.master.client.TopicType;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

//TODO shutdown node properly
public class RelayNode extends AbstractNodeMain {

    private TopicType topicToPublishTo;
    private TopicType topicToSubscribeTo;

    public RelayNode(TopicType topicToPublishTo, TopicType topicToSubscribeTo){
        this.topicToPublishTo = topicToPublishTo;
        this.topicToSubscribeTo = topicToSubscribeTo;
    }

    @Override
    public GraphName getDefaultNodeName() {
        String[] stringList = new String[]{
                "RelayNode",
                "From",
                topicToSubscribeTo.getName(),
                "To",
                topicToPublishTo.getName()
        };
        return GraphName.of(StringUtils.join(stringList, "_"));
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<Object> publisher = ROSUtils.addPublisher(connectedNode, topicToPublishTo);
        Subscriber<Object> subscriber = ROSUtils.addSubscriber(connectedNode, topicToSubscribeTo);
        addMessageRelay(publisher, subscriber);
    }

    private void addMessageRelay(Publisher<Object> publisher, Subscriber<Object> subscriber) {
        subscriber.addMessageListener(new MessageRelay<>(publisher));
    }
}
