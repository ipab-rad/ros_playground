package com.rad.rosplayground.views.module.mock;

import android.content.Context;

import com.rad.rosjava_wrapper.publish.TimePublisherNode;
import com.rad.rosplayground.rosjava.utils.ROSUtils;
import com.rad.rosplayground.views.module.ModuleView;

import java.net.URI;

public class TimePublisherModuleView extends ModuleView {

    private TimePublisherNode timePublisherNode;

    public TimePublisherModuleView(Context context, URI masterURI) {
        super(context);
        timePublisherNode = new TimePublisherNode();
        ROSUtils.executeNode(masterURI, timePublisherNode);
    }
}
