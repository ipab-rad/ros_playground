package com.rad.rosplayground.rosjava;

import android.support.annotation.NonNull;

import com.rad.rosplayground.rosjava.topics.SubscribedTopicsFinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SubscribedTopicsFinderTest {

    private List<TopicSystemState> testTopicSystemStateList;
    private List<TopicType> testTopicList;
    private String testTopic1;
    private String testTopic2;
    private String testTopicName1 = "/" + testTopic1 + "/test_vel";
    private String testTopicName2 = "/" + testTopic2 + "/test_vel";
    private String testSubscriberName1 = "testSubscriberName1";
    private String testSubscriberName2 = "testSubscriberName2";
    private TopicType mockTopic1;
    private TopicType mockTopic2;

    @Before
    public void setUp() throws Exception {
        testTopicSystemStateList = new ArrayList<>();
        testTopicList = new ArrayList<>();
        testTopic1 = "testTopic1";
        testTopic2 = "testTopic2";
        mockTopic1 = makeMockTopicType(testTopicName1);
        mockTopic2 = makeMockTopicType(testTopicName2);
    }

    @Test
    public void givenEmptyTopicList_NoTopicsWithSubscribersAreFound() {
        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers).isEmpty();

    }

    @Test
    public void givenTopicListWithOneTopicWithName_testTopicName1_whichHasNoSubscribers_NoTopicsWithSubscribersAreFound() {

        TopicSystemState mockTopicSystemState = makeMockTopicSystemStateWithSubscribers(testTopicName1, new HashSet<String>());

        testTopicSystemStateList.add(mockTopicSystemState);
        testTopicList.add(mockTopic1);

        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers).isEmpty();

    }

    @Test
    public void givenTopicListWithOneTopicWithName_testTopicName1_whichHasOneSubscriber_OneTopicWithSubscribersIsFound() {

        HashSet<String> subscriberSet = new HashSet<>();
        subscriberSet.add(testSubscriberName1);
        TopicSystemState mockTopicSystemState = makeMockTopicSystemStateWithSubscribers(testTopicName1, subscriberSet);

        testTopicSystemStateList.add(mockTopicSystemState);
        testTopicList.add(mockTopic1);

        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers.size()).isEqualTo(1);
        assertThat(topicsWithSubscribers.get(0).getName()).isEqualTo(testTopicName1);

    }

    @Test
    public void givenTopicListWithTwoTopics_andOnlyOneHasASubscriber_OneTopicWithSubscribersIsFound() {

        HashSet<String> subscriberSet = new HashSet<>();
        subscriberSet.add(testSubscriberName1);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithSubscribers(testTopicName1, subscriberSet);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithSubscribers(testTopicName2, new HashSet<String>());

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers.size()).isEqualTo(1);
        assertThat(topicsWithSubscribers.get(0).getName()).isEqualTo(testTopicName1);

    }

    @Test
    public void givenTopicListWithTwoTopics_andBothHaveTheSameSubscriber_TwoTopicsWithSubscribersAreFound() {

        HashSet<String> subscriberSet = new HashSet<>();
        subscriberSet.add(testSubscriberName1);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithSubscribers(testTopicName1, subscriberSet);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithSubscribers(testTopicName2, subscriberSet);

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers.size()).isEqualTo(2);
        assertThat(topicsWithSubscribers.get(0).getName()).isEqualTo(testTopicName1);
        assertThat(topicsWithSubscribers.get(1).getName()).isEqualTo(testTopicName2);

    }

    @Test
    public void givenTopicListWithTwoTopics_andBothHaveADifferentSubscriber_TwoTopicsWithSubscribersAreFound() {

        HashSet<String> subscriberSet1 = new HashSet<>();
        subscriberSet1.add(testSubscriberName1);
        HashSet<String> subscriberSet2 = new HashSet<>();
        subscriberSet2.add(testSubscriberName2);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithSubscribers(testTopicName1, subscriberSet1);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithSubscribers(testTopicName2, subscriberSet2);

        Mockito.when(mockTopicSystemState1.getSubscribers()).thenReturn(subscriberSet1);
        Mockito.when(mockTopicSystemState2.getSubscribers()).thenReturn(subscriberSet2);

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithSubscribers = SubscribedTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithSubscribers.size()).isEqualTo(2);
        assertThat(topicsWithSubscribers.get(0).getName()).isEqualTo(testTopicName1);
        assertThat(topicsWithSubscribers.get(1).getName()).isEqualTo(testTopicName2);

    }

    @NonNull
    private TopicType makeMockTopicType(String topicName) {
        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(topicName);
        return mockTopic;
    }

    @NonNull
    private TopicSystemState makeMockTopicSystemStateWithSubscribers(String topicName, HashSet<String> subscriberSet) {
        TopicSystemState mockTopicSystemState = Mockito.mock(TopicSystemState.class);
        Mockito.when(mockTopicSystemState.getTopicName()).thenReturn(topicName);
        Mockito.when(mockTopicSystemState.getSubscribers()).thenReturn(subscriberSet);
        return mockTopicSystemState;
    }
}