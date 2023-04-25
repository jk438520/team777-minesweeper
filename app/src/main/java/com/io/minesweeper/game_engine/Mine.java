package com.io.minesweeper.game_engine;

import java.util.ArrayList;
import java.util.List;

public class Mine extends Field {
    public Mine(int row, int column) {
        super(row, column);
    }

    @Override
    public boolean isMine() {
        return true;
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
        ret.add(new FieldToDisplay(row, column, FieldToDisplay.FieldEvent.REVEAL_MINE));
        return ret;
    }


}
