package com.akbor.studyproj;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.util.Timer;
import java.util.TimerTask;

public class Backspace extends CustomFunc {
    public Backspace(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    Runnable predictionBarDeleteAction;



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
                                backspace();
                            }
                        },200, 50);
                }
            },500);
        }
        if (event.getAction()== MotionEvent.ACTION_UP){
            timer.cancel();
            backspace();
        }

        return true;
    }

    private void backspace() {
        ic.get().deleteSurroundingText(1, 0);
        String currentInput = "";
        if (currentTextGetter != null) {
            currentInput = currentTextGetter.get();
        }
        if (currentInput.length() > 0) {
            currentTextSetter.accept(currentInput.substring(0, currentInput.length() - 1));
        }
//        predictionBarDeleteAction.run();
        new Handler(Looper.getMainLooper()).post(predictionBarDeleteAction);
    }

    public Runnable getPredictionBarDeleteAction() {
        return predictionBarDeleteAction;
    }

    public void setPredictionBarDeleteAction(Runnable predictionBarDeleteAction) {
        this.predictionBarDeleteAction = predictionBarDeleteAction;
    }

}
