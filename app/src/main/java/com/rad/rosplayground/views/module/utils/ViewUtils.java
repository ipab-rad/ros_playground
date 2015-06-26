package com.rad.rosplayground.views.module.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.rad.rosplayground.R;
import com.rad.rosplayground.views.module.ModuleView;

public class ViewUtils {
    public static void setBackground(View view) {
        Drawable border = ContextCompat.getDrawable(view.getContext(), R.drawable.back);
        view.setBackground(border);
    }

    public static void setPosition(View view, int x, int y) {
        view.setX(x);
        view.setY(y);
    }

    public static void setSize(ModuleView moduleView, int width, int height) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,height);
        moduleView.setLayoutParams(layoutParams);
    }

}
