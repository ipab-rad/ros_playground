package com.rad.rosplayground.rosjava;

import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SubscribedTopicsFinder {
    public static List<TopicType> find(Collection<TopicSystemState> topicSystemStates, List<TopicType> topicTypes) {
        ArrayList<TopicType> topicsWithSubscribers = new ArrayList<>();
        for(TopicSystemState topicSystemState: topicSystemStates){
            Set<String> subscribers = topicSystemState.getSubscribers();
            if(subscribers.size() > 0) {
                addTopicToTopicsWithSubscribersList(topicTypes, topicsWithSubscribers, topicSystemState);
            }
        }
        return topicsWithSubscribers;
    }

    private static void addTopicToTopicsWithSubscribersList(List<TopicType> topicTypes, List<TopicType> topicsWithSubscribers, TopicSystemState topicSystemState) {
        TopicType topicWithName = topicWithName(topicTypes, topicSystemState.getTopicName());
        if(topicWithName != null){
            topicsWithSubscribers.add(topicWithName);
        }
    }

    private static TopicType topicWithName(List<TopicType> topicTypes, String topicName) {
        for (TopicType topicType : topicTypes) {
            if (topicType.getName().equals(topicName)){
                return topicType;
            }
        }
        return null;
    }
}
