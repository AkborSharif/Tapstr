package com.akbor.studyproj;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PredictionKey extends AppCompatTextView {
    public Supplier<InputConnection> getIc() {
        return ic;
    }

    public void setIc(Supplier<InputConnection> ic) {
        this.ic = ic;
    }

    protected Supplier<InputConnection> ic;

    public PredictionKey(Context context) {
        super(context);
    }

    public PredictionKey(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PredictionKey(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){}

        if (event.getAction() == MotionEvent.ACTION_UP){
            ic.get().deleteSurroundingText(getText().length(), 0);
            ic.get().commitText(getText() + " ", 1);
//            ic.get().commitText(" ", 1);
            setText("");
        }
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
