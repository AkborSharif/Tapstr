package com.akbor.studyproj;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyinputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private View keyboardView;
    boolean check = true;

    private Map<Character, List<String>> dictionary = new HashMap<>();

    private String currentInput = "";

    @Override
    public View onCreateInputView() {
        setup();
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard_view, null);

        PredictionKey firstlayer = keyboardView.findViewById(R.id.pred1);
        firstlayer.setIc(this::getCurrentInputConnection);

        PredictionKey secondlayer = keyboardView.findViewById(R.id.pred2);
        secondlayer.setIc(this::getCurrentInputConnection);

        PredictionKey thirdlayer = keyboardView.findViewById(R.id.pred3);
        thirdlayer.setIc(this::getCurrentInputConnection);

        Runnable updatePredictionbar = () -> placeholder(secondlayer, thirdlayer);

        CustomView leftarea = keyboardView.findViewById(R.id.leftarea);
        leftarea.setInputConnection(this::getCurrentInputConnection);
        //"collect" converts the stream back to the list
        leftarea.setCharacters(Stream.of("s", "d", "e", "w", "q", "a", "", "z", "x").collect(Collectors.toList()));
        leftarea.setCurrentTextSetter(input -> currentInput = input);
        leftarea.setCurrentTextGetter(()-> currentInput);
        leftarea.setPredictionCallback(firstlayer::append);
        leftarea.setUpdatebars(updatePredictionbar);

        CustomView centerarea = keyboardView.findViewById(R.id.centerarea);
        centerarea.setInputConnection(this::getCurrentInputConnection);
        centerarea.setCharacters(Stream.of("g", "h", "y", "t", "r", "f", "c", "v", "b").collect(Collectors.toList()));
        centerarea.setCurrentTextSetter(input -> currentInput = input);
        centerarea.setCurrentTextGetter(()-> currentInput);
        centerarea.setPredictionCallback(firstlayer::append);
        centerarea.setUpdatebars(updatePredictionbar);

        CustomView rightarea = keyboardView.findViewById(R.id.rightarea);
        rightarea.setInputConnection(this::getCurrentInputConnection);
        rightarea.setCharacters(Stream.of("k", "l", "o", "i", "u", "j", "n", "m", "p").collect(Collectors.toList()));
        rightarea.setCurrentTextSetter(input -> currentInput = input);
        rightarea.setCurrentTextGetter(()-> currentInput);
        rightarea.setPredictionCallback(firstlayer::append);
        rightarea.setUpdatebars(updatePredictionbar);





        leftarea.setType("lower");
        centerarea.setType("lower");
        rightarea.setType("lower");


        firstlayer.onCommitTextAction(() -> {
            getCurrentInputConnection().deleteSurroundingText(currentInput.length(), 0);
            secondlayer.setText("");
            thirdlayer.setText("");
        });
        secondlayer.onCommitTextAction(() -> {
            getCurrentInputConnection().deleteSurroundingText(currentInput.length(), 0);
            firstlayer.setText("");
            thirdlayer.setText("");
        });
        thirdlayer.onCommitTextAction(() -> {
            getCurrentInputConnection().deleteSurroundingText(currentInput.length(), 0);
            firstlayer.setText("");
            secondlayer.setText("");
        });
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


        InputMethodAction inputMethodAction = keyboardView.findViewById(R.id.minipart);
        inputMethodAction.setInputConnection(this::getCurrentInputConnection);
        inputMethodAction.setEnterKeyAction(()-> this.getCurrentInputConnection().commitText("\n", 0));

        inputMethodAction.setPredictionBarDeleteAction(() -> {
            if (firstlayer.getText().length() > 0) {
                firstlayer.setText(firstlayer.getText().subSequence(0, firstlayer.getText().length() - 1));
            }
        });
        inputMethodAction.setSpaceBarAction(()->this.getCurrentInputConnection().commitText(" ", 1));
        inputMethodAction.setPredictionBarSpaceAction(()->{
            if (firstlayer.getText().length() > 0) {
//                firstlayer.setText(firstlayer.getText().subSequence(0, firstlayer.getText().length() - firstlayer.getText().length()));
                firstlayer.setText("");
            }
        });

        inputMethodAction.setCurrentTextSetter(input -> currentInput = input);
        inputMethodAction.setCurrentTextGetter(()-> currentInput);
        inputMethodAction.onBackspaceAction(() -> placeholder(secondlayer, thirdlayer));

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


    static int min(int x,int y,int z)
    {
        if (x <= y && x <= z) return x;
        if (y <= x && y <= z) return y;
        else return z;
    }

    static int editDistDP(CharSequence str1, CharSequence str2) {
        // Create a table to store results of subproblems

        int str1Length = str1.length();

        int str2Length = str2.length();

        int dp[][] = new int[str1Length + 1][str2Length + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= str1Length; i++) {
            for (int j = 0; j <= str2Length; j++) {
                // If first string is empty, only option is to
                // insert all characters of second string
                if (i == 0)
                    dp[i][j] = j;  // Min. operations = j

                    // If second string is empty, only option is to
                    // remove all characters of second string
                else if (j == 0)
                    dp[i][j] = i; // Min. operations = i

                    // If last characters are same, ignore last char
                    // and recur for remaining string
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];

                    // If the last character is different, consider all
                    // possibilities and find the minimum
                else
                    dp[i][j] = 1 + min(dp[i][j - 1],  // Insert
                            dp[i - 1][j],  // Remove
                            dp[i - 1][j - 1]); // Replace
            }
        }
        return dp[str1Length][str2Length];
    }

    List<String> bestFits;

    private void placeholder(PredictionKey layer2, PredictionKey layer3) {

        String str1 = currentInput.toLowerCase();

        if (str1.length() == 0) {
            layer2.setText("");
            layer3.setText("");
            return;
        }


        List<String> section = dictionary.get(str1.charAt(0));

        if (section == null) {
            return;
        }

        bestFits = section.stream()
                .sorted((w1, w2) -> editDistDP(str1, w1) - editDistDP(str1, w2))
                .limit(2)
                .collect(Collectors.toList());

        if (bestFits.size() > 0) {
            layer2.setText(bestFits.get(0));
        } else {
            layer2.setText("");
        }

        if (bestFits.size() > 1) {
            layer3.setText(bestFits.get(1));
        } else {
            layer3.setText("");
        }

    }


    private void setup()
    {

        ObjectMapper mapper = new ObjectMapper();

        InputStream dict = getResources().openRawResource(R.raw.dict);

        List<String> words;

        try {
            words = mapper.readValue(dict, List.class);



            for (char c = 'a'; c <= 'z'; c++) {
                words.remove("" + c);
                String startWith = "" + c;
                List<String> currentList = words.stream()
                        .filter(s -> s.startsWith(startWith))
                        .collect(Collectors.toList());
                dictionary.put(c, currentList);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
