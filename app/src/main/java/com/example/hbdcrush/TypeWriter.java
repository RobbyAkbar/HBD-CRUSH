package com.example.hbdcrush;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Created by robby on 31/07/22.
 */
public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence myText;
    private int myIndex;
    private long myDelay = 150;

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private final Handler myHandler = new Handler();

    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(myText.subSequence(0, myIndex++));
            if (myIndex<=myText.length()){
                myHandler.postDelayed(characterAdder, myDelay);
            }
        }
    };

    public void animateText(CharSequence myTxt){
        myText = myTxt;
        myIndex = 0;

        setText("");

        myHandler.removeCallbacks(characterAdder);

        myHandler.postDelayed(characterAdder, myDelay);
    }

    public void setCharacterDelay(long m){
        myDelay = m;
    }
}
