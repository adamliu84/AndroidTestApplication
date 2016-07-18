package com.example.adam.androidtestapplication.kupo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;

/**
 * Created by adam on 18/7/16.
 */
public class KupoDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "KupoDatabase";
    private static final String TABLE_KUPO_RECORD = "KupoRecord";

    public KupoDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String szKupoRecordCreate = "CREATE TABLE " + TABLE_KUPO_RECORD + "(" +
                "    ID INTEGER PRIMARY KEY," +
                "    KupoValue TEXT," +
                "    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";
        Log.i(this.toString(), "onCreate");
        sqLiteDatabase.execSQL(szKupoRecordCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_KUPO_RECORD);
        onCreate(sqLiteDatabase);
    }

    public static void testDataInit(Context context) {

        KupoDBHandler kupoDB = new KupoDBHandler(context);
        SQLiteDatabase db = kupoDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("KupoValue", "test1");
        db.insert(TABLE_KUPO_RECORD, null, values);

        values = new ContentValues();
        values.put("KupoValue", "test2");
        db.insert(TABLE_KUPO_RECORD, null, values);

        values = new ContentValues();
        values.put("KupoValue", "test3");
        db.insert(TABLE_KUPO_RECORD, null, values);

        db.close();
    }

    public static void testDataLog(Context context) {
        KupoDBHandler kupoDB = new KupoDBHandler(context);
        SQLiteDatabase db = kupoDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KUPO_RECORD, null);
        while (cursor.moveToNext()) {
            Integer nId = cursor.getInt(0);
            String szKupoValue = cursor.getString(1);
            Date dateTimestamp = new Date(cursor.getLong(2));
            String temp = Integer.toString(nId)+" "+szKupoValue+" "+dateTimestamp.toString();
            Log.d("oei", temp);
        };
    }

    public static void testDataRemove(Context context){
        KupoDBHandler kupoDB = new KupoDBHandler(context);
        SQLiteDatabase db = kupoDB.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_KUPO_RECORD);
        db.close();
    }
}

