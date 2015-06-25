package com.rad.rosplayground.rosjava;

import android.support.annotation.NonNull;

import com.rad.rosplayground.rosjava.topics.PublishedToTopicsFinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ros.master.client.TopicSystemState;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class PublishedToTopicsFinderTest {

    private List<TopicSystemState> testTopicSystemStateList;
    private List<TopicType> testTopicList;
    private String testTopic1;
    private String testTopic2;
    private String testTopicName1 = "/" + testTopic1 + "/test_vel";
    private String testTopicName2 = "/" + testTopic2 + "/test_vel";
    private String testPublisherName1 = "testPublisherName1";
    private String testPublisherName2 = "testPublisherName2";
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
    public void givenEmptyTopicList_NoTopicsWithPublishersAreFound() {
        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers).isEmpty();

    }

    @Test
    public void givenTopicListWithOneTopicWithName_testTopicName1_whichHasNoPublishers_NoTopicsWithPublishersAreFound() {

        TopicSystemState mockTopicSystemState = makeMockTopicSystemStateWithPublishers(testTopicName1, new HashSet<String>());

        testTopicSystemStateList.add(mockTopicSystemState);
        testTopicList.add(mockTopic1);

        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers).isEmpty();

    }

    @Test
    public void givenTopicListWithOneTopicWithName_testTopicName1_whichHasOnePublisher_OneTopicWithPublishersIsFound() {

        HashSet<String> publisherSet = new HashSet<>();
        publisherSet.add(testPublisherName1);
        TopicSystemState mockTopicSystemState = makeMockTopicSystemStateWithPublishers(testTopicName1, publisherSet);

        testTopicSystemStateList.add(mockTopicSystemState);
        testTopicList.add(mockTopic1);

        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers.size()).isEqualTo(1);
        assertThat(topicsWithPublishers.get(0).getName()).isEqualTo(testTopicName1);

    }

    @Test
    public void givenTopicListWithTwoTopics_andOnlyOneHasAPublisher_OneTopicWithPublishersIsFound() {

        HashSet<String> publisherSet = new HashSet<>();
        publisherSet.add(testPublisherName1);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithPublishers(testTopicName1, publisherSet);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithPublishers(testTopicName2, new HashSet<String>());

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers.size()).isEqualTo(1);
        assertThat(topicsWithPublishers.get(0).getName()).isEqualTo(testTopicName1);

    }

    @Test
    public void givenTopicListWithTwoTopics_andBothHaveTheSamePublisher_TwoTopicsWithPublishersAreFound() {

        HashSet<String> publisherSet = new HashSet<>();
        publisherSet.add(testPublisherName1);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithPublishers(testTopicName1, publisherSet);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithPublishers(testTopicName2, publisherSet);

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers.size()).isEqualTo(2);
        assertThat(topicsWithPublishers.get(0).getName()).isEqualTo(testTopicName1);
        assertThat(topicsWithPublishers.get(1).getName()).isEqualTo(testTopicName2);

    }

    @Test
    public void givenTopicListWithTwoTopics_andBothHaveADifferentPublisher_TwoTopicsWithPublishersAreFound() {

        HashSet<String> publisherSet1 = new HashSet<>();
        publisherSet1.add(testPublisherName1);
        HashSet<String> publisherSet2 = new HashSet<>();
        publisherSet2.add(testPublisherName2);
        TopicSystemState mockTopicSystemState1 = makeMockTopicSystemStateWithPublishers(testTopicName1, publisherSet1);
        TopicSystemState mockTopicSystemState2 = makeMockTopicSystemStateWithPublishers(testTopicName2, publisherSet2);

        Mockito.when(mockTopicSystemState1.getPublishers()).thenReturn(publisherSet1);
        Mockito.when(mockTopicSystemState2.getPublishers()).thenReturn(publisherSet2);

        testTopicSystemStateList.add(mockTopicSystemState1);
        testTopicSystemStateList.add(mockTopicSystemState2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        List<TopicType> topicsWithPublishers = PublishedToTopicsFinder.find(testTopicSystemStateList, testTopicList);
        assertThat(topicsWithPublishers.size()).isEqualTo(2);
        assertThat(topicsWithPublishers.get(0).getName()).isEqualTo(testTopicName1);
        assertThat(topicsWithPublishers.get(1).getName()).isEqualTo(testTopicName2);

    }

    @NonNull
    private TopicType makeMockTopicType(String topicName) {
        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(topicName);
        return mockTopic;
    }

    @NonNull
    private TopicSystemState makeMockTopicSystemStateWithPublishers(String topicName, HashSet<String> publisherSet) {
        TopicSystemState mockTopicSystemState = Mockito.mock(TopicSystemState.class);
        Mockito.when(mockTopicSystemState.getTopicName()).thenReturn(topicName);
        Mockito.when(mockTopicSystemState.getPublishers()).thenReturn(publisherSet);
        return mockTopicSystemState;
    }

}