package com.rad.rosplayground.rosjava.msg.transformers;

import com.rad.rosplayground.rosjava.msg.factories.TwistFactory;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

public class Vector3ToTwist implements ITransformMsg {
    public Vector3ToTwist() {
    }

    @Override
    public String getName() {
        return "vector3_to_twist";
    }

    @Override
    public Object transformMsg(Object msg) {
        Vector3 original = (Vector3) msg;
        Twist transformed = TwistFactory.makeTwist(original.getX()/180, original.getY()/180, original.getZ()/180, 1, 0, 0);
        return transformed;
    }

    @Override
    public String getToMsgType() {
        return Twist._TYPE;
    }

    @Override
    public String getFromMsgType() {
        return Vector3._TYPE;
    }
}
