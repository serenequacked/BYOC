package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoosePeriodActivity extends AppCompatActivity {

    private Button btnMove1, btnMove2, btnMove3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_period);

        btnMove1 = findViewById(R.id.choice1);
        btnMove2 = findViewById(R.id.choice2);
        btnMove3 = findViewById(R.id.choice3);

        btnMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRentSuccess();
            }
        });

        btnMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRentSuccess();
            }
        });

        btnMove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRentSuccess();
            }
        });
    }

    private void moveToRentSuccess() {
        Intent i = new Intent(getApplicationContext(), RentSuccess.class);
        startActivity(i);
    }
}
