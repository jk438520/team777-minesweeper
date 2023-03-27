package com.example.io_minesweeper_tests;

import android.app.ActionBar;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Locale;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {

    private int x;
    private int y;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyButton(Context context, int x, int y){
        super(context);
        this.x = x;
        this.y = y;

    }

    public void onClick(BackEndSim backEndSim) {
        setBackgroundColor(backEndSim.getNextColor(x, y));
    }

}
