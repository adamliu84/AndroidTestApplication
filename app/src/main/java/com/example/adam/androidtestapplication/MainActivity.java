package com.example.adam.androidtestapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSecondActivity(View view){
        //Get textbox value
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("toSecondIntent", getTextInput());
        this.startActivity(intent);
    }

    private String getTextInput(){
        String szPassValue = ((TextView)findViewById(R.id.txtInput)).getText().toString();
        if(szPassValue.equals("")){
            szPassValue="DEFAULT";
        }
        return szPassValue;
    }
}
