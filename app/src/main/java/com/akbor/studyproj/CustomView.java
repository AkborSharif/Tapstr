package com.akbor.studyproj;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import java.util.function.Supplier;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomView extends LinearLayout {

    /**
     * supplier is a callback function to get the inputconnection from the {@link MyinputService}
     */
    private Supplier<InputConnection> ic;

    public CustomView(Context context) {
        super(context);
    }


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInputConnection(Supplier<InputConnection> getIC) {
        ic = getIC;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ic.get().commitText("a", 1);
            performClick();
        }
        // ic.get() will call getCurrentInputConnection from MyinputService and committext will set text on textfield.
        return true;
    }



    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
