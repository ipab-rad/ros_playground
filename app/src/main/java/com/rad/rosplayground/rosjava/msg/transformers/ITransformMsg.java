package com.rad.rosplayground.rosjava.msg.transformers;

public interface ITransformMsg<X, Y> {
    String getName();
    Y transformMsg(X msg);

    String getToMsgType();
    String getFromMsgType();
}
