package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class RankingActivity extends AppCompatActivity {

    TableLayout tableLayout;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        tableLayout = findViewById(R.id.layout_ranking);
        fillRankingTable(DataBaseHelper.COLUMN_USER_HARD_SCORE, 1);

        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.b1) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_HARD_SCORE, 1);
                }
                if (checkedId == R.id.b2) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_MEDIUM_SCORE, 2);
                }
                if (checkedId == R.id.b3) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_EASY_SCORE, 3);
                }
            }
        });
    }

    String scoreToString(long timeInMilliseconds) {
        if (timeInMilliseconds == UserModel.MILLISECONDS_IN_2_HOURS) {
            return "-";
        }
        long m = (timeInMilliseconds / 1000) / 60;
        long s = (timeInMilliseconds / 1000) % 60;
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";

        return mm + ":" + ss;
    }

    void fillRankingTable(String category, int highlight) {
        tableLayout.removeAllViews();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(RankingActivity.this);
        List<UserModel> usersSorted = dataBaseHelper.getEveryoneSortedByTime(category);

        TableRow row = new TableRow(this);
        TextView[] columns = new TextView[4];
        for (int i = 0; i < 4; ++i) {
            columns[i] = new TextView(this);
        }
        columns[0].setText("Name");
        columns[1].setText("Hard");
        columns[2].setText("Medium");
        columns[3].setText("Easy");

        for (int i = 0; i < 4; ++i) {
            columns[i].setWidth(250);
            columns[i].setHeight(100);
            columns[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            columns[i].setBackgroundColor(Color.GRAY);
            columns[i].setGravity(Gravity.CENTER | Gravity.CENTER);
            columns[i].setTypeface(null, Typeface.BOLD);
            columns[i].setTextSize(14);

            if (i == highlight) {
                columns[i].setBackgroundColor(Color.MAGENTA);
            }
            row.addView(columns[i]);
        }
        tableLayout.addView(row);

        for (UserModel user: usersSorted) {
            row = new TableRow(this);
            for (int i = 0; i < 4; ++i) {
                columns[i] = new TextView(this);
            }
            Log.d("Results: ", user.getName() + user.getHardScore() + user.getMediumScore() + user.getEasyScore());
            columns[0].setText(user.getName());
            columns[1].setText(scoreToString(user.getHardScore()));
            columns[2].setText(scoreToString(user.getMediumScore()));
            columns[3].setText(scoreToString(user.getEasyScore()));
            for (int i = 0; i < 4; ++i) {
                columns[i].setWidth(250);
                columns[i].setHeight(100);
                columns[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                columns[i].setBackgroundColor(Color.LTGRAY);
                columns[i].setGravity(Gravity.CENTER | Gravity.CENTER);
                columns[i].setTypeface(null, Typeface.BOLD);
                columns[i].setTextSize(14);
                row.addView(columns[i]);
            }
            tableLayout.addView(row);
        }
    }
}