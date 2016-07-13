package com.example.adam.androidtestapplication.rotk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.androidtestapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        ImageView theImageView = (ImageView) theView.findViewById(R.id.rotkCharRowAvatar);
        new DownLoadImageTask(theImageView).execute(curRotkCharacter.get_avatarurl());

        return theView;

    }

    //http://android--code.blogspot.sg/2015/08/android-imageview-set-image-from-url.html
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
