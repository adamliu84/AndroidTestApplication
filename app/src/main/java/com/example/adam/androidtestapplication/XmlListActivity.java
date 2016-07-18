package com.example.adam.androidtestapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adam.androidtestapplication.rotk.RotkCharacter;
import com.example.adam.androidtestapplication.rotk.RotkCharacterAdapter;
import com.example.adam.androidtestapplication.rotk.RotkXmlParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class XmlListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_list);
        InputStream inputstream = this.getResources().openRawResource(R.raw.rotk);

        XmlPullParserFactory pullParserFactory;
        try {
            //Parsing of ROKT characters listing from xml
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = getResources().openRawResource(R.raw.rotk);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            ArrayList<RotkCharacter> aRotkCharacters = RotkXmlParser.parseRotkCharacter(parser);

            //Parsing of freshly downloaded xml listing for avatar upload
            String szCatApiXmlResponse = new DownloadCatXmlTask().execute("http://thecatapi.com/api/images/get?format=xml&type=jpg&results_per_page=" + Integer.toString(aRotkCharacters.size())).get();
            if(szCatApiXmlResponse.equals("")){
                throw new Exception("Invalid Cat API Response");
            }
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(szCatApiXmlResponse));
            aRotkCharacters = RotkXmlParser.parseCatAvatar(parser, aRotkCharacters);

            //Loading into listAdapter for display
            RotkCharacter[] tempArray = aRotkCharacters.toArray(new RotkCharacter[aRotkCharacters.size()]);
            ListAdapter theAdapter = new RotkCharacterAdapter(this, tempArray);
            ListView listView = (ListView) findViewById(R.id.rotkcharacterlistview);
            listView.setAdapter(theAdapter);

        } catch (XmlPullParserException | IOException | InterruptedException | ExecutionException e) {
            Toast tempToast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
            tempToast.show();
        }catch (Exception e){
            Toast tempToast = Toast.makeText(getApplicationContext(),e.getMessage() , Toast.LENGTH_SHORT);
            tempToast.show();
        }
    }

    private class DownloadCatXmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String urlOfImage = urls[0];
            String responseString = null;
            try {
                URL url = new URL(urlOfImage);
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
            }
            return responseString;
        }
    }
}
