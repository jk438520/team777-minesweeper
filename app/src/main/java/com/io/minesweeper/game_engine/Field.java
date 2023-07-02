package com.io.minesweeper.game_engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Field {
    public final int row;
    public final int column;

    enum State {
        HIDDEN,
        REVEALED,
        FLAGGED
    }

    protected List<Field> neighbours;
    protected State state = State.HIDDEN;

    protected Field(int row, int column){
        this.row = row;
        this.column = column;
        neighbours = new ArrayList<>();
    }


    public State getState() {
        return state;
    }

    public void addNeighbour(Field neighbour){
        neighbours.add(neighbour);
    }

    public List<FieldToDisplay>toggleFlag(){
        List<FieldToDisplay> ret = new ArrayList<>();
        switch (state){
            case HIDDEN:
                state = State.FLAGGED;
                ret.add(new FieldToDisplay(row, column, FieldToDisplay.FieldEvent.FLAG));
                break;
            case FLAGGED:
                state = State.HIDDEN;
                ret.add(new FieldToDisplay(row, column, FieldToDisplay.FieldEvent.UNFLAG));
                break;
            case REVEALED:
            default:
                break;
        }
        return ret;
    }

    public abstract boolean isMine();

    public abstract List<FieldToDisplay> reveal();

}

