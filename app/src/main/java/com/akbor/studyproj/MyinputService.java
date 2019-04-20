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

    @Override
    public View onCreateInputView() {
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard_view, null);

        CustomView rightarea = keyboardView.findViewById(R.id.rightarea);
        rightarea.setInputConnection(this::getCurrentInputConnection);
        //"collect" converts the stream back to the list
        rightarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", "z", "x", "c").collect(Collectors.toList()));

        CustomView centerarea = keyboardView.findViewById(R.id.centerarea);
        centerarea.setInputConnection(this::getCurrentInputConnection);
        centerarea.setCharacters(Stream.of("g", "h", "y", "t", "r", "f", " ", "v", "u").collect(Collectors.toList()));


        CustomView leftarea = keyboardView.findViewById(R.id.leftrarea);
        leftarea.setInputConnection(this::getCurrentInputConnection);
        leftarea.setCharacters(Stream.of("k", "l", "p", "o", "i", "j", "b", "n", "m").collect(Collectors.toList()));


        CustomFunc backspace  = keyboardView.findViewById(R.id.a2);
        backspace.setInputConnection(this::getCurrentInputConnection);
        backspace.setAction(ic -> ic.deleteSurroundingText(1,0));

        CustomCapsLock capsLock = keyboardView.findViewById(R.id.a3);
        capsLock.setInputConnection(this::getCurrentInputConnection);
        Runnable toggleCapsLock = () -> {
            capsLock.setUpperCaseMode(!capsLock.isUpperCaseMode());
            rightarea.setUpperCase(capsLock.isUpperCaseMode());
            centerarea.setUpperCase(capsLock.isUpperCaseMode());
            leftarea.setUpperCase(capsLock.isUpperCaseMode());
        };
        capsLock.setToggleCapsLock(toggleCapsLock);
//        if (CapsLock.performClick()) {
//                rightarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", "z", "x", "c").collect(Collectors.toList()));
//
//        }
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
