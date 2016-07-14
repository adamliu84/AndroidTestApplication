package com.example.adam.androidtestapplication.rotk;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by adam on 15/7/16.
 */
public class RotkXmlParser {

    public static ArrayList<RotkCharacter> parseRotkCharacter(XmlPullParser parser) throws XmlPullParserException, IOException {
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

        return aRotkCharacters;
    }

    public static ArrayList<RotkCharacter> parseCatAvatar(XmlPullParser parser, ArrayList<RotkCharacter> aRotkCharacters) throws XmlPullParserException, IOException {
        int nCurIndex = 0;

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

        //Return with replacement of random cat avatar
        return aRotkCharacters;
    }

}
