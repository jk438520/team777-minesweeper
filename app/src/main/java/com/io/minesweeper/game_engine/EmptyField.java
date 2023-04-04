package com.io.minesweeper.game_engine;

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
        switch (super.state) {
            case HIDDEN:
                super.state = State.REVEALED;
                break;
            case REVEALED:
            case FLAGGED:
            default:
                return new ArrayList<>();
        }
        List<FieldToDisplay> ret = new ArrayList<>();
        int mines = 0;
        for (Field neighbour : neighbours) {
            if (neighbour.isMine()) {
                mines++;
            }
        }
        if (mines > 0) {
            ret.add(new FieldToDisplay(row, column, mines, FieldToDisplay.FieldEvent.REVEAL_NUMBER));
        } else {
            ret.add(new FieldToDisplay(row, column, FieldToDisplay.FieldEvent.REVEAL_NUMBER));
            for (Field neighbour : neighbours) {
                if (neighbour.getState() == State.HIDDEN) {
                    ret.addAll(neighbour.reveal());
                }
            }
        }
        return ret;
    }

}
