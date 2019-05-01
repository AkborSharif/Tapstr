package com.akbor.studyproj;

import android.graphics.Color;
import android.hardware.camera2.params.InputConfiguration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.akbor.studyproj.R.xml.key_layout;

public class MyinputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private View keyboardView;
    boolean check = true;
    @Override
    public View onCreateInputView() {
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard_view, null);

        CustomView leftarea = keyboardView.findViewById(R.id.leftarea);
        leftarea.setInputConnection(this::getCurrentInputConnection);
        //"collect" converts the stream back to the list
        leftarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", " ", "z", "x").collect(Collectors.toList()));


        CustomView centerarea = keyboardView.findViewById(R.id.centerarea);
        centerarea.setInputConnection(this::getCurrentInputConnection);
        centerarea.setCharacters(Stream.of("g", "h", "y", "t", "r", "f", "c", "v", "b").collect(Collectors.toList()));


        CustomView rightarea = keyboardView.findViewById(R.id.rightarea);
        rightarea.setInputConnection(this::getCurrentInputConnection);
        rightarea.setCharacters(Stream.of("k", "l", "o", "i", "u", "j", "n", "m", "p").collect(Collectors.toList()));


        CustomFunc backspace  = keyboardView.findViewById(R.id.a2);
        backspace.setInputConnection(this::getCurrentInputConnection);
        backspace.setAction(ic -> ic.deleteSurroundingText(1,0));

        CustomFunc a3 = keyboardView.findViewById(R.id.a3);
        a3.setInputConnection(this::getCurrentInputConnection);
        a3.setAction(ic -> ic.commitText(" ", 1));

        CustomCapsLock capsLock = keyboardView.findViewById(R.id.a1);
        capsLock.setInputConnection(this::getCurrentInputConnection);
        Runnable toggleCapsLock = () -> {
            capsLock.setUpperCaseMode(!capsLock.isUpperCaseMode());
            leftarea.setUpperCase(capsLock.isUpperCaseMode());
            centerarea.setUpperCase(capsLock.isUpperCaseMode());
            rightarea.setUpperCase(capsLock.isUpperCaseMode());
        };
//
//        Runnable backgroundupper = () -> {
//            leftarea.setBackground(getDrawable(R.drawable.left));
//            centerarea.setBackground(getDrawable(R.drawable.middle));
//            rightarea.setBackground(getDrawable(R.drawable.right));
//        };

//        leftarea.setUppercaseBackgroundFunc(backgroundupper);
//        centerarea.setUppercaseBackgroundFunc(backgroundupper);
//        rightarea.setUppercaseBackgroundFunc(backgroundupper);

        leftarea.setNo6function(() -> {
            if (!check){
                leftarea.setBackground(getDrawable(R.drawable.first));
                centerarea.setBackground(getDrawable(R.drawable.second));
                rightarea.setBackground(getDrawable(R.drawable.third));
                check = true;
            }
            else {
                leftarea.setBackgroundColor(Color.BLUE);
                centerarea.setBackgroundColor(Color.YELLOW);
                rightarea.setBackgroundColor(Color.RED);
                check = false;
            }
        });


        capsLock.setToggleCapsLock(toggleCapsLock);



        return keyboardView;
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if(inputConnection != null)
        {
            switch (primaryCode){

            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
