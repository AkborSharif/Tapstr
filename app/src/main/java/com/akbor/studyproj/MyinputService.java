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

        leftarea.setType("lower");
        centerarea.setType("lower");
        rightarea.setType("lower");

        Capslock capsLock = keyboardView.findViewById(R.id.minipart);
        // capsLock.setInputConnection(this::getCurrentInputConnection);
        Runnable toggleCapsLock = () -> {
            capsLock.setUpperCaseMode(!capsLock.isUpperCaseMode());
            leftarea.setUpperCase(capsLock.isUpperCaseMode());
            centerarea.setUpperCase(capsLock.isUpperCaseMode());
            rightarea.setUpperCase(capsLock.isUpperCaseMode());
        };

        CustomFunc newline  = keyboardView.findViewById(R.id.a1);
        newline.setInputConnection(this::getCurrentInputConnection);
        newline.setAction(ic -> ic.commitText("\n", 0));

        CustomFunc backspace  = keyboardView.findViewById(R.id.a2);
        backspace.setInputConnection(this::getCurrentInputConnection);
        backspace.setAction(ic -> ic.deleteSurroundingText(1,0));

        CustomFunc space = keyboardView.findViewById(R.id.a3);
        space.setInputConnection(this::getCurrentInputConnection);
        space.setAction(ic -> ic.commitText(" ", 1));


        capsLock.setToggleCapsLock(toggleCapsLock);

        Runnable backgroundupper = () -> {
            leftarea.setBackground(getDrawable(R.drawable.upl));
            centerarea.setBackground(getDrawable(R.drawable.upm));
            rightarea.setBackground(getDrawable(R.drawable.upr));
            leftarea.setType("upper");
            centerarea.setType("upper");
            rightarea.setType("upper");
        };

        Runnable backgroundlower = () -> {
            leftarea.setBackground(getDrawable(R.drawable.first));
            centerarea.setBackground(getDrawable(R.drawable.second));
            rightarea.setBackground(getDrawable(R.drawable.third));
            leftarea.setType("lower");
            centerarea.setType("lower");
            rightarea.setType("lower");
        };

        leftarea.setLowercaseBackgroundFunc(backgroundlower);
        centerarea.setLowercaseBackgroundFunc(backgroundlower);
        rightarea.setLowercaseBackgroundFunc(backgroundlower);

        leftarea.setUppercaseBackgroundFunc(backgroundupper);
        centerarea.setUppercaseBackgroundFunc(backgroundupper);
        rightarea.setUppercaseBackgroundFunc(backgroundupper);

        //when "check" is true programe will change the layers to nubers and special characters
        leftarea.setNo6function(() -> {
            if (!check){
                leftarea.setBackground(getDrawable(R.drawable.first));
                centerarea.setBackground(getDrawable(R.drawable.second));
                rightarea.setBackground(getDrawable(R.drawable.third));

                leftarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", " ", "z", "x").collect(Collectors.toList()));
                centerarea.setCharacters(Stream.of("g", "h", "y", "t", "r", "f", "c", "v", "b").collect(Collectors.toList()));
                rightarea.setCharacters(Stream.of("k", "l", "o", "i", "u", "j", "n", "m", "p").collect(Collectors.toList()));
                leftarea.setType("lower");
                centerarea.setType("lower");
                rightarea.setType("lower");
                check = true;
            }
            else {
                leftarea.setBackground(getDrawable(R.drawable.lgc));
                centerarea.setBackground(getDrawable(R.drawable.number));
                rightarea.setBackground(getDrawable(R.drawable.sign));

                leftarea.setCharacters(Stream.of(".", "\"", ";", ",", ":", "'", " ", "!", "?").collect(Collectors.toList()));
                centerarea.setCharacters(Stream.of("5", "6", "3", "2", "1", "4", "7", "8", "9").collect(Collectors.toList()));
                rightarea.setCharacters(Stream.of("0", "&", "-", "@", "+", "$", "*", "#", "/").collect(Collectors.toList()));
                leftarea.setType("numeric");
                centerarea.setType("numeric");
                rightarea.setType("numeric");
                check = false;
            }
        });

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
