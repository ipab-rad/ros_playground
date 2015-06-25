package com.rad.rosplayground;

import android.content.Context;

import com.rad.rosjava_wrapper.publish.TimePublisherNode;

import java.net.URI;

public class TimePublisherModuleView extends PublisherModuleView {

    private TimePublisherNode timePublisherNode;

    public TimePublisherModuleView(Context context, URI masterURI) {
        super(context);
        timePublisherNode = new TimePublisherNode();
        executeNode(masterURI, timePublisherNode);
    }
}
