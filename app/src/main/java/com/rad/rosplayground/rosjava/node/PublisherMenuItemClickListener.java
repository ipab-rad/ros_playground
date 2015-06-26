package com.rad.rosplayground.rosjava.node;

import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.rad.rosplayground.activities.ModuleListActivity;
import com.rad.rosplayground.views.module.ModuleView;
import com.rad.rosplayground.views.module.factories.ModuleViewFactory;

import org.ros.master.client.TopicType;

import java.util.List;

public class PublisherMenuItemClickListener implements OnMenuItemClickListener {
    private List<TopicType> topicsInMenu;
    private ModuleListActivity parentActivity;
    private TopicType topicToPublishTo;

    public PublisherMenuItemClickListener(List<TopicType> topicsInMenu, ModuleListActivity parentActivity, TopicType topicToPublishTo){
        this.topicsInMenu = topicsInMenu;
        this.parentActivity = parentActivity;
        this.topicToPublishTo = topicToPublishTo;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TopicType topicType = topicTypeFromMenu(item);
        ModuleView moduleView = publishToSubscribedTopic(topicType);
        //TODO add moduleView to playground_view
        return true;
    }

    private TopicType topicTypeFromMenu(MenuItem item) {
        int itemId = item.getItemId();
        return topicsInMenu.get(itemId);
    }

    private ModuleView publishToSubscribedTopic(TopicType topicType) {
        return ModuleViewFactory.makeRelayModuleView(parentActivity, parentActivity.getMasterUri(), topicToPublishTo, topicType);
    }
}
