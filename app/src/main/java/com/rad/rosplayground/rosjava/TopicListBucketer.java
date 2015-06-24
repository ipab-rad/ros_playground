package com.rad.rosplayground.rosjava;

import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicListBucketer {
    public static Map<String, List<TopicType>> bucketTopics(List<TopicType> topicList) {
        HashMap<String, List<TopicType>> stringTopicTypeHashMap = new HashMap<>();
        for(TopicType topicType: topicList){
            addTopicToBucket(stringTopicTypeHashMap, topicType);
        }
        return stringTopicTypeHashMap;
    }

    private static void addTopicToBucket(HashMap<String, List<TopicType>> stringTopicTypeHashMap, TopicType topicType) {
        String startsWith = topicType.getName().split("/")[1];
        List<TopicType> topicTypes = stringTopicTypeHashMap.get(startsWith);
        if(topicTypes == null){
            topicTypes = new ArrayList<>();
            stringTopicTypeHashMap.put(startsWith, topicTypes);
        }
        topicTypes.add(topicType);
    }
}
