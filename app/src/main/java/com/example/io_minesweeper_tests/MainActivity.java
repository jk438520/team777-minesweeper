package com.example.io_minesweeper_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // generate 2d 10x8 array of buttons. Force buttons to fit on screen.
        LinearLayout container = findViewById(R.id.llContainer);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
        for (int i = 0; i < 10; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 8; j++) {
                Button btn = new Button(this);
                btn.setLayoutParams(params);
                // set button text to coordinates
                btn.setText(String.format(Locale.ENGLISH, "(%d %d)", i, j));
                // set text size so it doesn't wrap
                btn.setTextSize(10);
                //force button to be a square
                row.addView(btn);

            }
            container.addView(row);
        }

    }
}