package com.io.minesweeper.game_engine;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    public enum ClickMode {
        BOMB,
        FLAG
    }

    public enum GameStatus {
        PLAYING,
        WON,
        LOST
    }
    private final int HEIGHT;
    private final int WIDTH;

    private boolean firstClick = true;
    private int fieldsLeft;
    private final int ALL_MINES;
    private ClickMode clickMode = ClickMode.BOMB;

    private final Field[][] fields;

    private GameStatus gameStatus = GameStatus.PLAYING;


    public Game(int width, int height, int allMines) {
        WIDTH = width;
        HEIGHT = height;
        ALL_MINES = allMines;
        fieldsLeft = width * height - allMines;
        fields = new Field[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = new EmptyField(i, j);
            }
        }
    }

    public Game(boolean[][] fields){
        firstClick = false;
        HEIGHT = fields.length;
        WIDTH = fields[0].length;
        this.fields = new Field[HEIGHT][WIDTH];
        int tempAllMines = 0;
        for(int i=0; i<HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (fields[i][j]) {
                    this.fields[i][j] = new Mine(i, j);
                    tempAllMines++;
                } else{
                    this.fields[i][j] = new EmptyField(i, j);
                    fieldsLeft++;
                }
            }
        }
        ALL_MINES = tempAllMines;
        connectNeighbors();
    }


    public Field getField(int i, int j) {
        return fields[i][j];
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void connectNeighbors(){
        for(int i=0; i<HEIGHT; i++)
            for(int j=0; j<WIDTH; j++)
                for(int k=-1; k<=1; k++)
                    for(int l=-1; l<=1; l++)
                        if(i+k >= 0 && i+k < HEIGHT && j+l >= 0 && j+l < WIDTH && !(k==0 && l==0))
                            fields[i][j].addNeighbour(fields[i+k][j+l]);
    }

    private void generateMines(int row, int column) {
        int fieldsToAssess = HEIGHT * WIDTH - 1;
        int minesToPlant = ALL_MINES;
        for(int i=0; i<HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++) {
                if (i == row && j == column)
                    continue;
                boolean isFlagged = fields[i][j].getState() == Field.State.FLAGGED;
                if (ThreadLocalRandom.current().nextInt(0, fieldsToAssess) < minesToPlant) {
                    fields[i][j] = new Mine(i, j);
                    minesToPlant--;
                } else {
                    fields[i][j] = new EmptyField(i, j);
                }
                if(isFlagged)
                    fields[i][j].toggleFlag();
                fieldsToAssess--;
            }
        }
        connectNeighbors();
    }
    public GameState click(int row, int column){
        List<FieldToDisplay> ftd = new ArrayList<>();
        switch (clickMode) {
            case BOMB:
                if(firstClick && fields[row][column].state == Field.State.HIDDEN){
                    generateMines(row, column);
                    firstClick = false;
                }
                ftd = fields[row][column].reveal();
                break;
            case FLAG:
                if(fields[row][column].getState() == Field.State.REVEALED)
                    ftd = fields[row][column].reveal();
                else
                    ftd = fields[row][column].toggleFlag();
                break;
        }
        for(FieldToDisplay f : ftd){
            switch(f.event){
                case REVEAL_NUMBER:
                    fieldsLeft--;
                    if(fieldsLeft == 0)
                        gameStatus = GameStatus.WON;
                    break;
                case REVEAL_MINE:
                    gameStatus = GameStatus.LOST;
                    break;
                default:
                    break;
            }
        }
        //if game is over, reveal all fields
        if(gameStatus != GameStatus.PLAYING){
            for(int i=0; i<HEIGHT; i++)
                for(int j=0; j<WIDTH; j++)
                    if(fields[i][j].getState() != Field.State.FLAGGED)
                        ftd.addAll(fields[i][j].reveal());
        }
        return new GameState(gameStatus, ftd);
    }

    public void setClickMode(ClickMode clickMode) {
        this.clickMode = clickMode;
    }
    public ClickMode getClickMode() { return this.clickMode; }


}
