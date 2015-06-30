package com.rad.rosplayground.views.module;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;

import com.rad.rosplayground.activities.ModuleListActivity;
import com.rad.rosplayground.rosjava.msg.actors.Actor;
import com.rad.rosplayground.rosjava.node.PublisherPopupMenu;
import com.rad.rosplayground.rosjava.node.SubscriberNode;
import com.rad.rosplayground.rosjava.utils.ROSUtils;

import org.ros.master.client.MasterStateClient;
import org.ros.master.client.TopicType;

import java.net.URI;

public class SubscriberModuleView extends ModuleView {

    private MasterStateClient masterStateClient;
    private SubscriberNode subscriberNode;
    private TopicType topicToPublishTo;
    private ModuleListActivity parentActivity;
    private boolean isOnClick;
    private int MOVE_THRESHOLD = 50;
    private int initialX;
    private int initialY;

    public SubscriberModuleView(Context context, URI masterURI, TopicType topicToSubscribeTo, Actor actor, MasterStateClient masterStateClient) {
        super(context);
        this.masterStateClient = masterStateClient;
        subscriberNode = new SubscriberNode(topicToSubscribeTo, actor);
        ROSUtils.executeNode(masterURI, subscriberNode);
        this.topicToPublishTo = topicToSubscribeTo;
        this.parentActivity = (ModuleListActivity) context;
    }

    //TODO make more generic and move up to ModuleView
    //TODO add delete of node on double tap with GestureListener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                _xDelta = X - v.getX();
                _yDelta = Y - v.getY();
                initialX = X;
                initialY = Y;
                isOnClick = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isOnClick) {
                    showAvailablePublishersMenu(v);
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnClick && (Math.abs(initialX - X) > MOVE_THRESHOLD || Math.abs(initialY - Y) > MOVE_THRESHOLD)) {
                    isOnClick = false;
                }
                v.setX(X - _xDelta);
                v.setY(Y - _yDelta);
                invalidate();
                break;
        }
        return true;
    }

    private void showAvailablePublishersMenu(View v) {
        PopupMenu popup = new PublisherPopupMenu(v.getContext(), v, masterStateClient, parentActivity, topicToPublishTo);
        popup.show();
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
    }
}
