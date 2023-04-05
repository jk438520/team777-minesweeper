package com.io.minesweeper.game_engine;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    enum ClickMode {
        BOMB,
        FLAG
    }

    enum GameStatus {
        PLAYING,
        WON,
        LOST
    }
    private final int X;
    private final int Y;

    private int fieldsLeft;
    private final int ALL_MINES;
    private ClickMode clickMode = ClickMode.BOMB;

    private final Field[][] fields;

    private GameStatus gameStatus = GameStatus.PLAYING;

    public GameState lastGameState = null;

    public Game(int width, int height, int allMines) {
        X = width;
        Y = height;
        ALL_MINES = allMines;
        fieldsLeft = width * height - allMines;
        int minesToPlant = allMines;
        fields = new Field[width][height];
        for (int i = 0; i < height && minesToPlant > 0; i=(i+1)%width) {
            for (int j = 0; j < width; j++) {
                int randVal = ThreadLocalRandom.current().nextInt(0, width * height);
                if(randVal < allMines && fields[i][j] == null && minesToPlant > 0) {
                    fields[i][j] = new Mine(i, j);
                    minesToPlant--;
                }
            }
        }
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++)
                if(fields[i][j] == null)
                    fields[i][j] = new EmptyField(i, j);
        connectNeighbors();
    }

    public Game(boolean[][] fields){
        Y = fields.length;
        X = fields[0].length;
        this.fields = new Field[X][Y];
        int tempAllMines = 0;
        for(int i=0; i<Y; i++) {
            for (int j = 0; j < X; j++) {
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

    public int getNumberOnField(int x, int y) {
        int number = 0;
        for(int i=-1; i<=1; i++)
            for(int j=-1; j<=1; j++)
                if(x+i >= 0 && x+i < X && y+j >= 0 && y+j < Y)
                    if(fields[x+i][y+j].isMine())
                        number++;
        return number;
    }


    public Field getField(int i, int j) {
        return fields[i][j];
    }

    private void connectNeighbors(){
        for(int i=0; i<X; i++)
            for(int j=0; j<Y; j++)
                for(int k=-1; k<=1; k++)
                    for(int l=-1; l<=1; l++)
                        if(i+k >= 0 && i+k < X && j+l >= 0 && j+l < Y && !(k==0 && l==0))
                            fields[i][j].addNeighbour(fields[i+k][j+l]);
    }

    public GameState click(int row, int column){
        GameState gs;
        List<FieldToDisplay> ftd;
        switch (clickMode) {
            case BOMB:
                ftd = fields[row][column].reveal();
                break;
            case FLAG:
                ftd = fields[row][column].toggleFlag();
                break;
            default:
                gs = new GameState(gameStatus);
                return gs;
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
                case FLAG:
                    break;
                case UNFLAG:
                    break;
                default:
                    break;
            }
        }
        //if game is over, reveal all fields
        if(gameStatus != GameStatus.PLAYING){
            for(int i=0; i<X; i++)
                for(int j=0; j<Y; j++)
                    if(fields[i][j].getState() != Field.State.FLAGGED)
                        ftd.addAll(fields[i][j].reveal());
        }


        return new GameState(gameStatus, ftd);
    }

    public void setClickMode(ClickMode clickMode) {
        this.clickMode = clickMode;
    }
}
