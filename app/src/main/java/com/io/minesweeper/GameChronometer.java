package com.io.minesweeper;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.widget.Chronometer;

public class GameChronometer {
    Chronometer chronometer;
    private boolean isRunning;
    private long timeWhenStarted;
    private long timeInMillis;

    public GameChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            // This part is only for the "ticking colon" effect
            private boolean colonVisible = true;
            @SuppressLint("SetTextI18n")
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                long m = (time / 1000) / 60;
                long s = (time / 1000) % 60;
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                if (colonVisible){
                    cArg.setText(mm + ":" + ss);
                }
                else {
                    cArg.setText(mm + " " + ss);
                }
                colonVisible = !colonVisible;
            }
        });
    }

    public void tileClick() {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            timeWhenStarted = SystemClock.elapsedRealtime();

            isRunning = true;
        }
    }

    public void stop() {
        chronometer.stop();
        timeInMillis = SystemClock.elapsedRealtime() - timeWhenStarted;

        isRunning = false;
    }

    public void restartClick() {
        if (isRunning) {
            stop();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }


    public long getTimeInMillis() {
        return timeInMillis;
    }
}
