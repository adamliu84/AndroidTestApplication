package com.example.adam.androidtestapplication.rotk;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.androidtestapplication.R;

/**
 * Created by adam on 13/7/16.
 */
public class RotkCharacterAdapter extends ArrayAdapter<RotkCharacter> {


    public RotkCharacterAdapter(Context context, RotkCharacter[] objects) {
        super(context, R.layout.rotkcharacter_row , objects);
    }

    // A ViewGroup are invisible containers that hold a bunch of views and
    // define their layout properties.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get current rotk character
        RotkCharacter curRotkCharacter = getItem(position);

        // The LayoutInflator puts a layout into the right View
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        // inflate takes the resource to load, the parent that the resource may be
        // loaded into and true or false if we are loading into a parent view.
        View theView = theInflater.inflate(R.layout.rotkcharacter_row, parent, false);

        //Put the character information into textView
        TextView theTextView = (TextView) theView.findViewById(R.id.rotkCharRowName);
        theTextView.setText(curRotkCharacter.get_name());
        theTextView = (TextView) theView.findViewById(R.id.rotkCharRowATK);
        theTextView.setText("ATK:"+Integer.toString(curRotkCharacter.get_atk()));
        theTextView = (TextView) theView.findViewById(R.id.rotkCharRowDEF);
        theTextView.setText("DEF:"+Integer.toString(curRotkCharacter.get_def()));


        // Get the ImageView in the layout
        //ImageView theImageView = (ImageView) theView.findViewById(R.id.imageView1);

        // We can set a ImageView like this
        //theImageView.setImageResource(R.drawable.dot);

        return theView;

    }
}
