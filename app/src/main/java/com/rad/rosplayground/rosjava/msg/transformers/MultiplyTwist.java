package com.rad.rosplayground.rosjava.msg.transformers;

import com.rad.rosplayground.rosjava.msg.factories.TwistFactory;
import com.rad.rosplayground.rosjava.msg.utils.Vector3Utils;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

public class MultiplyTwist implements ITransformMsg {
    private double multiplier;
    public MultiplyTwist(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {
        return "multiply_twist_x" + (int) multiplier;
    }

    @Override
    public Object transformMsg(Object msg) {
        Twist original = (Twist) msg;
        Vector3 angular = original.getAngular();
        Vector3 linear = original.getLinear();
        multiplyAngularZ(angular);
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

    private void multiplyAngularZ(Vector3 angular) {
        Vector3Utils.multiplyZ(angular, multiplier);
    }
}
