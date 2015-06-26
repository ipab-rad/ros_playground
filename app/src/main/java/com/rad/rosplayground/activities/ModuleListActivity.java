package com.rad.rosplayground.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rad.rosplayground.fragments.ModuleListFragment;
import com.rad.rosplayground.rosjava.topics.TopicTypeAdapter;
import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsgAdapter;
import com.rad.rosplayground.rosjava.msg.actors.LogTwistActor;
import com.rad.rosplayground.rosjava.msg.transformers.MultiplyTwist;
import com.rad.rosplayground.rosjava.msg.transformers.ReverseTwist;
import com.rad.rosplayground.rosjava.msg.transformers.Vector3ToTwist;
import com.rad.rosplayground.rosjava.msg.transformers.ITransformMsg;
import com.rad.rosplayground.views.module.factories.MockModuleViewFactory;
import com.rad.rosplayground.views.module.ModuleView;
import com.rad.rosplayground.views.module.factories.ModuleViewFactory;
import com.rad.rosplayground.R;
import com.rad.rosplayground.rosjava.master.MasterStateClientFactory;
import com.rad.rosplayground.rosjava.topics.SubscribedTopicsFinder;
import com.rad.rosplayground.rosjava.topics.TopicListBucketer;

import org.ros.android.RosActivity;
import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicType;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

