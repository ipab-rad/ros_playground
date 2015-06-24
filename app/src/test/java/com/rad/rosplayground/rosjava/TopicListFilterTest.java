package com.rad.rosplayground.rosjava;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ros.master.client.TopicType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TopicListFilterTest {

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
    public void givenEmptyTopicList_FilteringTopicsStartingWithTestTopic1ReturnsEmptyList() {
        List<TopicType> topicList = new ArrayList<>();
        List<TopicType> filteredTopicList = TopicListFilter.topicsStartingWith(topicList, "testTopic1");
        assertThat(filteredTopicList).isEmpty();
    }

    @Test
    public void givenTopicListWithOneTopicStartingWith_TestTopic1_FilteringTopicsStartingWithTestTopic1ReturnsSameList() {
        String testTopicName = "/" + testTopic1 + "/test_vel";

        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(testTopicName);

        testTopicList.add(mockTopic);

        List<TopicType> filteredTopicList = TopicListFilter.topicsStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList.size()).isEqualTo(1);
        assertThat(filteredTopicList.get(0).getName()).isEqualTo(testTopicName);
    }

    @Test
    public void givenTopicListWithOneTopicStartingWith_TestTopic2_FilteringTopicsStartingWithTestTopic1ReturnsSameList() {
        String testTopicName = "/" + testTopic2 + "/test_vel";

        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(testTopicName);

        testTopicList.add(mockTopic);

        List<TopicType> filteredTopicList = TopicListFilter.topicsStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList).isEmpty();
    }

    @Test
    public void givenTopicListWith5Topics_3StartingWith_TestTopic1_FilteringTopicsStartingWithTestTopic1ReturnsListOf3() {
        String testTopicName1 = "/" + testTopic1 + "/test_vel";
        String testTopicName2 = "/" + testTopic1 + "/test_accel";
        String testTopicName3 = "/" + testTopic1 + "/test_pos";
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

        List<TopicType> filteredTopicList = TopicListFilter.topicsStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList.size()).isEqualTo(3);
        assertThat(filteredTopicList).contains(mockTopic1);
        assertThat(filteredTopicList).contains(mockTopic2);
        assertThat(filteredTopicList).contains(mockTopic3);
    }

    @Test
    public void givenEmptyTopicList_FilteringTopicsNotStartingWithTestTopic1ReturnsEmptyList() {
        List<TopicType> topicList = new ArrayList<>();
        List<TopicType> filteredTopicList = TopicListFilter.topicsNotStartingWith(topicList, "testTopic1");
        assertThat(filteredTopicList).isEmpty();
    }

    @Test
    public void givenTopicListWithOneTopicStartingWith_TestTopic1_FilteringTopics_NotStartingWithTestTopic1ReturnsEmptyList() {
        String testTopicName = "/" + testTopic1 + "/test_vel";

        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(testTopicName);

        testTopicList.add(mockTopic);

        List<TopicType> filteredTopicList = TopicListFilter.topicsNotStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList).isEmpty();
    }

    @Test
    public void givenTopicListWithOneTopicStartingWith_TestTopic2_FilteringTopics_NotStartingWithTestTopic1ReturnsSameList() {
        String testTopicName = "/" + testTopic2 + "/test_vel";

        TopicType mockTopic = Mockito.mock(TopicType.class);
        Mockito.when(mockTopic.getName()).thenReturn(testTopicName);

        testTopicList.add(mockTopic);

        List<TopicType> filteredTopicList = TopicListFilter.topicsNotStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList.size()).isEqualTo(1);
        assertThat(filteredTopicList.get(0).getName()).isEqualTo(testTopicName);
    }

    @Test
    public void givenTopicListWith5Topics_3StartingWith_TestTopic1_FilteringTopicsStartingWithTestTopic1ReturnsListOf2() {
        String testTopicName1 = "/" + testTopic1 + "/test_vel";
        String testTopicName2 = "/" + testTopic1 + "/test_accel";
        String testTopicName3 = "/" + testTopic1 + "/test_pos";
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

        List<TopicType> filteredTopicList = TopicListFilter.topicsNotStartingWith(testTopicList, testTopic1);
        assertThat(filteredTopicList.size()).isEqualTo(2);
        assertThat(filteredTopicList).contains(mockTopic4);
        assertThat(filteredTopicList).contains(mockTopic5);
    }

}