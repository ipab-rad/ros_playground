package com.rad.rosplayground.rosjava.node;

import com.rad.rosplayground.rosjava.msg.MessageRelay;

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
        //TODO check that these topics have the same type. Either here or restrict the options when creating a relay node
        this.topicToPublishTo = topicToPublishTo;
        this.topicToSubscribeTo = topicToSubscribeTo;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("RelayNode_From_" + topicToSubscribeTo.getName() + "_To_" + topicToPublishTo.getName());
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<Object> publisher = connectedNode.newPublisher(GraphName.of(topicToPublishTo.getName()), topicToPublishTo.getMessageType());
        Subscriber<Object> subscriber = connectedNode.newSubscriber(topicToSubscribeTo.getName(), topicToSubscribeTo.getMessageType());
        subscriber.addMessageListener(new MessageRelay<>(publisher));
    }
}
