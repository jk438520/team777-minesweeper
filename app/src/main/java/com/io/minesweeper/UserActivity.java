package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void sign_up_activity(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    public void show_ranking_activity(View view) {
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }

    public void play_anonymously_activity(View view) {
        Intent i = new Intent(this, LevelActivity.class);
        i.putExtra("user_id", 0);
        i.putExtra("user_name", "None");
        startActivity(i);
    }
}