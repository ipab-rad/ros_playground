package com.rad.rosplayground.views.module.mock;

import android.content.Context;

import com.rad.rosjava_wrapper.publish.TimePublisherNode;
import com.rad.rosplayground.views.module.NodeMainModuleView;

import java.net.URI;

public class TimePublisherModuleView extends NodeMainModuleView {

    private TimePublisherNode timePublisherNode;

    public TimePublisherModuleView(Context context, URI masterURI) {
        super(context);
        timePublisherNode = new TimePublisherNode();
        executeNode(masterURI, timePublisherNode);
    }
}
