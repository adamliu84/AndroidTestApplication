package com.example.adam.androidtestapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.adam.androidtestapplication.rotk.RotkCharacter;
import com.example.adam.androidtestapplication.rotk.RotkCharacterAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class XmlListActivity extends AppCompatActivity {


    private static ArrayList<RotkCharacter> updateWithCatAvatar(ArrayList<RotkCharacter> aRotkCharacters, String szCatApi) throws XmlPullParserException, IOException {

        int nCurIndex = 0;

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            //InputStream in_s = getApplicationContext().getAssets().open("raw/rotk.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(szCatApi));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        //If new character, init new character
                        if (tagName.equals("url")) {
                           RotkCharacter curRotkCharacter = aRotkCharacters.get(nCurIndex);
                            curRotkCharacter.set_avatarurl(parser.nextText());
                            nCurIndex++;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aRotkCharacters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_list);
        InputStream inputstream = this.getResources().openRawResource(R.raw.rotk);

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            //InputStream in_s = getApplicationContext().getAssets().open("raw/rotk.xml");
            InputStream in_s = getResources().openRawResource(R.raw.rotk);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<RotkCharacter> aRotkCharacters = new ArrayList<RotkCharacter>();
        RotkCharacter curRotkCharacter = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    //If new character, init new character
                    if (tagName.equals("character")) {
                        curRotkCharacter = new RotkCharacter();
                    }
                    //After that start setting values for new character
                    if (tagName.equals("name")) {
                        curRotkCharacter.set_name(parser.nextText());
                    }
                    if (tagName.equals("atk")) {
                        curRotkCharacter.set_atk(Integer.parseInt(parser.nextText()));
                    }
                    if (tagName.equals("def")) {
                        curRotkCharacter.set_def(Integer.parseInt(parser.nextText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    //Add into character roster
                    if (tagName.equals("character") && curRotkCharacter != null) {
                        aRotkCharacters.add(curRotkCharacter);
                    }
            }
            eventType = parser.next();
        }

        //TODO SUPER DIRTY WAY TO LOAD CAT AVATAR!
        try {
            String szCatApi = new RandomCatAvatarUrlTask().execute("http://thecatapi.com/api/images/get?format=xml&type=jpg&results_per_page="+Integer.toString(aRotkCharacters.size())).get();
            aRotkCharacters = updateWithCatAvatar(aRotkCharacters, szCatApi);
            RotkCharacter[] tempArray = aRotkCharacters.toArray(new RotkCharacter[aRotkCharacters.size()]);
            ListAdapter theAdapter = new RotkCharacterAdapter(this, tempArray);
            ListView listView = (ListView) findViewById(R.id.rotkcharacterlistview);
            listView.setAdapter(theAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class RandomCatAvatarUrlTask extends AsyncTask<String, Void, String> {

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
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
