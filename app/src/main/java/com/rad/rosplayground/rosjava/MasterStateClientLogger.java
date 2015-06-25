package com.rad.rosplayground.rosjava;

import android.util.Log;

import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;

import java.util.Collection;
import java.util.List;

public class MasterStateClientLogger {

    MasterStateClient masterStateClient;

    public MasterStateClientLogger(MasterStateClient masterStateClient){
        this.masterStateClient = masterStateClient;
    }
    public void logTopicsFromMasterClient() {
        Collection<TopicSystemState> topics = masterStateClient.getSystemState().getTopics();
        for(TopicSystemState topic: topics){
            Log.i("topicName", topic.getTopicName());
            Log.i("topicPublisherSet", topic.getPublishers().toString());
            Log.i("topicSubscriberSet", topic.getSubscribers().toString());
        }

        List<TopicType> topicTypes = masterStateClient.getTopicTypes();
        for(TopicType topicType: topicTypes){
            Log.i("topicTypeName", topicType.getName());
            Log.i("topicTypeMessageType", topicType.getMessageType());
        }

    }
}
