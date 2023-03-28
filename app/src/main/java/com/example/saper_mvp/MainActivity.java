package com.example.saper_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TableLayout mainTL;
    TableRow tr;
    int rows = 3;
    int columns = 2;
    int bombs = 2;
    Button b_restart;
    Button b_setFlag;
    TextView tv_feedback;
    List<Button> buttons = new ArrayList<>;
    Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTL = (TableLayout)findViewById(R.id.mainTableLayout);
        b_restart = (Button)findViewById(R.id.restart);
        b_setFlag = (Button)findViewById(R.id.setFlag);
        tv_feedback = (TextView)findViewById(R.id.feedback);
        currentGame = new Game(rows, columns, bombs);

        tv_feedback.setText("Keep going! :)");
        b_setFlag.setText("Flag inactive");

        for (int i = 0; i < rows * columns; ++i) {
            if (i % columns == 0) {
                tr = new TableRow(this);
                mainTL.addView(tr);
            }
            Button btn = new Button(this);
            btn.setText("");
            btn.setId(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    int result = currentGame.handleClick(id);
                    if (result == -3) {
                        ((Button)v).setText("");
                    }
                    else if (result == -2) {
                        ((Button)v).setText("F");
                    }
                    else if (result == -1) {
                        ((Button)v).setText("B");
                        gameEnds(false);
                    }
                    else {
                        ((Button)v).setText(Integer.toString(result));
                        ((Button)v).setEnabled(false);
                        if (currentGame.isWin()) {
                            gameEnds(true);
                        }
                    }
                }
            });
            buttons.add(btn);
            tr.addView(btn);
        }
    }

    private void restart(View v) {
        currentGame = new Game(rows, columns, bombs);
        for (Button b : buttons) {
            b.setEnabled(true);
            b.setText("");
        }
        b_setFlag.setText("Flag inactive");
        b_setFlag.setEnabled(true);
        tv_feedback.setText("Keep going! :)");
    }

    private void setFlag(View v) {
        if (currentGame.isFlagSet()) {
            ((Button)v).setText("Flag inactive");
        }
        else {
            ((Button)v).setText("Flag active");
        }
        currentGame.changeFlag();
    }

    private void gameEnds(boolean win) {
        for (Button b : buttons) {
            b.setEnabled(false);
            if (!win) {
                if (currentGame.showIfBomb(b.getId())) {
                    b.setText("B");
                }
            }
        }
        b_setFlag.setEnabled(false);
        if (win) {
            tv_feedback.setText("Congratulations! You won! :)");
        }
        else {
            tv_feedback.setText("Maybe next time ;)");
        }
    }
}