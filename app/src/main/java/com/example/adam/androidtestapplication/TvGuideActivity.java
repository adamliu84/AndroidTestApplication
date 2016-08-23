package com.example.adam.androidtestapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TvGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_guide);

        try {
            //Get the TV programming list (via default hardcode url)
            ArrayList<String> arrTvPrograms = new TvProgramTask().execute().get();
            //Display of the tv programming listing on listview
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTvPrograms);
            ListView listView = (ListView) findViewById(R.id.tvprogramlistview);
            listView.setAdapter(itemsAdapter);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }

    private class TvProgramTask extends AsyncTask<Void, Void, ArrayList<String>> {

        ArrayList<String> arrTvPrograms = new ArrayList<String>();

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try{
                getTimeTitle();
            }catch(Exception e){
                Log.e("Error", e.getMessage());
            }
            return arrTvPrograms;
        }

        private void getAvailableDate() throws IOException {
            String url = "http://www.toggle.sg/en/toggle/channelguide";
            Document document = Jsoup.connect(url).get();
            Elements lAvailableDate = document.select(".tgtabs__row").get(0).select(".tgtab");
            for (Element curAvailableDate : lAvailableDate) {
                arrTvPrograms.add(curAvailableDate.attr("data-daymonthyear"));
            }
        }

        private void getTimeTitle() throws IOException {
            String url = "http://www.toggle.sg/en/toggle/channelguide";
            Document document = Jsoup.connect(url).get();
            Elements lChannelList = document.select(".epg__channel-list");
            for (Element curChannelList : lChannelList) {
                Elements lChannelItem = curChannelList.select(".epg--channel__item");
                for (Element curChannelItem : lChannelItem) {
                    //Get Time & Title
                    String szTime = curChannelItem.getElementsByClass("epg--channel__time").text();
                    String szTitle = curChannelItem.getElementsByClass("epg__title").text();
                    arrTvPrograms.add("[" + szTime + "] " + szTitle);
                }
            }
        }
    }

}
