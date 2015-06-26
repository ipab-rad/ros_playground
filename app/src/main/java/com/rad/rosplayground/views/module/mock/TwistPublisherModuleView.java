package com.rad.rosplayground.views.module.mock;

import android.content.Context;

import com.rad.rosplayground.rosjava.node.mock.TwistPublisherNode;
import com.rad.rosplayground.rosjava.utils.ROSUtils;
import com.rad.rosplayground.views.module.ModuleView;

import java.net.URI;

public class TwistPublisherModuleView extends ModuleView {

    private TwistPublisherNode twistPublisherNode;

    public TwistPublisherModuleView(Context context, URI masterURI) {
        super(context);
        twistPublisherNode = TwistPublisherNode.getInstance();
        if(!twistPublisherNode.started){
            ROSUtils.executeNode(masterURI, twistPublisherNode);
        }
        twistPublisherNode.addPublisher();
    }
}
