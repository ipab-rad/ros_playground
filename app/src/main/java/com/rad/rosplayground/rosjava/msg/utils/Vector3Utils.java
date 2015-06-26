package com.rad.rosplayground.rosjava.msg.utils;

import geometry_msgs.Vector3;

public class Vector3Utils {
    public static void multiplyZ(Vector3 vector3, double multiplier) {
        vector3.setZ(vector3.getZ()*multiplier);
    }
}
