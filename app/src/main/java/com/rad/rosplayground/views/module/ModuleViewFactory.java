package com.rad.rosplayground.views.module;

import android.view.ViewGroup;

import com.rad.rosplayground.Actor;
import com.rad.rosplayground.PublishingData;
import com.rad.rosplayground.activities.ModuleListActivity;

import org.ros.master.client.TopicType;

import java.net.URI;

public class ModuleViewFactory {
    public static ModuleView makeRelayModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToPublishTo, TopicType topicToSubscribeTo) {
        ModuleView moduleView = new RelayModuleView(moduleListActivity, masterUri, topicToPublishTo, topicToSubscribeTo);
        //TODO: set x and y position correctly if something is already occupying that space
        moduleView.setX(250);
        moduleView.setY(250);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(250,250); //TODO set height and width correctly according to view
        moduleView.setLayoutParams(layoutParams);
        moduleView.setOnTouchListener(moduleView);
        return moduleView;
    }

    public static ModuleView makeDataPublisherModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToPublishTo, int rateToPublishAt, PublishingData dataToPublish) {
        ModuleView moduleView = new DataPublisherModuleView(moduleListActivity, masterUri, topicToPublishTo, rateToPublishAt, dataToPublish);
        //TODO: set x and y position correctly if something is already occupying that space
        moduleView.setX(250);
        moduleView.setY(250);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(250,250); //TODO set height and width correctly according to view
        moduleView.setLayoutParams(layoutParams);
        moduleView.setOnTouchListener(moduleView);
        return moduleView;
    }

    public static ModuleView makeSubscriberModuleView(ModuleListActivity moduleListActivity, URI masterUri, TopicType topicToSubscribeTo, Actor actor) {
        ModuleView moduleView = new SubscriberModuleView(moduleListActivity, masterUri, topicToSubscribeTo, actor);
        //TODO: set x and y position correctly if something is already occupying that space
        moduleView.setX(250);
        moduleView.setY(250);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(250,250); //TODO set height and width correctly according to view
        moduleView.setLayoutParams(layoutParams);
        moduleView.setOnTouchListener(moduleView);
        return moduleView;
    }
}
