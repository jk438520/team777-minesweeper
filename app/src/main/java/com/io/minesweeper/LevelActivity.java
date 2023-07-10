package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    int id_to_pass = 0;
    String name_to_pass = "None";
    String level_to_pass = "None";

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

        Intent intent = getIntent();
        id_to_pass = intent.getIntExtra("user_id", 0);
        name_to_pass = intent.getStringExtra("user_name");
        if (name_to_pass == null) {
            name_to_pass = "None";
        }
        level_to_pass = "None";
    }

    @Override
    public void onClick(View view) {

        Intent i;
        if (view.getId() == R.id.custom) {
            i = new Intent(this, CustomActivity.class);
            i.putExtra("user_name", name_to_pass);
            i.putExtra("user_id", id_to_pass);
            i.putExtra("level", level_to_pass);
            startActivity(i);
        }
        else {
            i = new Intent(this, MainActivity.class);
            if (view.getId() == R.id.easy) {
                level_to_pass = DataBaseHelper.COLUMN_USER_EASY_SCORE;
                i.putExtra("user_name", name_to_pass);
                i.putExtra("user_id", id_to_pass);
                i.putExtra("columns", 6);
                i.putExtra("rows", 9);
                i.putExtra("bombSaturation", 15);
            }

            if (view.getId() == R.id.medium) {
                level_to_pass = DataBaseHelper.COLUMN_USER_MEDIUM_SCORE;
                i.putExtra("user_name", name_to_pass);
                i.putExtra("user_id", id_to_pass);
                i.putExtra("columns", 10);
                i.putExtra("rows", 14);
                i.putExtra("bombSaturation", 20);
            }

            if (view.getId() == R.id.hard) {
                level_to_pass = DataBaseHelper.COLUMN_USER_HARD_SCORE;
                i.putExtra("user_name", name_to_pass);
                i.putExtra("user_id", id_to_pass);
                i.putExtra("columns", 15);
                i.putExtra("rows", 22);
                i.putExtra("bombSaturation", 30);
            }
            i.putExtra("level", level_to_pass);
            startActivity(i);
        }
    }
}