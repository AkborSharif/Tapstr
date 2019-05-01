package com.akbor.studyproj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomView extends LinearLayout {

    /**
     * supplier is a callback function to get the inputconnection from the {@link MyinputService}
     */
    protected Supplier<InputConnection> ic;

    /**
     * A special function for the layer change
     */
    private Runnable no6function = null;
    private Runnable uppercaseBackgroundFunc = null;
    /**
     * x coordinate of down position (click)
     */
    float x;

    /**
     * y coordinate of down position (click)
     */
    float y;

    /**
     * A boolean indicating if this keyboard is in upper case mode.
     */
    boolean upperCase = false;

    /**
     *
     */
    private Timer timer;

    /**
     * The list of characters corresponding to this keyboard section
     */
    List<String> characters;

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

    public void setUpperCase(boolean isUpper) {
        if (upperCase != isUpper) {
            if (isUpper) {
                characters = characters.stream()
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());
            } else {
                characters = characters.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
            }
            upperCase = isUpper;
        }
    }
    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setUpperCase(true);
//                    ((Activity) getContext()).runOnUiThread(() -> {
//                        uppercaseBackgroundFunc.run();
//                    });
                }
            }, 1000);

        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            timer.cancel();
            float x2 = event.getX();
            float y2 = event.getY();
            float dx = (x2-x);
            float dy = (y2-y);

            float distance = dx*dx + dy*dy;  //d = root(x^2+y^2)
            double threshold = getWidth() * 0.2;
            // Added 360 to get rid of negative angles and
            // the -45 is an offset to match the actual keyboard
            double theta = (Math.toDegrees(Math.atan2(dx, dy)) - 45 + 360) % 360;

            if (distance < threshold * threshold ) {
                // ic.get() will call getCurrentInputConnection from MyinputService and
                // committext will set text on textfield.
                ic.get().commitText(characters.get(0), 1);
            } else {
                if (theta < 22.5) {
                    ic.get().commitText(characters.get(8), 1);
                } else if (theta<67.5){
                    ic.get().commitText(characters.get(1), 1);
                } else if (theta<112.5){
                    ic.get().commitText(characters.get(2), 1);
                }
                else if (theta<157.5 ){
                    ic.get().commitText(characters.get(3), 1);
                }
                else if (theta<202.5){
                    ic.get().commitText(characters.get(4), 1);
                }
                else if (theta<247.5){
                    ic.get().commitText(characters.get(5), 1);
                }
                else if (theta<292.5 ){
                    if (no6function == null) {
                        ic.get().commitText(characters.get(6), 1);
                    } else {
                        no6function.run();
                    }
                }
                else if (theta<337.5){
                    ic.get().commitText(characters.get(7), 1);
                }
            }
            performClick();
            setUpperCase(false);
        }
        return true;
    }

    public void setNo6function(Runnable no6function) {
        this.no6function = no6function;
    }

    public void setUppercaseBackgroundFunc(Runnable uppercaseBackgroundFunc) {
        this.uppercaseBackgroundFunc = uppercaseBackgroundFunc;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
