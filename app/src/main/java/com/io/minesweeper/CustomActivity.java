package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.io.minesweeper.game_engine.Game;
import com.io.minesweeper.game_engine.GameState;

public class CustomActivity extends AppCompatActivity {

    int colVal = 10;
    int satVal = 20;
    int id_to_pass = 0;
    String name_to_pass = "None";
    String level_to_pass = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Intent intent = getIntent();
        id_to_pass = intent.getIntExtra("user_id", 0);
        name_to_pass = intent.getStringExtra("user_name");
        if (name_to_pass == null) {
            name_to_pass = "None";
        }
        level_to_pass = "None";

        ImageButton colMinus = findViewById(R.id.colMinus);
        ImageButton colPlus = findViewById(R.id.colPlus);
        ImageButton satMinus = findViewById(R.id.satMinus);
        ImageButton satPlus = findViewById(R.id.satPlus);
        Button play = findViewById(R.id.play);

        TextView colValue = findViewById(R.id.colValue);
        TextView satValue = findViewById(R.id.satValue);

        colValue.setText(Integer.toString(colVal));
        satValue.setText(Integer.toString(satVal) + "%");

        colMinus.setOnClickListener(v -> {
            if (colVal >= 4) {
                colVal--;
                colValue.setText(Integer.toString(colVal));
            }
        });

        colPlus.setOnClickListener(v -> {
            if (colVal <= 13) {
                colVal++;
                colValue.setText(Integer.toString(colVal));
            }
        });

        satMinus.setOnClickListener(v -> {
            if (satVal >= 15) {
                satVal-=5;
                satValue.setText(Integer.toString(satVal) + "%");
            }
        });

        satPlus.setOnClickListener(v -> {
            if (satVal <= 55) {
                satVal+=5;
                satValue.setText(Integer.toString(satVal) + "%");
            }
        });

        satPlus.setOnClickListener(v -> {
            if (satVal <= 55) {
                satVal+=5;
                satValue.setText(Integer.toString(satVal) + "%");
            }
        });

        play.setOnClickListener(v -> {
            int rowVal = colVal * 3 / 2;

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("user_name", name_to_pass);
            i.putExtra("user_id", id_to_pass);
            i.putExtra("columns", colVal);
            i.putExtra("rows", rowVal);
            i.putExtra("bombSaturation", satVal);
            i.putExtra("level", level_to_pass);

            startActivity(i);
        });
    }
}