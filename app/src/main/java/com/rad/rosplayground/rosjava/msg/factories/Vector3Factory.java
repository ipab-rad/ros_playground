package com.rad.rosplayground.rosjava.msg.factories;


import org.ros.message.MessageFactory;
import org.ros.node.NodeConfiguration;

import geometry_msgs.Vector3;

public class Vector3Factory {
    public static Vector3 makeVector3(double x, double y, double z){
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPrivate();
        MessageFactory messageFactory = nodeConfiguration.getTopicMessageFactory();
        Vector3 vector3 = messageFactory.newFromType(Vector3._TYPE);
        vector3.setX(x);
        vector3.setY(y);
        vector3.setZ(z);
        return vector3;
    }
}
