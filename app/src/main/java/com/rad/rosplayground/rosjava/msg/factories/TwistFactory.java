package com.rad.rosplayground.rosjava.msg.factories;


import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.ros.message.MessageFactory;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

public class TwistFactory {
    public static Twist makeTwist(double angularX, double angularY, double angularZ, double linearX, double linearY, double linearZ){
        MessageFactory messageFactory = ROSUtils.getMessageFactory();
        Twist twist = messageFactory.newFromType(Twist._TYPE);
        Vector3 vector3Angular = Vector3Factory.makeVector3(angularX, angularY, angularZ);
        Vector3 vector3Linear = Vector3Factory.makeVector3(linearX, linearY, linearZ);
        twist.setLinear(vector3Linear);
        twist.setAngular(vector3Angular);
        return twist;
    }

    public static Twist makeTwist(Vector3 angular, Vector3 linear){
        MessageFactory messageFactory = ROSUtils.getMessageFactory();
        Twist twist = messageFactory.newFromType(Twist._TYPE);
        twist.setLinear(linear);
        twist.setAngular(angular);
        return twist;
    }
}
