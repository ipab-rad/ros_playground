package com.rad.rosplayground;

import com.rad.rosjava_wrapper.publish.PublisherNode;
import com.rad.rosplayground.rosjava.Vector3Factory;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import java.util.ArrayList;
import java.util.List;

import geometry_msgs.Twist;
import geometry_msgs.Vector3;

//TODO shutdown node properly so node along with its topics are removed from node list and topic list
public class TwistPublisherNode extends AbstractNodeMain implements PublisherNode{

    private static TwistPublisherNode twistPublisherNode;
    public static boolean started = false;
    private static int count;
    private static List<Publisher<Twist>> publishers;
    private ConnectedNode connectedNode;
    Object syncObject = new Object();

    public TwistPublisherNode(){
        if(publishers == null){
            publishers = new ArrayList<>();
        }
    }

    public static TwistPublisherNode getInstance(){
        if(twistPublisherNode == null){
            twistPublisherNode = new TwistPublisherNode();
        }
        return twistPublisherNode;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("TwistPublisherNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        this.connectedNode = connectedNode;
        synchronized(syncObject) {
            syncObject.notify();
        }
        CancellableLoop loop = new CancellableLoop() {
            protected void loop() throws InterruptedException {
                TwistPublisherNode.this.publishMessage();
                Thread.sleep(1000L);
            }
        };
        connectedNode.executeCancellableLoop(loop);
        started = true;
    }

    @Override
    public void publishMessage() {
        int loopCount = 0;
        for(Publisher<Twist> publisher: publishers) {
            Vector3 linearVector = Vector3Factory.makeVector3(2.0 + loopCount/2, 0, 0);
            Vector3 angularVector = Vector3Factory.makeVector3(0, 0, 1.8 - loopCount/2);
            publishTwist(publisher, linearVector, angularVector);
            loopCount += 1;
        }

    }

    private void publishTwist(Publisher<Twist> publisher, Vector3 linearVector, Vector3 angularVector) {
        Twist twist = publisher.newMessage();
        twist.setLinear(linearVector);
        twist.setAngular(angularVector);
        publisher.publish(twist);
    }

    public void addPublisher(){
        count+=1;
        if(!started) {
            waitUntilConnectedNodeIsReadyThenAddPublisher();
        } else {
            publishers.add(connectedNode.<Twist>newPublisher(GraphName.of("mock_twist_" + Integer.toString(count)), "geometry_msgs/Twist"));
        }
    }

    private void waitUntilConnectedNodeIsReadyThenAddPublisher() {
        synchronized (syncObject) {
            try {
                // Calling wait() will block this thread until another thread
                // calls notify() on the object.
                syncObject.wait();
            } catch (InterruptedException e) {
                // Happens if someone interrupts your thread.
            }
            publishers.add(connectedNode.<Twist>newPublisher(GraphName.of("mock_twist_" + Integer.toString(count)), "geometry_msgs/Twist"));
        }
    }
}
