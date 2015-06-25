package com.rad.rosplayground;

import android.content.Context;

import java.net.URI;

public class TwistPublisherModuleView extends PublisherModuleView {

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
