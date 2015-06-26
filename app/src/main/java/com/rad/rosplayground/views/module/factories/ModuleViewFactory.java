package com.rad.rosplayground.views.module.factories;

import com.rad.rosplayground.activities.ModuleListActivity;
import com.rad.rosplayground.rosjava.msg.actors.Actor;
import com.rad.rosplayground.rosjava.msg.data.PublishingData;
import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsg;
import com.rad.rosplayground.views.module.DataPublisherModuleView;
import com.rad.rosplayground.views.module.ModuleView;
import com.rad.rosplayground.views.module.RelayModuleView;
import com.rad.rosplayground.views.module.SubscriberModuleView;
import com.rad.rosplayground.views.module.TransformerModuleView;
import com.rad.rosplayground.views.module.utils.ViewUtils;

import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicType;

import java.net.URI;

//TODO: set x and y position correctly if something is already occupying that space
//TODO set height and width correctly according to view
public class ModuleViewFactory {

    public static ModuleView makeTransformerModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToPublishTo, TopicType topicToSubscribeTo, ITransformMsg transformFunction) {
        ModuleView moduleView = new TransformerModuleView(moduleListActivity, masterUri, topicToPublishTo, topicToSubscribeTo, transformFunction);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        moduleView.setText(transformFunction.getName());
        return moduleView;
    }

    public static ModuleView makeRelayModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToPublishTo, TopicType topicToSubscribeTo) {
        ModuleView moduleView = new RelayModuleView(moduleListActivity, masterUri, topicToPublishTo, topicToSubscribeTo);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        moduleView.setText("to_" + topicToPublishTo.getName() + "_from_" + topicToSubscribeTo);
        return moduleView;
    }

    public static ModuleView makeDataPublisherModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToPublishTo, int rateToPublishAt, PublishingData dataToPublish) {
        ModuleView moduleView = new DataPublisherModuleView(moduleListActivity, masterUri, topicToPublishTo, rateToPublishAt, dataToPublish);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        moduleView.setText(topicToPublishTo.getName());
        return moduleView;
    }

    public static ModuleView makeSubscriberModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToSubscribeTo, Actor actor, MasterStateClient masterStateClient) {
        ModuleView moduleView = new SubscriberModuleView(moduleListActivity, masterUri, topicToSubscribeTo, actor, masterStateClient);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        moduleView.setText(topicToSubscribeTo.getName());
        return moduleView;
    }
}
