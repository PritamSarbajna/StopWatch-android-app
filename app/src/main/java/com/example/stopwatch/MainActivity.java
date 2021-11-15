package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Use seconds, running and notRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean notRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            notRunning = savedInstanceState.getBoolean("notRunning");
        }
        runTimer();

        Button start = findViewById(R.id.start);
        start.setOnClickListener(v -> running = true);

        Button stop = findViewById(R.id.stop);
        stop.setOnClickListener(v -> running = false);

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(v -> {
            running = false;
            seconds = 0;
        });
    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(
            @NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("notRunning", notRunning);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause() {
        super.onPause();
        notRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume() {
        super.onResume();
        if (notRunning) {
            running = true;
        }
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer() {

        // Get the text view
        TextView timeV = findViewById(R.id.time);

        // Creates a new handler
        final Handler handler = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time = String.
                        format(Locale.getDefault(),
                                "%d:%02d:%02d", hours, minutes, secs);

                // Set the text view text.
                timeV.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}