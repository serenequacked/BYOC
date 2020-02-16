package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RentalPeriod2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_period2);

        final ImageButton arrowbutton = (ImageButton) findViewById(R.id.rightarrow);
        final Button daybutton = (Button) findViewById(R.id.day);
        final Button weekbutton = (Button) findViewById(R.id.week);
        final Button monthbutton = (Button) findViewById(R.id.month);
        final Drawable d = getResources().getDrawable(R.drawable.orangerectangle);
        daybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daybutton.setBackgroundDrawable(d);
            }
        });
        weekbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekbutton.setBackgroundDrawable(d);
            }
        });
        monthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthbutton.setBackgroundDrawable(d);
            }
        });


        arrowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });





    }

    public void openActivity3()
    {
        Intent intent = new Intent(this,RentSuccess.class);
        startActivity(intent);
    }

}
