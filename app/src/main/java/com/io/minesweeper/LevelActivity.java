package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Button easy = findViewById(R.id.easy);
        Button medium = findViewById(R.id.medium);
        Button hard = findViewById(R.id.hard);
        Button custom = findViewById(R.id.custom);

        easy.setOnClickListener(this);
        medium.setOnClickListener(this);
        hard.setOnClickListener(this);
        custom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;
        if (view.getId() == R.id.custom) {
            i = new Intent(this, CustomActivity.class);
            startActivity(i);
        }
        else {
            i = new Intent(this, MainActivity.class);
            if (view.getId() == R.id.easy) {
                i.putExtra("columns", 6);
                i.putExtra("rows", 9);
                i.putExtra("bombSaturation", 15);
            }

            if (view.getId() == R.id.medium) {
                i.putExtra("columns", 10);
                i.putExtra("rows", 14);
                i.putExtra("bombSaturation", 20);
            }

            if (view.getId() == R.id.hard) {
                i.putExtra("columns", 15);
                i.putExtra("rows", 22);
                i.putExtra("bombSaturation", 30);
            }
            startActivity(i);
        }
    }
}