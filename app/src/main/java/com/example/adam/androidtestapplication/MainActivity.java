package com.example.adam.androidtestapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSecondActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("toSecondIntent", getTextInput());
        this.startActivity(intent);
    }

    Toast _previousToast = null;
    public void onToast(View view){
        //Cancel previous toast if valid
        if(null!= this._previousToast ){
            this._previousToast.cancel();
        }
        Context context = getApplicationContext();
        CharSequence text = getTextInput();
        int duration = Toast.LENGTH_SHORT;

        this._previousToast = Toast.makeText(context, text, duration);
        this._previousToast.show();
    }

    public void onXmlListActivity(View view){
        Intent intent = new Intent(this, XmlListActivity.class);
        this.startActivity(intent);
    }

    public void onLearnToPlayActivity(View view){
        Intent intent = new Intent(this, LearnToPlayActivity.class);
        this.startActivity(intent);
    }

    public void onSQLiteActivity(View view){
        Intent intent = new Intent(this, SQLiteActivity.class);
        this.startActivity(intent);
    }

    public void onRFIDActivity(View view){
        Intent intent = new Intent(this, RFIDActivity.class);
        this.startActivity(intent);
    }

    public void onHttpGetUPActivity(View view){
        Intent intent = new Intent(this, HttpGetUPActivity.class);
        this.startActivity(intent);
    }

    public void onTvGuideActivity(View view){
        Intent intent = new Intent(this, TvGuideActivity.class);
        this.startActivity(intent);
    }

    public void onGPSActivity(View view){
        Intent intent = new Intent(this, LocationActivity.class);
        this.startActivity(intent);
    }

    public void onCameraLiveActivity(View view){
        Intent intent = new Intent(this, CamerLiveActivity.class);
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
