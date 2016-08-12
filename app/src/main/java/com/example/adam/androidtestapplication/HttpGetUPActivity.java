/**
 * Reference video: https://www.youtube.com/watch?v=dVwR5Gpw1_E
 */

package com.example.adam.androidtestapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class HttpGetUPActivity extends AppCompatActivity {

    private String[] texts = {"Num1", "Num2", "Num3", "Num4", "Num5", "Num6", "Num7", "Num8", "Num9", "Num10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get_up);

        Button btnDownXml = (Button) findViewById(R.id.btnDownXml);
        btnDownXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadSecurityXmlTask().execute();
            }
        });

    }


    private class DownloadSecurityXmlTask extends AsyncTask<Void, Void, Void> {

        private int nCurDownload = 0;
        private String m_url = LearnToPlayAPI.httpGetUP_url;
        private String m_username = LearnToPlayAPI.httpGetUP_username;
        private String m_password = LearnToPlayAPI.httpGetUP_password;

        @Override
        protected Void doInBackground(Void... voids) {
            String responseString = null;

            try {
                URL url = new URL(m_url);
                //https://examples.javacodegeeks.com/core-java/net/authenticator/access-password-protected-url-with-authenticator/
                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(m_username, m_password.toCharArray());
                    }
                });
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                //and connect!
                urlConnection.connect();
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader br = null;
                StringBuilder sb = new StringBuilder();
                br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                responseString = sb.toString();
            } catch (Exception e) {
                responseString = "";
                Log.e("FailXmlDownload", e.getMessage());
            }
            Log.d("SuccessfulXmlDownload", responseString);

            return null;
        }
    }

    private final String FOLDER_NAME = "AndroidTestApplication";
    private final String FILE_NAME = "Text.xml";

    private void saveFile() {
        try {
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FOLDER_NAME;
            File directory = new File(file_path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File gpxfile = new File(file_path, FILE_NAME);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append("<?xml version='1.0' encoding='UTF-8'?><note>\n" +
                    "  <to>Tove</to>\n" +
                    "  <from>Jani</from>\n" +
                    "  <heading>Reminder</heading>\n" +
                    "  <body>Don't forget me this weekend!</body>\n" +
                    "</note>");
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("saveFileError", e.getMessage());
        }
    }

}
