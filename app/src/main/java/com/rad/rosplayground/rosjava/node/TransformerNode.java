package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsg;
import com.rad.rosplayground.rosjava.msg.listeners.MessageRelayTransform;
import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.apache.commons.lang.StringUtils;
import org.ros.master.client.TopicType;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

//TODO shutdown node properly
public class TransformerNode extends AbstractNodeMain {

    private TopicType topicToPublishTo;
    private TopicType topicToSubscribeTo;
    private ITransformMsg transformFunction;

    public TransformerNode(TopicType topicToPublishTo, TopicType topicToSubscribeTo, ITransformMsg transformFunction){
        this.topicToPublishTo = topicToPublishTo;
        this.topicToSubscribeTo = topicToSubscribeTo;
        this.transformFunction = transformFunction;
    }

    @Override
    public GraphName getDefaultNodeName() {
        String[] stringList = new String[]{
                "TransformerNode",
                "From",
                topicToSubscribeTo.getName(),
                transformFunction.getName()
        };
        return GraphName.of(StringUtils.join(stringList, "_"));
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<Object> publisher = ROSUtils.addPublisher(connectedNode, topicToPublishTo);
        Subscriber<Object> subscriber = ROSUtils.addSubscriber(connectedNode, topicToSubscribeTo);
        addMessageRelayTransform(publisher, subscriber, transformFunction);
    }

    private void addMessageRelayTransform(Publisher<Object> publisher, Subscriber<Object> subscriber, ITransformMsg transformFunction) {
        subscriber.addMessageListener(new MessageRelayTransform<>(publisher, transformFunction));
    }
}
