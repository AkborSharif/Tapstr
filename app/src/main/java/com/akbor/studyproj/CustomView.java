package com.akbor.studyproj;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import android.widget.LinearLayout;

public class CustomView extends LinearLayout {

    /**
     * supplier is a callback function to get the inputconnection from the {@link MyinputService}
     */
    protected Runnable updatebars = null;

    protected Supplier<InputConnection> ic;

    public String type;

    public Consumer<String> predictionCallback = null;

    public Consumer<String> currentTextSetter = null;

    public Supplier<String> currentTextGetter = null;

    /**
     * A special function for the layer change
     */
    private Runnable no6function = null;
    protected Runnable uppercaseBackgroundFunc = null;
    protected Runnable lowercaseBackgroundFunc = null;
    protected Runnable numr = null;
    protected Runnable spceialchar = null;
    protected Runnable specialcharbackground = null;


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

    /**
     * A boolean indicating if this keyboard is in upper case mode.
     */
    boolean upperCase = false;

    public Timer timer;
    public Timer timer1;
    /**
     * The list of characters corresponding to this keyboard section
     */
    List<String> characters;

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context); //setup();
    }
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs); //setup();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); //setup();
         }

    public void setInputConnection(Supplier<InputConnection> getIC) {
        ic = getIC;
    }

    public void setNumer(Runnable numer) {
        this.numr = numer;
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
    int counter = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override

                    public void run() {
                     if (type.equals("numeric")){
                            new Handler(Looper.getMainLooper()).post(specialcharbackground);
                        }
               // counter++;
                    }
                },500);

            timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (type.equals("lower")) {
                        setUpperCase(true);
                        new Handler(Looper.getMainLooper()).post(uppercaseBackgroundFunc);
                    }
                   else if (type.equals("upper")) {
                        setUpperCase(false);
                        new Handler(Looper.getMainLooper()).post(lowercaseBackgroundFunc);
                    }
                }
            }, 500);
        }




        if (event.getAction() == MotionEvent.ACTION_UP) {

           timer.cancel();
           timer1.cancel();
             x2 = event.getX();
             y2 = event.getY();
             dx = (x2-x);
             dy = (y2-y);
              //  System.out.print(counter);
            if (type.equals("upper")) {
               // setUpperCase(false);
               // uppercaseBackgroundFunc.run();
                lowercaseBackgroundFunc.run();
            }
           // if (type.equals("lower")) {
               // setUpperCase(false);
               // lowercaseBackgroundFunc.run();
              //  uppercaseBackgroundFunc.run();
           // }
            if (type.equals("spec")) { spceialchar.run(); }
            else if (type.equals("numeric")){ numr.run(); }

            float distance = dx*dx + dy*dy;  //d = root(x^2+y^2)
            double threshold = getWidth() * 0.2;
            // Added 360 to get rid of negative angles and
            // the -45 is an offset to match the actual keyboard
            double theta = (Math.toDegrees(Math.atan2(dx, dy)) - 45 + 360) % 360;

            if (distance < threshold * threshold ) {

                // ic.get() will call getCurrentInputConnection from MyinputService and
                // committext will set text on textfield.
               /**
                int[] keyOrder = {4, 3, 2, 5, 0, 1, 6, 7, 8};

                Map<Pair<Float, Float>, Integer> keyMap = new HashMap<>();

                for (int i = 0; i < keyOrder.length; i++) {
                    float x3 = ((i % 3) * 2 + 1) * getWidth() / 6;
                    float y3 = ((i / 3) * 2 + 1) * getHeight() / 6;
                    keyMap.put(Pair.create(x3, y3), keyOrder[i]);
                }

                for (Map.Entry<Pair<Float, Float>, Integer> e : keyMap.entrySet()) {
                    Pair<Float, Float> point = e.getKey();
                    Integer keyID = e.getValue();
                    float dx = x2 - point.first;
                    float dy = y2 - point.second;
                    if (dx * dx + dy * dy < threshold * threshold) {
                        commitText(characters.get(keyID));
                        break;
                    }
                } **/
               commitText(characters.get(0));
            }
            else {
                if (theta < 22.5) {
                    commitText(characters.get(8));
                } else if (theta<67.5){
                    commitText(characters.get(1));

                } else if (theta<112.5){
                    commitText(characters.get(2));
                }
                else if (theta<157.5 ){
                    commitText(characters.get(3));
                }
                else if (theta<202.5){
                    commitText(characters.get(4));
                }
                else if (theta<247.5){
                    commitText(characters.get(5));
                }
                else if (theta<292.5 ) {
                    if (no6function == null) {
                        commitText(characters.get(6));
                    } else {
                        no6function.run();
                         }
                }
                else if (theta<337.5){
                    commitText(characters.get(7));
                } else if (theta < 360) {
                    commitText(characters.get(8));
                }

            }
            performClick();

//           if (type.equals("upper")) {
//               setUpperCase(true);
//               uppercaseBackgroundFunc.run();
//            }


            setUpperCase(false);
        }
        return true;
    }

        private void commitText(String text) {


            ic.get().commitText(text, 1);

            String currentInput = currentTextGetter.get();
            //System.out.println(currentInput);
            if (currentTextSetter!=null) {
                currentTextSetter.accept(currentInput + text);
            }
            if (predictionCallback != null) {
                predictionCallback.accept(text);
            }
//                System.out.print(fisrtword);
//                System.out.print(secondword);

//            updatebars.run();


    }


    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNo6function(Runnable no6function) {
        this.no6function = no6function;
    }

    public void setUppercaseBackgroundFunc(Runnable uppercaseBackgroundFunc) {
        this.uppercaseBackgroundFunc = uppercaseBackgroundFunc;
    }

    public void setLowercaseBackgroundFunc(Runnable lowercaseBackgroundFunc) {
        this.lowercaseBackgroundFunc = lowercaseBackgroundFunc;
    }

    public void setSpceialchar(Runnable numeric) {
        this.spceialchar = numeric;
    }

    public void setSpecialcharbackground(Runnable specialcharbackground) {
        this.specialcharbackground = specialcharbackground;
    }

    public Consumer<String> getPredictionCallback() {
        return predictionCallback;
    }

    public Consumer<String> getCurrentTextSetter() {
        return currentTextSetter;
    }

    public void setCurrentTextSetter(Consumer<String> currentTextSetter) {
        this.currentTextSetter = currentTextSetter;
    }

    public void setPredictionCallback(Consumer<String> predictionCallback) {
        this.predictionCallback = predictionCallback;


    }

    public Supplier<String> getCurrentTextGetter() {
        return currentTextGetter;
    }

    public void setCurrentTextGetter(Supplier<String> currentTextGetter) {
        this.currentTextGetter = currentTextGetter;
    }

    public Runnable getUpdatebars() {
        return updatebars;
    }

    public void setUpdatebars(Runnable updatebars) {
        this.updatebars = updatebars;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
