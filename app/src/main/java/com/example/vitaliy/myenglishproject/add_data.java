package com.example.vitaliy.myenglishproject;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class add_data extends AppCompatActivity implements View.OnClickListener {
    ContentValues contentData = new ContentValues();//для хранения данных
    EditText rus;
    EditText eng;
    Button btnAdd;
    DBhelper dBhelper=new DBhelper(this); //обьект для управления базой данных
    final String LOG_TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Добавьте своё новое слово");//название активити
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "viev2");
        setContentView(R.layout.activity_add_data);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        rus=(EditText)findViewById(R.id.ruText);
        eng=(EditText)findViewById(R.id.engText);
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick ");
        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "BTN ");

                String stru = rus.getText().toString();
                String steng = eng.getText().toString();
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                contentData.put("english", steng);
                contentData.put("russian", stru);
                Log.d(LOG_TAG, "contentData.put ");
                break;


        }
        Log.d(LOG_TAG, "connect db");
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        // вставляем запись и получаем ее ID

        long rowID = db.insert("Englishtable", null, contentData);

        Toast rusTranslate=Toast.makeText(getApplicationContext(),"Слово добавлено",Toast.LENGTH_LONG);
        finish();
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);







    }
}

