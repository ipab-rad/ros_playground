package com.rad.rosplayground.rosjava.msg.listeners;

import com.rad.rosplayground.rosjava.msg.actors.Actor;

import org.ros.message.MessageListener;

public class MessageActor<T> implements MessageListener<T> {
    Actor<T> actor;

    public MessageActor(Actor<T> actor){
        this.actor = actor;
    }

    @Override
    public void onNewMessage(T msg) {
        actor.act(msg);
    }
}
