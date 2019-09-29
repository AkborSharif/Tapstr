package com.akbor.studyproj;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InputMethodAction extends LinearLayout {

    public Consumer<String> currentTextSetter = null;

    public Supplier<String> currentTextGetter = null;

    public void setInputConnection(Supplier<InputConnection> getIC) {
        ic = getIC;
    }

    /**
     * x coordinate of down position (click)
     */
    float x;

    /**
     * x coordinate of up position (click)
     */
    float x2;

    /**
     * y coordinate of down position (click)
     */
    float y2;

    /**
     * y coordinate of down position (click)
     */
    float y;


    float dx;

    float dy;

    Runnable predictionBarSpaceAction;
    Runnable predictionBarDeleteAction;
    Runnable enterKeyAction;
    Runnable spaceBarAction;

    Runnable backspaceAction;

    public Runnable getPredictionBarSpaceAction() {
        return predictionBarSpaceAction;
    }

    protected Supplier<InputConnection> ic;

    public void setPredictionBarSpaceAction(Runnable predictionBarSpaceAction) {
        this.predictionBarSpaceAction = predictionBarSpaceAction;
    }


    public InputMethodAction(Context context) {
        super(context);
    }

    public InputMethodAction(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputMethodAction(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputMethodAction(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public Timer timer;

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();

            timer = new Timer();
            //action.accept(ic.get());

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            backspace();
                        }
                    }, 200, 50);
                }
            }, 500);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {

            timer.cancel();

            x2 = event.getX();
            y2 = event.getY();
            dx = (x2 - x);
            dy = (y2 - y);

            float distance = dx * dx + dy * dy;  //d = root(x^2+y^2)
            double threshold = getWidth() * 0.2;
            // Added 360 to get rid of negative angles and
            // the -45 is an offset to match the actual keyboard
            double theta = (Math.toDegrees(Math.atan2(dx, dy)) - 45 + 360) % 360;

            if (distance < threshold * threshold) {
                backspace();
            } else {
                if (0 < theta && theta < 180) {

                    enterKeyAction.run();


                } else {
                    spaceBarAction.run();
                    if (predictionBarSpaceAction != null) {
                        predictionBarSpaceAction.run();
                    }
                }
                // commitText(characters.get(1));

            }
            performClick();
        }
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    private void backspace() {
        ic.get().deleteSurroundingText(1, 0);
        new Handler(Looper.getMainLooper()).post(predictionBarDeleteAction);
        //predictionBarDeleteAction.run();
        String currentInput = "";
        if (currentTextGetter != null) {
            currentInput = currentTextGetter.get();
        }
        if (currentInput.length() > 0) {
            currentTextSetter.accept(currentInput.substring(0, currentInput.length() - 1));
        }
        new Handler(Looper.getMainLooper()).post(backspaceAction);
        //backspaceAction.run();

    }

    public Runnable getEnterKeyAction() {
        return enterKeyAction;
    }

    public void setEnterKeyAction(Runnable enterKeyAction) {
        this.enterKeyAction = enterKeyAction;
    }

    public Runnable getSpaceBarAction() {
        return spaceBarAction;
    }

    public void setSpaceBarAction(Runnable spaceBarAction) {
        this.spaceBarAction = spaceBarAction;
    }

    public Runnable getPredictionBarDeleteAction() {
        return predictionBarDeleteAction;
    }

    public void setPredictionBarDeleteAction(Runnable predictionBarDeleteAction) {
        this.predictionBarDeleteAction = predictionBarDeleteAction;
    }

    public Consumer<String> getCurrentTextSetter() {
        return currentTextSetter;
    }

    public void setCurrentTextSetter(Consumer<String> currentTextSetter) {
        this.currentTextSetter = currentTextSetter;
    }

    public Supplier<String> getCurrentTextGetter() {
        return currentTextGetter;
    }

    public void setCurrentTextGetter(Supplier<String> currentTextGetter) {
        this.currentTextGetter = currentTextGetter;
    }

    public void onBackspaceAction(Runnable backspaceAction) {
        this.backspaceAction = backspaceAction;
    }
}
