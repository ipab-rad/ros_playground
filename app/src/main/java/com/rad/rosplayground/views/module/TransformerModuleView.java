package com.rad.rosplayground.views.module;

import android.content.Context;

import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsg;
import com.rad.rosplayground.rosjava.node.TransformerNode;
import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.ros.master.client.TopicType;

import java.net.URI;

public class TransformerModuleView extends ModuleView {

    private TransformerNode transformerNode;

    public TransformerModuleView(Context context, URI masterURI, TopicType topicToPublishTo, TopicType topicToSubscribeTo, ITransformMsg transformFunction) {
        super(context);
        transformerNode = new TransformerNode(topicToPublishTo, topicToSubscribeTo, transformFunction);
        ROSUtils.executeNode(masterURI, transformerNode);
    }
}
