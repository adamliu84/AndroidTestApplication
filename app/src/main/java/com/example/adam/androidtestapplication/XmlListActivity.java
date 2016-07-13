package com.example.adam.androidtestapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adam.androidtestapplication.rotk.RotkCharacter;
import com.example.adam.androidtestapplication.rotk.RotkCharacterAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XmlListActivity extends AppCompatActivity {


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
            // TODO Auto-generated catch block
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
        //Add temp textView for each character
//        View linearLayout = findViewById(R.id.activityxmllistmain);
//        for (RotkCharacter curChar : aRotkCharacters) {
//            TextView valueTV = new TextView(this);
//            valueTV.setText(curChar.toString());
//            valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            ((LinearLayout) linearLayout).addView(valueTV);
//        }

        RotkCharacter[] tempArray = aRotkCharacters.toArray(new RotkCharacter[aRotkCharacters.size()]);
        ListAdapter theAdapter = new RotkCharacterAdapter(this, tempArray);
        ListView listView = (ListView) findViewById(R.id.rotkcharacterlistview);
        listView.setAdapter(theAdapter);

    }
}
