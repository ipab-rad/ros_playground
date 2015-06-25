package com.rad.rosplayground.rosjava;

import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PublishedToTopicsFinder {
    public static List<TopicType> find(Collection<TopicSystemState> topicSystemStates, List<TopicType> topicTypes) {
        ArrayList<TopicType> topicsWithPublishers = new ArrayList<>();
        for(TopicSystemState topicSystemState: topicSystemStates){
            Set<String> publishers = topicSystemState.getPublishers();
            if(publishers.size() > 0) {
                addTopicToTopicsWithPublishersList(topicTypes, topicsWithPublishers, topicSystemState);
            }
        }
        return topicsWithPublishers;
    }

    private static void addTopicToTopicsWithPublishersList(List<TopicType> topicTypes, List<TopicType> topicsWithPublishers, TopicSystemState topicSystemState) {
        TopicType topicWithName = topicWithName(topicTypes, topicSystemState.getTopicName());
        if(topicWithName != null){
            topicsWithPublishers.add(topicWithName);
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
