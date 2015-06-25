package com.rad.rosplayground.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rad.rosplayground.fragments.ModuleListFragment;
import com.rad.rosplayground.views.module.mock.MockModuleViewFactory;
import com.rad.rosplayground.views.module.ModuleView;
import com.rad.rosplayground.views.module.ModuleViewFactory;
import com.rad.rosplayground.R;
import com.rad.rosplayground.rosjava.master.MasterStateClientFactory;
import com.rad.rosplayground.rosjava.topics.PublishedToTopicsFinder;
import com.rad.rosplayground.rosjava.topics.SubscribedTopicsFinder;
import com.rad.rosplayground.rosjava.topics.TopicListBucketer;

import org.ros.android.RosActivity;
import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import geometry_msgs.Twist;

public class ModuleListActivity extends RosActivity
        implements ModuleListFragment.Callbacks {

    private String TAG = "ModuleListActivity";
    private String currentDrawer = "none";
    private MasterStateClient masterStateClient;
    FrameLayout playgroundContainer;
    List<String> mTopicTitles;

    public ModuleListActivity(){
        this("ModuleListActivity");
    }

    public ModuleListActivity(String activityIdentifier) {
        super(activityIdentifier, activityIdentifier);
        //Use below if we want to by pass MasterURIChooser and hardcode
//        super(activityIdentifier, activityIdentifier, URI.create("http://192.168.1.17:11311"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);
        lockModuleDrawerClosed();
        activateListItemsOnTouch();
        playgroundContainer = (FrameLayout) findViewById(R.id.playground_container);
    }

    private void lockModuleDrawerClosed() {
        ListView drawerList = (ListView) findViewById(R.id.module_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, drawerList);
    }

    private void activateListItemsOnTouch() {
        // List items should be given the
        // 'activated' state when touched.
        ((ModuleListFragment) getFragmentManager()
                .findFragmentById(R.id.module_list))
                .setActivateOnItemClick(true);
    }

    /**
     * Callback method from {@link ModuleListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

        ListView drawerList = (ListView) findViewById(R.id.module_drawer);
        mTopicTitles = new ArrayList<>();
        // Set the adapter for the list view
        List<TopicType> topicTypes = masterStateClient.getTopicTypes();
        Collection<TopicSystemState> topicSystemStates = masterStateClient.getSystemState().getTopics();
        if(id.equals("all-topics")) {
            Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(topicTypes);
            for(String topicNamespace: bucketedTopics.keySet()){
                mTopicTitles.add(topicNamespace);
            }
            drawerList.setOnItemClickListener(new PublisherDrawerItemClickListener());
        } else if(id.equals("subscribed-to-topics")){
            List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(topicSystemStates, topicTypes);
            for(TopicType topic: topicsWithSubscribers){
                mTopicTitles.add(topic.getName());
            }
            drawerList.setOnItemClickListener(new SubscriberDrawerItemClickListener());
        } else if(id.equals("published-to-topics")){
            List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(topicSystemStates, topicTypes);
            for(TopicType topic: topicsWithPublishers){
                mTopicTitles.add(topic.getName());
            }
            drawerList.setOnItemClickListener(new PublisherDrawerItemClickListener());
        }
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mTopicTitles));

        toggleDrawer(id, drawerList);
        currentDrawer = id;
    }

    private void toggleDrawer(String id, ListView drawerList) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(currentDrawer.equals(id)){
            if(drawerLayout.isDrawerOpen(drawerList)) {
                drawerLayout.closeDrawer(drawerList);
            } else {
                drawerLayout.openDrawer(drawerList);
            }
        } else {
            drawerLayout.closeDrawers();
            drawerLayout.openDrawer(drawerList);
        }
    }

    private class PublisherDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectPublisherItem(position);
        }
    }

    private void selectPublisherItem(int position) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();

        ModuleView moduleView = MockModuleViewFactory.makeTwistPublisherModuleView(this, getMasterUri());
        moduleView.setText(mTopicTitles.get(position));
        // Add the text view to the parent layout
        playgroundContainer.addView(moduleView);
    }

    private class SubscriberDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectSubscriberItem(position);
        }
    }

    private void selectSubscriberItem(int position) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();

        ModuleView moduleView = ModuleViewFactory.makeRelayModuleView(this, getMasterUri(), new TopicType("/turtle1/cmd_vel", Twist._TYPE), new TopicType("mock_twist_1", Twist._TYPE));
        moduleView.setText(mTopicTitles.get(position));
        // Add the text view to the parent layout
        playgroundContainer.addView(moduleView);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        masterStateClient = MasterStateClientFactory.getInstance(nodeMainExecutor, getMasterUri());
    }
}
