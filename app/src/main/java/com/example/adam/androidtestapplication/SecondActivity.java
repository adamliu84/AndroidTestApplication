package com.example.adam.androidtestapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String value = intent.getStringExtra("toSecondIntent").toString(); //if it's a string you stored.

        ((TextView)findViewById(R.id.txtPassedValue)).setText(value);
    }
}
