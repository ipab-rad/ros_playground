package com.rad.rosplayground.rosjava.msg.actors;

import android.util.Log;

public class LogActor implements Actor {
    @Override
    public void act(Object msg) {
        Log.i("LogActor", msg.toString());
    }
}
