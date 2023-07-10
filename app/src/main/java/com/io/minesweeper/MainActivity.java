package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.io.minesweeper.game_engine.FieldToDisplay;
import com.io.minesweeper.game_engine.Game;
import com.io.minesweeper.game_engine.GameState;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DisplayMetrics displayMetrics;
    int bombSaturation = 30;
    ImageButton setFlag;
    ImageButton restart;
    int numberOfColumns = 15;
    int numberOfRows = numberOfColumns * 16 / 9;
    int numberOfMines = numberOfColumns * numberOfRows * bombSaturation / 100;
    int user_id = 0;
    String user_name = "None";
    String level = "None";
    Game currentGame;
    GameState currentGameState;

    GameChronometer chronometer;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = new DisplayMetrics();

        Intent intent = getIntent();
        numberOfColumns = intent.getIntExtra("columns", 10);
        numberOfRows = intent.getIntExtra("rows", 14);
        numberOfMines = intent.getIntExtra("bombSaturation", 20)
                        * numberOfColumns * numberOfRows / 100;
        user_id = intent.getIntExtra("user_id", 0);
        user_name = intent.getStringExtra("user_name");
        level = intent.getStringExtra("level");

        Toast.makeText(this, "User_id: " + Integer.toString(user_id) + ", user_name: " + user_name, Toast.LENGTH_SHORT).show();

        currentGame = new Game(numberOfColumns, numberOfRows, numberOfMines);
        currentGameState = new GameState(currentGame.getGameStatus());

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int paddingDp = 5;

        LinearLayout boardView = findViewById(R.id.boardView);
        setFlag = findViewById(R.id.setFlag);
        restart = findViewById(R.id.restart);
        chronometer = new GameChronometer(findViewById(R.id.chronometer));

        setFlag.setOnClickListener(v -> {
            Game.ClickMode currentMode = currentGame.getClickMode();
            if (currentMode == Game.ClickMode.BOMB) {
                currentGame.setClickMode(Game.ClickMode.FLAG);
                ((ImageButton)v).setImageResource(R.drawable.flag);
            }
            else { // currentMode == Game.ClickMode.FLAG
                currentGame.setClickMode(Game.ClickMode.BOMB);
                ((ImageButton)v).setImageResource(R.drawable.bomb);
            }
        });

        restart.setOnClickListener(v -> {
            ImageButton imageButton;
            currentGame = new Game(numberOfColumns, numberOfRows, numberOfMines);
            currentGameState = new GameState(currentGame.getGameStatus());
            int max = numberOfRows * numberOfColumns;
            for (int i = 0; i < max; ++i) {
                imageButton = findViewById(i);
                imageButton.setImageResource(R.drawable.blank);
            }
            chronometer.restartClick();
        });

        float scale = getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * scale + 0.5f);
        int btnWeight  = (int) ((screenWidth - 2 * paddingPixel) / numberOfColumns);
        int btnHeight = (int) ((screenWidth - 2 * paddingPixel) / numberOfColumns);

        for (int row = 0; row < numberOfRows; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            for (int column = 0; column < numberOfColumns; column++) {
                ImageButton button = new ImageButton(this, null, 0, R.style.SquareStyle);
                //button.setId(row * numberOfRows + column);
                button.setId(row * numberOfColumns + column);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        btnWeight,
                        btnHeight
                ));

                button.setOnClickListener(this);
                button.setOnLongClickListener(v -> {
                    onLongClickHelper(v);
                    return true;
                });
                rowLayout.addView(button);

                Log.d("MainActivity", "Button " + row + " " + column + " created with id " + button.getId());
            }

            boardView.addView(rowLayout);
        }
    }

    private void applyGameStateChanges(GameState gameState) {
        int helperId;
        ImageButton changedButton;
        for (FieldToDisplay field : gameState.fieldEvents) {
            helperId = field.row * numberOfColumns + field.col;
            changedButton = findViewById(helperId);
            switch (field.event) {
                case FLAG:
                    changedButton.setImageResource(R.drawable.flag);
                    break;
                case UNFLAG:
                    changedButton.setImageResource(R.drawable.blank);
                    break;
                case REVEAL_MINE:
                    changedButton.setImageResource(R.drawable.bomb);
                    break;
                default: // case REVEAL_NUMBER
                    String valueToString = Integer.toString(field.value);
                    String choice = "minesweeper_" + valueToString;
                    int drawingId = getResources().getIdentifier(choice, "drawable", getPackageName());
                    changedButton.setImageResource(drawingId);
            }
        }
        if (gameState.gameStatus != Game.GameStatus.PLAYING) {
            chronometer.stop();
            String mess;
            if (gameState.gameStatus == Game.GameStatus.WON) {
                Log.d("Time: ", Long.toString(chronometer.getTimeInMillis()));
                Log.d("Level: ", level);
                if (user_id != 0 && !Objects.equals(level, "None") && level != null) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                    dataBaseHelper.updateTime(user_id, chronometer.getTimeInMillis(), level);
                }
                mess = "You won! Congratulations!";
            } else {
                Log.d("Time: ", Long.toString(chronometer.getTimeInMillis()));
                Log.d("Level: ", level);
                mess = "You lost. Let's try again!";
            }
            Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        chronometer.tileClick();
        int id = view.getId();

        Log.d("Id", "Id: " + id);
        currentGameState = currentGame.click(id / numberOfColumns, id % numberOfColumns);
        Log.d("Row, col", "Row, col; " + id / numberOfColumns + " " + id % numberOfColumns);

        applyGameStateChanges(currentGameState);
    }

    public void onLongClickHelper(View view) {
        Log.d("", "Long click");
        int id = view.getId();
        Game.ClickMode previousClickMode = currentGame.getClickMode();

        currentGame.setClickMode(Game.ClickMode.FLAG);
        currentGameState = currentGame.click(id / numberOfColumns, id % numberOfColumns);
        currentGame.setClickMode(previousClickMode);

        applyGameStateChanges(currentGameState);
    }
}