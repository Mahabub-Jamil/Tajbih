package com.example.tajbih;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button addButton, resetButton, newLapButton, newDollerButton,lapButton,dollerButton,subButton;
    TextView counterView, lapView, dollerView;
    LinearLayout lapLayout, dollerLayout;

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

        addButton = findViewById(R.id.addButton);
        resetButton = findViewById(R.id.resetButton);
        counterView = findViewById(R.id.counterView);
        newLapButton = findViewById(R.id.newLapButton);
        lapView = findViewById(R.id.lapView);
        dollerView = findViewById(R.id.dollerView);
        newDollerButton = findViewById(R.id.newDollerButton);
        lapButton = findViewById(R.id.lapButton);
        dollerButton = findViewById(R.id.dollerButton);
        lapLayout = findViewById(R.id.lapLayout);
        dollerLayout = findViewById(R.id.dollLayout);
        subButton = findViewById(R.id.subButton);

        // Set views to restored values
        counterView.setText(String.valueOf(counter));
        lapView.setText(String.valueOf(lap));
        dollerView.setText(String.valueOf(doller));

        addButton.setOnClickListener(new View.OnClickListener() {
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

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter!=0) {
                    counter--;
                    if(counter%38==37&&doller>0)
                    {
                        doller--;
                        dollerView.setText(String.valueOf(doller));
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Counter is already zero",Toast.LENGTH_SHORT).show();
                }
                counterView.setText(String.valueOf(counter));

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

        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lapLayout.setVisibility(View.VISIBLE);
                dollerLayout.setVisibility(View.GONE);
            }
        });

        dollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lapLayout.setVisibility(View.GONE);
                dollerLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
