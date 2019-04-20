package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class CustomCapsLock extends CustomFunc {

    GestureDetector gestureDetector;

    private boolean upperCaseMode = false;

    private Runnable toggleCapsLock;

    public CustomCapsLock(Context context, AttributeSet attrs) {
        super(context, attrs);
        // creating new gesture detector
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public CustomCapsLock(Context context, AttributeSet attrs, GestureDetector gestureDetector) {
        super(context, attrs);
        this.gestureDetector = gestureDetector;
    }

    public CustomCapsLock(Context context, AttributeSet attrs, int defStyleAttr, GestureDetector gestureDetector) {
        super(context, attrs, defStyleAttr);
        this.gestureDetector = gestureDetector;
    }

    public boolean isUpperCaseMode() {
        return upperCaseMode;
    }

    public void setUpperCaseMode(boolean upperCaseMode) {
        this.upperCaseMode = upperCaseMode;
    }

    // delegate the event to the gesture detector
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction()== MotionEvent.ACTION_UP) {
//            action.accept(ic.get());

            toggleCapsLock.run();
        }
        return gestureDetector.onTouchEvent(e);
    }

    public void setToggleCapsLock(Runnable toggleCapsLock) {
        this.toggleCapsLock = toggleCapsLock;
    }

    public Runnable getToggleCapsLock() {
        return toggleCapsLock;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");
            return true;
        }

    }


    }

