package com.rad.rosplayground;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rad.rosplayground.rosjava.MasterStateClientFactory;
import com.rad.rosplayground.rosjava.TopicListBucketer;
import com.rad.rosplayground.rosjava.TopicListFilter;

import org.ros.android.RosActivity;
import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicType;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        //super(activityIdentifier, activityIdentifier, URI.create("http://localhost:11311"));
        publisherNode = new TimePublisherNode();
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
        if(id.equals("1")) {
            Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(topicTypes);
            for(String topicNamespace: bucketedTopics.keySet()){
                mTopicTitles.add(topicNamespace);
            }
        } else if(id.equals("2")){
            List<TopicType> turtle1Topics = TopicListFilter.topicsStartingWith(topicTypes, "turtle1");
            for(TopicType turtle1Topic: turtle1Topics){
                mTopicTitles.add(turtle1Topic.getName());
            }
        } else if(id.equals("3")){
            List<TopicType> nonTurtle1Topics = TopicListFilter.topicsNotStartingWith(topicTypes, "turtle1");
            for(TopicType topic: nonTurtle1Topics){
                mTopicTitles.add(topic.getName());
            }
        }
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTopicTitles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();

        ModuleView moduleView = new TimePublisherModuleView(this, getMasterUri());
        moduleView.setText(mTopicTitles.get(position));
        //TODO: set x and y position correctly if something is already occupying that space
        moduleView.setX(250);
        moduleView.setY(250);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(250,250); //TODO set height and width correctly according to view
        moduleView.setLayoutParams(layoutParams);
        moduleView.setOnTouchListener(moduleView);

        // Add the text view to the parent layout
        playgroundContainer.addView(moduleView);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        masterStateClient = MasterStateClientFactory.getInstance(nodeMainExecutor, getMasterUri());
    }
}
