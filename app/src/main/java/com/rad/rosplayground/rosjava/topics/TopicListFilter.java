package com.rad.rosplayground.rosjava.topics;

import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.List;

public class TopicListFilter {
    public static List<TopicType> topicsStartingWith(List<TopicType> topicList, String startsWith) {
        List<TopicType> filteredTopicList = new ArrayList<>();
        for(TopicType topicType: topicList){
            if(doesTopicStartWith(topicType, startsWith)){
                filteredTopicList.add(topicType);
            }
        }
        return filteredTopicList;
    }

    private static boolean doesTopicStartWith(TopicType topicType, String startsWith) {
        String[] parts = topicType.getName().split("/");
        return parts[1].equals(startsWith);
    }

    public static List<TopicType> topicsNotStartingWith(List<TopicType> topicList, String startsWith) {
        List<TopicType> filteredTopicList = new ArrayList<>();
        for(TopicType topicType: topicList){
            if(!doesTopicStartWith(topicType, startsWith)){
                filteredTopicList.add(topicType);
            }
        }
        return filteredTopicList;
    }
}
