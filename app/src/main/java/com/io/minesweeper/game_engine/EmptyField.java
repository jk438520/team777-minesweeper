package com.io.minesweeper.game_engine;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EmptyField extends Field {

    public EmptyField(int row, int column) {
        super(row, column);
    }

    @Override
    public boolean isMine() {
        return false;
    }

    @Override
    public List<FieldToDisplay> reveal() {
        int mines = 0;
        for (Field neighbour : neighbours) {
            if (neighbour.isMine()) {
                mines++;
            }
        }
        switch (super.state) {
            case HIDDEN:
                super.state = State.REVEALED;
                break;
            case REVEALED:
                ArrayList<FieldToDisplay> acc = new ArrayList<>();
                int cnt = 0;
                for (Field neighbour: neighbours){
                    if (neighbour.state == State.FLAGGED) {
                        cnt++;
                    }
                }
                if( cnt >= mines) {
                    for (Field neighbour : neighbours) {
                        if (neighbour.getState() == State.HIDDEN) {
                            acc.addAll(neighbour.reveal());
                        }
                    }
                }
                Log.d("REVEALED", "Click on revealed field, column: "+column+", row: "+row+
                        "\nmines: " + mines + ", cnt: " + cnt);
                return acc;
            case FLAGGED:
            default:
                return new ArrayList<>();
        }
        List<FieldToDisplay> ret = new ArrayList<>();
        if (mines > 0) {
            ret.add(new FieldToDisplay(row, column, mines, FieldToDisplay.FieldEvent.REVEAL_NUMBER));
        } else {
            //ret.add(new FieldToDisplay(row, column, FieldToDisplay.FieldEvent.REVEAL_NUMBER));
            ret.add(new FieldToDisplay(row, column, 0, FieldToDisplay.FieldEvent.REVEAL_NUMBER));
            for (Field neighbour : neighbours) {
                if (neighbour.getState() == State.HIDDEN) {
                    ret.addAll(neighbour.reveal());
                }
            }
        }
        return ret;
    }

}
