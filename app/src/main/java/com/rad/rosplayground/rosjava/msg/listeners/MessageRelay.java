package com.rad.rosplayground.rosjava.msg.listeners;

import org.ros.message.MessageListener;
import org.ros.node.topic.Publisher;

public class MessageRelay<T> implements MessageListener<T> {
    Publisher<T> publisher;

    public MessageRelay(Publisher<T> publisher){
        this.publisher = publisher;
    }

    @Override
    public void onNewMessage(T msg) {
        publisher.publish(msg);
    }
}
