package com.example.user.exercisetimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.exercisetimer.Exercises.Exercise;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    private static final String STATE = "State";
    private static final String STAGE = "STAGE";
    private static final String selectedExerciseKey = "selectedExerciseKey";
    private static final String STATE_Remainder = "timeRemaining";
    private static final String STATE_Times = "times";
    Exercise selectedExercise;
    private String stage;

    private Handler handler;
    private long timeRemaining;
    private long holdTime;
    private long restTime;
    private int timesRemaining;

    private String state;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Bundle bundle = this.getIntent().getExtras();
        selectedExercise = (Exercise) bundle.getSerializable("selectedExercise");

        final TextView times = (TextView) this.findViewById(R.id.times);
        final Button startPause = (Button) this.findViewById(R.id.button2);

        final TextView stateView = (TextView) this.findViewById(R.id.state);

        handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(!paused) {
                    timeRemaining = timeRemaining - 1000;
                    updateTimer(timeRemaining);
                    if (timeRemaining <= 0) {
                        if (state.equals("holding")) {
                            state = "resting";
                            stateView.setText("resting");
                            timeRemaining = restTime;
                            updateTimer(timeRemaining);
                        }else{
                            state = "holding";
                            stateView.setText("holding");
                            timeRemaining = holdTime;
                            updateTimer(timeRemaining);
                            timesRemaining -= 1;
                            times.setText(""+timesRemaining);
                            if (timesRemaining <= 0) {
                                startPause.setText("Restart");
                                stateView.setText("");
                                paused=false;
                                return;
                            }
                        }
                    }
                    handler.postDelayed(this, 1000);
                }
            }
        };

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stage = startPause.getText().toString();
                if(stage.equals("Start")){
                    handler.postDelayed(runnable,1000);
                    startPause.setText("Pause");
                    paused=false;
                }else if(stage.equals("Pause")){
                    stage = "Start";
                    startPause.setText("Start");
                    paused=true;
                }else if(stage.equals("Restart")){
                    timesRemaining = Integer.parseInt(selectedExercise.getTimes());
                    times.setText(""+timesRemaining);
                    holdTime = selectedExercise.getHoldTImeInMili();
                    restTime = selectedExercise.getRestTImeInMili();

                    handler.postDelayed(runnable,1000);
                    stage = "Pause";
                    startPause.setText("Pause");
                    paused=false;
                }
                stage = startPause.getText().toString();
            }
        });
        if (savedInstanceState != null){

            stage = savedInstanceState.getString(STAGE, "Start");
            state = savedInstanceState.getString(STATE, "something wrong");
            timeRemaining = savedInstanceState.getLong(STATE_Remainder, 0);
            selectedExercise =(Exercise) savedInstanceState.getSerializable(selectedExerciseKey);
            timesRemaining = savedInstanceState.getInt(STATE_Times, 0);

            holdTime = selectedExercise.getHoldTImeInMili();
            restTime = selectedExercise.getRestTImeInMili();

            startPause.setText(stage);
            times.setText(""+timesRemaining);
            updateTimer(timeRemaining);

            if(stage.equals("Pause")){
                handler.postDelayed(runnable,1000);
            }

        }else {
            holdTime = selectedExercise.getHoldTImeInMili();
            restTime = selectedExercise.getRestTImeInMili();

            state = "holding";
            timeRemaining = selectedExercise.getHoldTImeInMili();
            timesRemaining = Integer.parseInt(selectedExercise.getTimes());
            times.setText(""+timesRemaining);
            updateTimer(timeRemaining);
        }
        TextView name = (TextView) this.findViewById(R.id.name);
        name.setText(selectedExercise.getName());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putString(STAGE, stage);
        outState.putString(STAGE, stage);
        outState.putSerializable(selectedExerciseKey, selectedExercise);
        outState.putLong(STATE_Remainder, timeRemaining);
        outState.putInt(STATE_Times, timesRemaining);
    }

    public void updateTimer(long time){
        TextView hour = (TextView) this.findViewById(R.id.hour);
        TextView min = (TextView) this.findViewById(R.id.minute);
        TextView sec = (TextView) this.findViewById(R.id.second);

        hour.setText(""+TimeUnit.MILLISECONDS.toHours(time));
        min.setText(""+TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1));
        sec.setText(""+TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1));
    }
}