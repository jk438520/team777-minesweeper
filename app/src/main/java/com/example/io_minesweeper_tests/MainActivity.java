package com.example.io_minesweeper_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int XDIM = 8;
    private final int YDIM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BackEndSim backEndSim = new BackEndSim(XDIM, YDIM);

        // generate 2d 10x8 array of buttons. Force buttons to fit on screen.
        LinearLayout container = findViewById(R.id.llContainer);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);

        for (int i = 0; i < YDIM; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < XDIM; j++) {
                MyButton btn = new MyButton(this, j, i);
                btn.setLayoutParams(params);
                // set button text to coordinates
                btn.setText(String.format(Locale.ENGLISH, "(%d %d)", j, i));
                // set text size so it doesn't wrap
                btn.setTextSize(10);
                btn.setBackgroundColor(backEndSim.getMainColor(j, i));
                btn.setOnClickListener(v -> btn.onClick(backEndSim));

                row.addView(btn);
            }
            container.addView(row);
        }
    }
}