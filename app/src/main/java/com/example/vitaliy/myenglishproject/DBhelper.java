package com.example.vitaliy.myenglishproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vitaliy on 22.02.2018.
 */

class DBhelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBhelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG, "Create");
        db.execSQL("create table Englishtable ("
                + "id integer primary key autoincrement,"
                + "english,"
                + "russian" + ");");
        Log.d(LOG_TAG, "Creating");
    }

    //обновление БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         //Вставляю колонку для идентефикации групп
        db.execSQL("alter table Englishtable add column group integer;");
    }
}