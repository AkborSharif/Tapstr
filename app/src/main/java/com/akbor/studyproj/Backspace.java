package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Backspace extends CustomFunc {
    public Backspace(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Timer timer;
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()== MotionEvent.ACTION_DOWN)
        {
            timer = new Timer();
            //action.accept(ic.get());

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                action.accept(ic.get());
                            }
                        },200, 50);
                }
            },500);
        }
        if (event.getAction()== MotionEvent.ACTION_UP){
            timer.cancel();

            action.accept(ic.get());
        }

        return true;
    }
}
