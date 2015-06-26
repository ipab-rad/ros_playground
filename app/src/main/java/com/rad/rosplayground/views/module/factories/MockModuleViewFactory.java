package com.rad.rosplayground.views.module.factories;

import com.rad.rosplayground.activities.ModuleListActivity;
import com.rad.rosplayground.views.module.ModuleView;
import com.rad.rosplayground.views.module.mock.TimePublisherModuleView;
import com.rad.rosplayground.views.module.mock.TwistPublisherModuleView;
import com.rad.rosplayground.views.module.utils.ViewUtils;

import java.net.URI;

//TODO: set x and y position correctly if something is already occupying that space
//TODO set height and width correctly according to view
public class MockModuleViewFactory {
    public static ModuleView makeTwistPublisherModuleView(ModuleListActivity moduleListActivity, URI masterUri, String topicName) {
        ModuleView moduleView = new TwistPublisherModuleView(moduleListActivity, masterUri);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        moduleView.setText(topicName);
        return moduleView;
    }

    public static ModuleView makeTimePublisherModuleView(ModuleListActivity moduleListActivity, URI masterUri) {
        ModuleView moduleView = new TimePublisherModuleView(moduleListActivity, masterUri);
        ViewUtils.setPosition(moduleView, 250, 250);
        ViewUtils.setBackground(moduleView);
        ViewUtils.setSize(moduleView, 250, 100);
        moduleView.setOnTouchListener(moduleView);
        return moduleView;
    }
}
