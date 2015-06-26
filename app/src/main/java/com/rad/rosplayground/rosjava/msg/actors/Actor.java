package com.rad.rosplayground.rosjava.msg.actors;

public interface Actor<T> {
    void act(T msg);
}
