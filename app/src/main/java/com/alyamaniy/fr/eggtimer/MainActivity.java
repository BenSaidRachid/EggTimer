package com.alyamaniy.fr.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean changButtonState = true;
    SeekBar seekTimer;
    TextView countDown;
    CountDownTimer countDownTimer;
    Button start;
    int seconds = 0;
    int minutes = 0;

    public void resetTimer() {
        changButtonState = true;
        seekTimer.setEnabled(true);
        seekTimer.setProgress(30);
        countDown.setText("00:30");
        start.setText("GO!");
        countDownTimer.cancel();
    }

    public void start(View view) {
        start = (Button) view;

        if(changButtonState) {
            changButtonState = false;
            start.setText("STOP!");
            seekTimer.setEnabled(false);
            countDownTimer = new CountDownTimer(seekTimer.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer horn  = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    horn.start();
                    resetTimer();
                }
            }.start();

        } else {
            resetTimer();
        }

    }

    public void updateTimer(int progress)  {
        minutes = progress / 60;
        seconds = progress - (minutes * 60);
        String stringMinutes = (minutes < 10) ? "0" + minutes   : Integer.toString(minutes);
        String stringSeconds  = (seconds < 10) ? "0" + seconds  : Integer.toString(seconds);
        countDown.setText(stringMinutes + ":" +stringSeconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekTimer = (SeekBar) findViewById(R.id.seekTimer);
        countDown = (TextView) findViewById(R.id.countDown);
        seekTimer.setMax(600);
        seekTimer.setProgress(30);
        seekTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
