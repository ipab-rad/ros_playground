package com.rad.rosplayground.rosjava.node;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import com.rad.rosplayground.R;
import com.rad.rosplayground.activities.ModuleListActivity;
import com.rad.rosplayground.rosjava.topics.PublishedToTopicsFinder;

import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.List;

public class PublisherPopupMenu extends PopupMenu{

    public PublisherPopupMenu(Context context, View anchor, MasterStateClient masterStateClient,
                              ModuleListActivity parentActivity, TopicType topicToPublishTo) {
        super(context, anchor);
        this.getMenuInflater().inflate(R.menu.popup_menu, this.getMenu());
        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(masterStateClient);
        List<TopicType> topicsInMenu = fillMenuWithPublishingTopicsOfSameMsgType(this, topicsWithPublishers, topicToPublishTo);
        registerOnMenuItemClickListener(this, topicsInMenu, parentActivity, topicToPublishTo);
    }

    private List<TopicType> fillMenuWithPublishingTopicsOfSameMsgType(PopupMenu popup, List<TopicType> topicsWithPublishers, TopicType topicToPublishTo) {
        String topicToPublishToMessageType = topicToPublishTo.getMessageType();
        List<TopicType> topicsInMenu = new ArrayList<>();
        int positionId = 0;
        for(TopicType topicType: topicsWithPublishers){
            if(areTopicMsgTypeEqual(topicToPublishToMessageType, topicType)){
                addTopicToMenu(popup, topicsInMenu, positionId, topicType);
                positionId += 1;
            }
        }
        return topicsInMenu;
    }

    private boolean areTopicMsgTypeEqual(String topicToPublishToMessageType, TopicType topicType) {
        return topicType.getMessageType().equals(topicToPublishToMessageType);
    }

    private void addTopicToMenu(PopupMenu popup, List<TopicType> topicsInMenu, int positionId, TopicType topicType) {
        popup.getMenu().add(0, positionId, positionId, topicType.getName());
        topicsInMenu.add(topicType);
    }

    private void registerOnMenuItemClickListener(PopupMenu popup, List<TopicType> topicsInMenu,
                                                 ModuleListActivity parentActivity,
                                                 TopicType topicToPublishTo) {
        popup.setOnMenuItemClickListener(new PublisherMenuItemClickListener(topicsInMenu, parentActivity, topicToPublishTo));
    }
}
