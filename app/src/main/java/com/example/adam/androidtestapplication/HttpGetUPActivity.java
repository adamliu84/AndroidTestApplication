/**
 * Reference video: https://www.youtube.com/watch?v=dVwR5Gpw1_E
 * */

package com.example.adam.androidtestapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class HttpGetUPActivity extends AppCompatActivity {

    private String[] texts = {"Num1","Num2", "Num3", "Num4", "Num5", "Num6", "Num7", "Num8", "Num9", "Num10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get_up);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadSecurityXmlTask().execute();
            }
        });
    }


    private class DownloadSecurityXmlTask extends AsyncTask<Void, String, Void> {

        private int nCurDownload = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            for (String curText : texts) {
                publishProgress(curText);
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    Log.e("ThreadSleepError", e.getMessage());
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("onProgressUpdate", values[0]);
            Log.d("onProgressUpdateCount", Integer.toString(++nCurDownload));

        }
    }
}
