package com.rad.rosplayground.views.module;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public abstract class ModuleView extends TextView implements View.OnTouchListener{
    float _xDelta;
    float _yDelta;

    public ModuleView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                _xDelta = X - v.getX();
                _yDelta = Y - v.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                v.setX(X - _xDelta);
                v.setY(Y - _yDelta);
                invalidate();
                break;
        }
        return true;
    }

}
