package com.rad.rosplayground;

import org.ros.node.topic.Publisher;

public class MessageRelayFactory<T> {
    public MessageRelay<T> makeMessageRelay(Publisher<T> publisher) {
        return new MessageRelay<T>(publisher);
    }
}
