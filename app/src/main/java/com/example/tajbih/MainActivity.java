package com.example.tajbih;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button clickButton, resetButton, newLapButton, newDollerButton;
    TextView counterView, lapView, dollerView;

    int counter = 0, lap = 0, doller = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("TajbihPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Restore saved data
        counter = sharedPreferences.getInt("counter", 0);
        lap = sharedPreferences.getInt("lap", 0);
        doller = sharedPreferences.getInt("doller", 0);

        clickButton = findViewById(R.id.clickButton);
        resetButton = findViewById(R.id.resetButton);
        counterView = findViewById(R.id.counterView);
        newLapButton = findViewById(R.id.newLapButton);
        lapView = findViewById(R.id.lapView);
        dollerView = findViewById(R.id.dollerView);
        newDollerButton = findViewById(R.id.newDollerButton);

        // Set views to restored values
        counterView.setText(String.valueOf(counter));
        lapView.setText(String.valueOf(lap));
        dollerView.setText(String.valueOf(doller));

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counterView.setText(String.valueOf(counter));

                if (counter % 33 == 0) {
                    lap++;
                    lapView.setText(String.valueOf(lap));
                }

                if (counter % 38 == 0) {
                    doller++;
                    dollerView.setText(String.valueOf(doller));
                }

                // Save updated values
                editor.putInt("counter", counter);
                editor.putInt("lap", lap);
                editor.putInt("doller", doller);
                editor.apply();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                counterView.setText(String.valueOf(counter));

                // Save reset value
                editor.putInt("counter", counter);
                editor.apply();
            }
        });

        newLapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lap = 0;
                lapView.setText(String.valueOf(lap));

                // Save reset value
                editor.putInt("lap", lap);
                editor.apply();
            }
        });

        newDollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doller = 0;
                dollerView.setText(String.valueOf(doller));

                // Save reset value
                editor.putInt("doller", doller);
                editor.apply();
            }
        });
    }
}
