import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class InputMethodAction extends LinearLayout {
    public InputMethodAction(Context context) {
        super(context);
    }

    public InputMethodAction(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public InputMethodAction(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputMethodAction(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       if (event.getAction() == MotionEvent.ACTION_DOWN){}

        if (event.getAction() == MotionEvent.ACTION_UP){}



        return true;
    }
}
