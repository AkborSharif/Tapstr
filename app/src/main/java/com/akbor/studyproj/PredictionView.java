package com.akbor.studyproj;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

public class PredictionView extends LinearLayout {
    protected Supplier<InputConnection> ic;
    public void setInputConnection(Supplier<InputConnection> getIC) {
        ic = getIC;
    }


    private List<String> words;

    public PredictionView(Context context) {
        super(context);
        setup();
    }

    public PredictionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public PredictionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public PredictionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {

        ObjectMapper mapper = new ObjectMapper();

        InputStream dict = getResources().openRawResource(R.raw.dict);

        try {
            words = mapper.readValue(dict, List.class);
           // System.out.println(words);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
