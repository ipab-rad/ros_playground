package com.rad.rosplayground.rosjava.msg.transformers;

import com.rad.rosplayground.rosjava.msg.factories.TwistFactory;
import com.rad.rosplayground.rosjava.msg.utils.Vector3Utils;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

public class ReverseTwist implements ITransformMsg {
    @Override
    public String getName() {
        return "reverse_twist";
    }

    @Override
    public Object transformMsg(Object msg) {
        Twist original = (Twist) msg;
        Vector3 angular = original.getAngular();
        Vector3 linear = original.getLinear();
        reverseAngularZ(angular);
        return TwistFactory.makeTwist(angular, linear);
    }

    @Override
    public String getToMsgType() {
        return Twist._TYPE;
    }

    @Override
    public String getFromMsgType() {
        return Twist._TYPE;
    }

    private void reverseAngularZ(Vector3 angular) {
        Vector3Utils.multiplyZ(angular, -1);
    }
}
