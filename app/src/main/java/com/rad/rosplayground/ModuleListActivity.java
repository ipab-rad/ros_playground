package com.rad.rosplayground;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rad.rosjava_wrapper.publish.TimePublisherNode;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.internal.node.DefaultNode;
import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicSystemState;
import org.ros.node.DefaultNodeFactory;
import org.ros.node.DefaultNodeListener;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeListener;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;
import java.util.Collection;

import static org.ros.node.NodeConfiguration.newPublic;

public class ModuleListActivity extends RosActivity
        implements ModuleListFragment.Callbacks {

    private String TAG = "ModuleListActivity";
    private String currentDrawer = "none";
    private TimePublisherNode publisherNode;

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
        String[] mTopicTitles = new String[]{"Topic " + id + "-1", "Topic " + id + "-2"};
        // Set the adapter for the list view
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

    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        initNode(nodeMainExecutor, publisherNode);
    }

    public void initNode(NodeMainExecutor nodeMainExecutor, NodeMain node) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(node, nodeConfiguration);

        logTopicsFromMasterClient(nodeMainExecutor);
    }

    private void logTopicsFromMasterClient(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration = newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setDefaultNodeName("MasterClient/masterListener");
        Log.i(TAG, nodeConfiguration.getNodeName().toString());
        nodeConfiguration.setMasterUri(getMasterUri());
        ArrayList<NodeListener> listeners = new ArrayList<>();
        DefaultNodeFactory defaultNodeFactory = new DefaultNodeFactory(nodeMainExecutor.getScheduledExecutorService());
        Node host = defaultNodeFactory.newNode(nodeConfiguration, listeners);


        MasterStateClient masterStateClient = new MasterStateClient(host, getMasterUri());
        Collection<TopicSystemState> topics = masterStateClient.getSystemState().getTopics();
        for(TopicSystemState topic: topics){
            Log.i(TAG, topic.getTopicName());
        }
    }
}
