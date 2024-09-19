package com.example.tajbih;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button addButton, resetButton, restartLapButton, setButton, subButton;
    TextView counterView, lapsCompletedView;
    EditText inputLap;

    int counter = 0, lapGoal = 1, lapsCompleted = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("TajbihPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Restore saved data
        counter = sharedPreferences.getInt("counter", 0);
        lapGoal = sharedPreferences.getInt("lapGoal", 1);
        lapsCompleted = sharedPreferences.getInt("lapsCompleted", 0);

        // Initialize Views
        addButton = findViewById(R.id.addButton);
        resetButton = findViewById(R.id.resetButton);
        counterView = findViewById(R.id.counterView);
        restartLapButton = findViewById(R.id.restartLapButton);
        subButton = findViewById(R.id.subButton);
        setButton = findViewById(R.id.setButton);
        inputLap = findViewById(R.id.inputLap);  // Ensure inputLap matches your XML id
        lapsCompletedView = findViewById(R.id.lapsCompletedView);  // Ensure lapsCompletedView matches your XML id

        // Set views to restored values
        counterView.setText(String.valueOf(counter));
        lapsCompletedView.setText(String.valueOf(lapsCompleted));
        inputLap.setHint("Lap Goal: " + lapGoal);  // Set the hint to the current lap goal

        // Set Button - Set Lap Goal
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setLapGoal();
               hideKeyboard(v);
            }
        });

        inputLap.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    setLapGoal();
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        // Add Button - Increase Counter
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < 9999) {
                    counter++;
                    counterView.setText(String.valueOf(counter));

                    // Check if a lap is completed
                    if (counter % lapGoal == 0) {
                        lapsCompleted++;
                        lapsCompletedView.setText(String.valueOf(lapsCompleted));

                        // Save laps completed
                        editor.putInt("lapsCompleted", lapsCompleted);
                        editor.apply();

                    }

                    // Save updated counter
                    editor.putInt("counter", counter);
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, "Counter has reached its maximum value of 9999.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Subtract Button - Decrease Counter
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    counter--;

                    // Check if we need to adjust laps completed
                    if ((counter + 1) % lapGoal == 0 && lapsCompleted > 0) {
                        lapsCompleted--;
                        lapsCompletedView.setText(String.valueOf(lapsCompleted));

                        // Save laps completed
                        editor.putInt("lapsCompleted", lapsCompleted);
                        editor.apply();
                    }

                    counterView.setText(String.valueOf(counter));

                    // Save updated counter
                    editor.putInt("counter", counter);
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, "Counter is already zero.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Reset Button - Reset Counter
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                counterView.setText(String.valueOf(counter));

                // Save reset values
                editor.putInt("counter", counter);
                editor.putInt("lapsCompleted", lapsCompleted);
                editor.apply();
            }
        });

        // Restart Lap Button - Reset Laps Completed
        restartLapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lapsCompleted = 0;
                lapsCompletedView.setText(String.valueOf(lapsCompleted));

                // Save reset laps completed
                editor.putInt("lapsCompleted", lapsCompleted);
                editor.apply();
            }
        });

    }
    private void setLapGoal() {
        String lapInput = inputLap.getText().toString();
        if (lapInput.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a lap goal.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            lapGoal = Integer.parseInt(lapInput);
            if (lapGoal <= 0) {
                Toast.makeText(MainActivity.this, "Lap goal must be greater than 0.", Toast.LENGTH_SHORT).show();
                lapGoal = 1;  // Reset to default
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Invalid lap goal. Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set lap goal hint
        inputLap.setHint("Set lap: " + lapGoal);

        // Save lap goal
        editor.putInt("lapGoal", lapGoal);
        editor.apply();

        inputLap.setText("");  // Clear the input field
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
