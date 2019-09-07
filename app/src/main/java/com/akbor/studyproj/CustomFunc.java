package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class CustomFunc extends CustomView {
    Runnable predictionBarSpaceAction;
    Consumer<InputConnection> action;
    public Runnable getPredictionBarSpaceAction() {
        return predictionBarSpaceAction;
    }

    public void setPredictionBarSpaceAction(Runnable predictionBarSpaceAction) {
        this.predictionBarSpaceAction = predictionBarSpaceAction;
    }
    public void setAction(Consumer<InputConnection> action) {
        this.action = action;
    }
    public CustomFunc(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Consumer<InputConnection> action) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.action = action;
    }
    public CustomFunc(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomFunc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()== MotionEvent.ACTION_UP)
        {
            action.accept(ic.get());
            if(predictionBarSpaceAction!= null) {
                predictionBarSpaceAction.run();
            }
        }

        return true;
    }
}
