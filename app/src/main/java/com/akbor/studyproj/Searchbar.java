package com.akbor.studyproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.function.Consumer;

public class Searchbar extends CustomFunc {


    public Searchbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Consumer<InputConnection> action) {
        super(context, attrs, defStyleAttr, defStyleRes, action);
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()== MotionEvent.ACTION_UP)
        {

        }

        return true;
    }

}
