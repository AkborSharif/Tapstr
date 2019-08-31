package com.akbor.studyproj;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//        CustomCapsLock capsLock = keyboardView.findViewById(R.id.a0);
//       capsLock.setInputConnection(this::getCurrentInputConnection);
//        Runnable toggleCapsLock = () -> {
//            capsLock.setUpperCaseLocked(!capsLock.isUpperCaseLocked());
//            leftarea.setUpperCase(capsLock.isUpperCaseLocked());
//            centerarea.setUpperCase(capsLock.isUpperCaseLocked());
//            rightarea.setUpperCase(capsLock.isUpperCaseLocked());
//            if (capsLock.isUpperCaseLocked()) {
//                leftarea.setBackground(getDrawable(R.drawable.upl));
//                centerarea.setBackground(getDrawable(R.drawable.upm));
//                rightarea.setBackground(getDrawable(R.drawable.upr));
//            } else {
//                leftarea.setBackground(getDrawable(R.drawable.first));
//                centerarea.setBackground(getDrawable(R.drawable.second));
//                rightarea.setBackground(getDrawable(R.drawable.third));
//            }
//
//        };
//        capsLock.setToggleCapsLock(toggleCapsLock);

        CustomFunc newline  = keyboardView.findViewById(R.id.a1);
        newline.setInputConnection(this::getCurrentInputConnection);
        newline.setAction(ic -> ic.commitText("\n", 0));




        CustomFunc backspace  = keyboardView.findViewById(R.id.a2);
        backspace.setInputConnection(this::getCurrentInputConnection);
        backspace.setAction(ic -> ic.deleteSurroundingText(1,0));

        Backspace longdelete = keyboardView.findViewById(R.id.a2);
        backspace.setInputConnection(this::getCurrentInputConnection);
        backspace.setAction(ic -> ic.deleteSurroundingText(1,0));

        CustomFunc space = keyboardView.findViewById(R.id.a3);
        space.setInputConnection(this::getCurrentInputConnection);
        space.setAction(ic -> ic.commitText(" ", 1));


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

        Runnable specialcharbackground = () -> {
            leftarea.setBackground(getDrawable(R.drawable.emoj1));
            centerarea.setBackground(getDrawable(R.drawable.middnum));
            rightarea.setBackground(getDrawable(R.drawable.hiddenr));

              leftarea.setType("spec");
              centerarea.setType("spec");
              rightarea.setType("spec");
        };

        Runnable numer =()-> {
            leftarea.setCharacters(Stream.of("•", "/", "]", "`", "[", "\\", "", "✓", "️🙂").collect(Collectors.toList()));
            centerarea.setCharacters(Stream.of("5", "6", "3", "2", "1", "4", "7", "8", "9").collect(Collectors.toList()));
            rightarea.setCharacters(Stream.of(".", ";", "√", "=", "-", ",", "0", "×", "’").collect(Collectors.toList()));
            leftarea.setType("numeric");
            centerarea.setType("numeric");
            rightarea.setType("numeric");
        };
        Runnable specialchar = () -> {
            leftarea.setBackground(getDrawable(R.drawable.emoj2));
            centerarea.setBackground(getDrawable(R.drawable.number));
            rightarea.setBackground(getDrawable(R.drawable.charr));
            leftarea.setCharacters(Stream.of("°", "?", "}", "~", "{", "|", "", "¢", "🙂").collect(Collectors.toList()));
            centerarea.setCharacters(Stream.of("%", "^", "#", "@", "!", "$", "&", "*", "(").collect(Collectors.toList()));
            rightarea.setCharacters(Stream.of(">", ":", "±", "+", "_", "<", ")", "÷", "\"").collect(Collectors.toList()));

            leftarea.setType("numeric");
            centerarea.setType("numeric");
            rightarea.setType("numeric");
        };

        leftarea.setNumer(numer);
        centerarea.setNumer(numer);
        rightarea.setNumer(numer);


        leftarea.setSpceialchar(specialchar);
        centerarea.setSpceialchar(specialchar);
         rightarea.setSpceialchar(specialchar);

        leftarea.setSpecialcharbackground(specialcharbackground);
        centerarea.setSpecialcharbackground(specialcharbackground);
        rightarea.setSpecialcharbackground(specialcharbackground);

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

                leftarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", "", "z", "x").collect(Collectors.toList()));
                centerarea.setCharacters(Stream.of("g", "h", "y", "t", "r", "f", "c", "v", "b").collect(Collectors.toList()));
                rightarea.setCharacters(Stream.of("k", "l", "o", "i", "u", "j", "n", "m", "p").collect(Collectors.toList()));
                leftarea.setType("lower");
                centerarea.setType("lower");
                rightarea.setType("lower");
                check = true;
            }
            else {

                leftarea.setBackground(getDrawable(R.drawable.emoj2));
                centerarea.setBackground(getDrawable(R.drawable.number));
                rightarea.setBackground(getDrawable(R.drawable.charr));

//                leftarea.setCharacters(Stream.of(".", "\"", ";", ",", ":", "'", "", "!", "?").collect(Collectors.toList()));
//                centerarea.setCharacters(Stream.of("5", "6", "3", "2", "1", "4", "7", "8", "9").collect(Collectors.toList()));
//                rightarea.setCharacters(Stream.of("0", "&", "-", "@", "+", "$", "*", "#", "/").collect(Collectors.toList()));

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
