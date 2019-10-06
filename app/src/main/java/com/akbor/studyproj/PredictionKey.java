//package com.akbor.studyproj;
//
//import android.content.Context;
//import android.support.v7.widget.AppCompatTextView;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.inputmethod.InputConnection;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
//public class PredictionKey extends AppCompatTextView {
//    public Supplier<InputConnection> getIc() {
//        return ic;
//    }
//
//    private List<CharSequence> words;
////
//    CharSequence str1;
//
//    Runnable commitTextAction;
//
//    public void setIc(Supplier<InputConnection> ic) {
//        this.ic = ic;
//    }
//
//    protected Supplier<InputConnection> ic;
//
//    public PredictionKey(Context context) {
//        super(context); //setup();
//    }
//
//    public PredictionKey(Context context, AttributeSet attrs) {
//        super(context, attrs);//setup();
//    }
//
//    public PredictionKey(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr); //setup();
//    }
////
//    private void setup() {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        InputStream dict = getResources().openRawResource(R.raw.dict);
//
//        try {
//            words = mapper.readValue(dict, List.class);
//            System.out.println(words);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        str1 = getText();
//        if (event.getAction() == MotionEvent.ACTION_DOWN){}
//
//        if (event.getAction() == MotionEvent.ACTION_UP){
//            commitTextAction.run();
//            ic.get().commitText(getText() + " ", 1);
//            this.setText("");
//
//        }
//
//
//
//        performClick();
//        return true;
//    }
//
//    public void onCommitTextAction(Runnable commitTextAction) {
//        this.commitTextAction = commitTextAction;
//    }
//
//    @Override
//    public boolean performClick() {
//        super.performClick();
//        return true;
//    }
//}
