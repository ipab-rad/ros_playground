package com.rad.rosplayground;

import android.view.ViewGroup;

import java.net.URI;

public class ModuleViewFactory {
    public static ModuleView makeTwistPublisherModuleView(ModuleListActivity moduleListActivity, URI masterUri) {
        ModuleView moduleView = new TwistPublisherModuleView(moduleListActivity, masterUri);
        //TODO: set x and y position correctly if something is already occupying that space
        moduleView.setX(250);
        moduleView.setY(250);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(250,250); //TODO set height and width correctly according to view
        moduleView.setLayoutParams(layoutParams);
        moduleView.setOnTouchListener(moduleView);
        return moduleView;
    }
}
