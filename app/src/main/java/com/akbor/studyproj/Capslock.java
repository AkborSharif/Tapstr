package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class Capslock extends CustomView {

   float x;
   float y;
   float x2;
   float y2;

   float dx;
   float dy;

    private boolean upperCaseMode = false;
    private Runnable toggleCapsLock;

    public Runnable getToggleCapsLock() {
        return toggleCapsLock;
    }

    public void setToggleCapsLock(Runnable toggleCapsLock) {
        this.toggleCapsLock = toggleCapsLock;
    }

    public Capslock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Capslock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Capslock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isUpperCaseMode() {
        return upperCaseMode;
    }

    public void setUpperCaseMode(boolean upperCaseMode) {
        this.upperCaseMode = upperCaseMode;
    }



    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            x = event.getX();
            y = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            x2 = event.getX();
            y2 = event.getY();
            dx = (x2-x);
            dy = (y2-y);

            if (dy<=0) {
                setUpperCase(true);
               toggleCapsLock.run();
            }
            else{
                setUpperCase(false);
                lowercaseBackgroundFunc.run();
            }

        }
        return true;
    }
}
