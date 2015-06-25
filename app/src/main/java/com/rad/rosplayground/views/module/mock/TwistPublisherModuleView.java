package com.rad.rosplayground.views.module.mock;

import android.content.Context;

import com.rad.rosplayground.rosjava.node.TwistPublisherNode;
import com.rad.rosplayground.views.module.NodeMainModuleView;

import java.net.URI;

public class TwistPublisherModuleView extends NodeMainModuleView {

    private TwistPublisherNode twistPublisherNode;

    public TwistPublisherModuleView(Context context, URI masterURI) {
        super(context);
        twistPublisherNode = TwistPublisherNode.getInstance();
        if(!twistPublisherNode.started){
            executeNode(masterURI, twistPublisherNode);
        }
        twistPublisherNode.addPublisher();
    }
}
