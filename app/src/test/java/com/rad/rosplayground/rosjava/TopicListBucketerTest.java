package com.rad.rosplayground.rosjava;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicListBucketerTest {

    private List<TopicType> testTopicList;
    private String testTopic1;
    private String testTopic2;

    @Before
    public void setUp() throws Exception {
        testTopicList = new ArrayList<>();
        testTopic1 = "testTopic1";
        testTopic2 = "testTopic2";
    }

    @Test
    public void givenEmptyTopicList_WhenSortedIntoBuckets_EmptyMapIsReturned() {
        List<TopicType> topicList = new ArrayList<>();
        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(topicList);
        assertThat(bucketedTopics.size()).isEqualTo(0);
    }

    @Test
    public void givenTopicListWith1Topic_WhenSortedIntoBuckets_MapWith1EntryIsReturned() {
        String testTopicName = "/" + testTopic1 + "/test_vel";

        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(testTopicName);

        testTopicList.add(mockTopic);
        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(testTopicList);
        assertThat(bucketedTopics.size()).isEqualTo(1);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic);
    }

    @Test
    public void givenTopicListWith2Topic_WithSameStartingPart_WhenSortedIntoBuckets_MapWith1EntryIsReturned() {
        String testTopicName1 = "/" + testTopic1 + "/test_vel";
        String testTopicName2 = "/" + testTopic1 + "/test_pos";

        TopicType mockTopic1 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic1.getName()).thenReturn(testTopicName1);
        TopicType mockTopic2 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic2.getName()).thenReturn(testTopicName2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(testTopicList);
        assertThat(bucketedTopics.size()).isEqualTo(1);
        assertThat(bucketedTopics.get(testTopic1).size()).isEqualTo(2);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic1);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic2);
    }

    @Test
    public void givenTopicListWith2Topic_WithDifferentStartingPart_WhenSortedIntoBuckets_MapWith2EntriesIsReturned() {
        String testTopicName1 = "/" + testTopic1 + "/test_vel";
        String testTopicName2 = "/" + testTopic2 + "/test_pos";

        TopicType mockTopic1 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic1.getName()).thenReturn(testTopicName1);
        TopicType mockTopic2 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic2.getName()).thenReturn(testTopicName2);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);

        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(testTopicList);
        assertThat(bucketedTopics.size()).isEqualTo(2);
        assertThat(bucketedTopics.get(testTopic1).size()).isEqualTo(1);
        assertThat(bucketedTopics.get(testTopic2).size()).isEqualTo(1);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic1);
        assertThat(bucketedTopics.get(testTopic2)).contains(mockTopic2);
    }

    @Test
    public void givenTopicListWith5Topic_WithTwoDifferentStartingParts_WhenSortedIntoBuckets_MapWith2EntriesIsReturned() {
        String testTopicName1 = "/" + testTopic1 + "/test_vel";
        String testTopicName2 = "/" + testTopic1 + "/test_pos";
        String testTopicName3 = "/" + testTopic2 + "/test_accel";
        String testTopicName4 = "/" + testTopic2 + "/test_vel";
        String testTopicName5 = "/" + testTopic2 + "/test_pos";

        TopicType mockTopic1 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic1.getName()).thenReturn(testTopicName1);
        TopicType mockTopic2 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic2.getName()).thenReturn(testTopicName2);
        TopicType mockTopic3 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic3.getName()).thenReturn(testTopicName3);
        TopicType mockTopic4 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic4.getName()).thenReturn(testTopicName4);
        TopicType mockTopic5 = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic5.getName()).thenReturn(testTopicName5);

        testTopicList.add(mockTopic1);
        testTopicList.add(mockTopic2);
        testTopicList.add(mockTopic3);
        testTopicList.add(mockTopic4);
        testTopicList.add(mockTopic5);

        Map<String, List<TopicType>> bucketedTopics = TopicListBucketer.bucketTopics(testTopicList);
        assertThat(bucketedTopics.size()).isEqualTo(2);
        assertThat(bucketedTopics.get(testTopic1).size()).isEqualTo(2);
        assertThat(bucketedTopics.get(testTopic2).size()).isEqualTo(3);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic1);
        assertThat(bucketedTopics.get(testTopic1)).contains(mockTopic2);
        assertThat(bucketedTopics.get(testTopic2)).contains(mockTopic3);
        assertThat(bucketedTopics.get(testTopic2)).contains(mockTopic4);
        assertThat(bucketedTopics.get(testTopic2)).contains(mockTopic5);
    }
}