//TODO cleanup by splitting into bits
public class ModuleListActivity extends RosActivity
        implements ModuleListFragment.Callbacks {

    private String TAG = "ModuleListActivity";
    private String currentDrawer = "none";
    private MasterStateClient masterStateClient;
    FrameLayout playgroundContainer;
    List<String> mTopicNames;
    ArrayList<TopicType> mTopicTypes;
    ArrayList<ITransformMsg> transformers;

    public ModuleListActivity(){
        this("ModuleListActivity");
    }

    public ModuleListActivity(String activityIdentifier) {
        super(activityIdentifier, activityIdentifier);
        //Use above to ensure MasterURIChooser is shown at start of activity
        //Use below if we want to by pass MasterURIChooser and hardcode (mainly for developing)
//        super(activityIdentifier, activityIdentifier, URI.create("http://192.168.115.156:11311"));
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

    //TODO: move selection code out of activity into own class
    /**
     * Callback method from {@link ModuleListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        ListView drawerList = (ListView) findViewById(R.id.module_drawer);
        mTopicNames = new ArrayList<>();
        mTopicTypes = new ArrayList<>();
        if(id.equals("all-topics")) {
            fillMenuWithBucketedTopicNames(drawerList, masterStateClient);
        } else if(id.equals("subscribed-to-topics")){
            fillMenuWithSubscriberToTopics(drawerList, masterStateClient);
        } else if(id.equals("mock-publishers")){
            fillMenuWithMockPublisherTopics(drawerList);
        } else if(id.equals("transformers")){
            fillMenuWithTransformers(drawerList);
//            fillMenuWithStringTransformers(drawerList);
        }

        toggleDrawer(id, drawerList);
        currentDrawer = id;
    }

    private void fillMenuWithBucketedTopicNames(ListView drawerList, MasterStateClient masterStateClient) {
        List<TopicType> topicTypes = masterStateClient.getTopicTypes();
        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(topicTypes);
        for(String topicNamespace: bucketedTopics.keySet()){
            mTopicNames.add(topicNamespace);
        }
        drawerList.setOnItemClickListener(new MockPublisherDrawerItemClickListener());
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mTopicNames));
    }

    private void fillMenuWithSubscriberToTopics(ListView drawerList, MasterStateClient masterStateClient) {
        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(masterStateClient);
        for(TopicType topic: topicsWithSubscribers){
            mTopicTypes.add(topic);
        }
        drawerList.setOnItemClickListener(new SubscriberDrawerItemClickListener());
        drawerList.setAdapter(new TopicTypeAdapter(this, R.layout.drawer_list_item, mTopicTypes));
    }

    private void fillMenuWithMockPublisherTopics(ListView drawerList) {
        mTopicNames.add("Mock Twist");
        drawerList.setOnItemClickListener(new MockPublisherDrawerItemClickListener());
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mTopicNames));
    }

    private void fillMenuWithTransformers(ListView drawerList) {
        transformers = new ArrayList<>();
        transformers.add(new ReverseTwist());
        transformers.add(new MultiplyTwist(2));
        transformers.add(new Vector3ToTwist());
        drawerList.setOnItemClickListener(new TransformerDrawerItemClickListener());
        drawerList.setAdapter(new ITransformMsgAdapter(this, R.layout.drawer_list_item, transformers));
    }

    private void fillMenuWithStringTransformers(ListView drawerList) {
        List<String> mTopicNames = new ArrayList<>();
        mTopicNames.add("Reverse Twist");
        mTopicNames.add("Multiply x2 Twist");
        mTopicNames.add("Vector3 to Twist");
        drawerList.setOnItemClickListener(new TransformerDrawerItemClickListener());
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mTopicNames));
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

    private class MockPublisherDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectPublisherItem(position);
        }
    }

    private void selectPublisherItem(int position) {
        closeDrawer();
        ModuleView moduleView = MockModuleViewFactory.makeTwistPublisherModuleView(this, getMasterUri(), mTopicNames.get(position));
        playgroundContainer.addView(moduleView);
    }

    private void closeDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();
    }

    private class SubscriberDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectSubscriberItem(position);
        }
    }

    private void selectSubscriberItem(int position) {
        closeDrawer();
        ModuleView moduleView = ModuleViewFactory.makeSubscriberModuleView(this, getMasterUri(), mTopicTypes.get(position), new LogTwistActor(), masterStateClient);
        playgroundContainer.addView(moduleView);
    }

    private class TransformerDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectTransformerItem(position);
        }
    }

    private void selectTransformerItem(int position) {
        closeDrawer();
//        ModuleView moduleView = getTransformerModuleString(position);
        ITransformMsg transformFunction = transformers.get(position);
        String topicToTransformFrom = "mock_twist_1";
        TopicType topicToPublishTo = new TopicType(
                "transformed_" + topicToTransformFrom + "_" + transformFunction.getName(),
                transformFunction.getToMsgType()
        );
        TopicType topicToTransform = new TopicType(topicToTransformFrom, transformFunction.getFromMsgType());
        ModuleView moduleView = ModuleViewFactory.makeTransformerModuleView(
                this,
                getMasterUri(),
                topicToPublishTo,
                topicToTransform,
                transformFunction
        );
        playgroundContainer.addView(moduleView);
    }

    @NonNull
    private ModuleView getTransformerModuleString(int position) {
        ITransformMsg transformFunction;
        ModuleView moduleView;
        if(position == 0) {
            transformFunction = new ReverseTwist();
            moduleView = ModuleViewFactory.makeTransformerModuleView(this, getMasterUri(), new TopicType("transformed_mock_twist_" + transformFunction.getName(), Twist._TYPE), new TopicType("mock_twist_1", Twist._TYPE), transformFunction);
            moduleView.setText(mTopicNames.get(position));
        } else if (position == 1) {
            transformFunction = new MultiplyTwist(2);
            moduleView = ModuleViewFactory.makeTransformerModuleView(this, getMasterUri(), new TopicType("transformed_mock_twist_"+transformFunction.getName(), Twist._TYPE), new TopicType("mock_twist_1", Twist._TYPE), transformFunction);
        } else {
            transformFunction = new Vector3ToTwist();
            moduleView = ModuleViewFactory.makeTransformerModuleView(this, getMasterUri(), new TopicType("transformed_mock_twist_"+transformFunction.getName(), Twist._TYPE), new TopicType("myo_0/orientation", Vector3._TYPE), transformFunction);
        }
        return moduleView;
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        masterStateClient = MasterStateClientFactory.getInstance(nodeMainExecutor, getMasterUri());
    }
}
