package com.example.io_minesweeper_tests;

import android.graphics.Color;

import java.util.ArrayList;

public class BackEndSim {

    ArrayList<ArrayList<Unit>> fields;

    public BackEndSim(int xdim, int ydim) {
        fields = new ArrayList<>();
        for (int i = 0; i < ydim; i++) {
            ArrayList<Unit> row = new ArrayList<>();
            for (int j = 0; j < xdim; j++) {
                row.add(new Unit());
            }
            fields.add(row);
        }
    }

    public int getNextColor(int x, int y) {
        return fields.get(y).get(x).getNextColor();
    }

    public int getMainColor(int x, int y) {
        return fields.get(y).get(x).getMainColor();
    }

    private class Unit {

        private int mainColor = Color.WHITE;
        private int secondaryColor;

        public Unit() {
            // set secondary color to green or red based on random number
            secondaryColor = Math.random() < 0.5 ? Color.GREEN : Color.RED;
        }

        public int getMainColor() {
            return mainColor;
        }

        public int getNextColor() {
            // return secondary color and set it to main color
            int temp = secondaryColor;
            secondaryColor = mainColor;
            mainColor = temp;
            return temp;
        }
    }
}

