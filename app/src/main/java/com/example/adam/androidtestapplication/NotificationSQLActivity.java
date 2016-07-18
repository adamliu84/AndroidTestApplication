package com.example.adam.androidtestapplication;

import android.app.Dialog;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.adam.androidtestapplication.kupo.KupoDBHandler;

public class NotificationSQLActivity extends AppCompatActivity {

    Dialog m_kupoDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sql);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init custom dialog
        this.createKupoDialog();
        final Dialog kupoDialog = this.m_kupoDialog;

        //Creation click event for showing kupo dialog
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kupoDialog.show();
            }
        });

        //Get Kupo Record
        KupoDBHandler kupoDB = new KupoDBHandler(this);
        //Testing data
        KupoDBHandler.testDataInit(this);
        KupoDBHandler.testDataLog(this);
        KupoDBHandler.testDataRemove(this);
    }

    private void createKupoDialog(){
        // custom dialog
        this.m_kupoDialog = new Dialog(this);
        this.m_kupoDialog.setContentView(R.layout.kupodialog);
        this.m_kupoDialog.setTitle("Title<Timestamp>"); //TODO display time here
        final Dialog parentDialog = this.m_kupoDialog;
        //OK
        Button dialogButton = (Button) this.m_kupoDialog.findViewById(R.id.btnkupook);
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "kupook");
                parentDialog.dismiss();
            }
        });
        //Cancel
        dialogButton = (Button) this.m_kupoDialog.findViewById(R.id.btnkupocancel);
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info", "kupocancel");
                parentDialog.dismiss();
            }
        });
    }

}
