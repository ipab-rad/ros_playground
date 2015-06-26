package com.rad.rosplayground.rosjava.msg.actors;

import android.util.Log;

import org.apache.commons.lang.StringUtils;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

public class LogTwistActor extends LogActor {
    @Override
    public void act(Object msg) {
        Log.i("LogTwistActor", msgFromTwist((Twist) msg));
    }

    private String msgFromTwist(Twist msg){
        Vector3 angular = msg.getAngular();
        Vector3 linear = msg.getLinear();
        String [] stringList = new String[]{
                "Angular:- x: " + angular.getX(),
                "y: " + angular.getY(),
                "z: " + angular.getZ(),
                "Linear:- x: " + linear.getX(),
                "y: " + linear.getY(),
                "z: " + linear.getZ()

        };
        return StringUtils.join(stringList, ", ");
    }
}
