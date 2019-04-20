package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CustomFunc extends CustomView {

    Consumer<InputConnection> action;

    public CustomFunc(Context context) {
        super(context);
    }

    public void setAction(Consumer<InputConnection> action) {
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

        }

//        if (event.getAction()== MotionEvent.ACTION_DOWN)
//        {
//            //action.accept(ic.C);
//
//        }


        return true;
    }




}
