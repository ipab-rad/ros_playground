package com.rad.rosplayground.rosjava.msg.listeners;

import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsg;

import org.ros.message.MessageListener;
import org.ros.node.topic.Publisher;

public class MessageRelayTransform<X,Y> implements MessageListener<X> {
    Publisher<Y> publisher;
    ITransformMsg<X,Y> transformMsg;

    public MessageRelayTransform(Publisher<Y> publisher, ITransformMsg<X,Y> transformMsg){
        this.publisher = publisher;
        this.transformMsg = transformMsg;
    }

    @Override
    public void onNewMessage(X msg) {
        Y transformedMsg = transformMsg.transformMsg(msg);
        publisher.publish(transformedMsg);
    }
}
