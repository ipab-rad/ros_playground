package com.rad.rosplayground.views.module;

import android.content.Context;

import com.rad.rosplayground.rosjava.node.DataPublisherNode;
import com.rad.rosplayground.PublishingData;

import org.ros.master.client.TopicType;

import java.net.URI;

public class DataPublisherModuleView extends NodeMainModuleView {

    private DataPublisherNode dataPublisherNode;

    public DataPublisherModuleView(Context context, URI masterURI, TopicType topicToPublishTo, int rateToPublishAt, PublishingData dataToPublish) {
        super(context);
        dataPublisherNode = new DataPublisherNode(topicToPublishTo, rateToPublishAt, dataToPublish);
        executeNode(masterURI, dataPublisherNode);
    }
}
