package com.example.vitaliy.myenglishproject;

import static com.example.vitaliy.myenglishproject.R.layout.myadapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements
		TextToSpeech.OnInitListener,View.OnClickListener{
    SharedPreferences sharedPreferences;
    private static final int CM_DELETE_ID = 1;//флаг для контекстного меню
    private static final int CM_OK_ID = 2;//флаг для контекстного меню
    DBhelper dBhelper=new DBhelper(MainActivity.); //обьект для управления базой данных
    Cursor c=null;//курсор
    myAdapter myadapt;
    ArrayAdapter<String> adapter;// адаптер для хранения даных
    SQLiteDatabase db=null; //обьект бд
    final String LOG_TAG = "myLogs";
    EditText inputSearch;//экземпляр ввода
    private TextToSpeech mTTS;//для говорилки
    ListView listView;

    //****************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My lingvo book");//название активити
        Log.d(LOG_TAG, "viev1");
      //для говорилки
        mTTS = new TextToSpeech(this, this);
        //мой адаптер

        //
        final ArrayList<String> baseData = new ArrayList<>();//for keep data
      // adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, baseData);
        myadapt=new myAdapter(this,android.R.layout.simple_list_item_1,baseData);
       // Switch switch1=(Switch)findViewById(R.id.switch1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.listView);
        //для поиска
        inputSearch = (EditText) findViewById(R.id.inputSearch);
               /////
        //Для контекстного меню
        registerForContextMenu(listView);
        //**


        //событие кнопки
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(MainActivity.this,add_data.class);
                startActivity(myIntent);
            }
        });


// ЛистВью заполнение
        // Создаём пустой массив для хранения даных
       // if(switch1.get){Log.d(LOG_TAG, "Swich_chek");}

       // int savedRadioIndex2 = sharedPreferences.getInt("butt2", 0);
        db = dBhelper.getWritableDatabase();
        Log.d(LOG_TAG, "Read");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
       c= db.query("Englishtable",null,null,null,null,null,null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if(c.moveToFirst()){
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int engColIndex = c.getColumnIndex("english");
            int rusColIndex = c.getColumnIndex("russian");
            do {

                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(engColIndex) + ", email = "
                                + c.getString(rusColIndex));

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
                // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView


                listView.setAdapter(myadapt);
                //получаю сохранёные данные
                sharedPreferences = getSharedPreferences("mysettings", MODE_PRIVATE);
                int savedRadioIndex2 = sharedPreferences.getInt("butt2", 0);
                if(savedRadioIndex2==1) {
                    baseData.add(c.getString(engColIndex));//добавляю в список даые из колонки английских слов
                }
                else {
                    baseData.add(c.getString(rusColIndex));//добавляю в список даые из колонки английских слов
                 }
            }
            while (c.moveToNext());
            c.close();
            //для поиска


        }


        //Событие по нажатию на элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db = dBhelper.getWritableDatabase();
                final Cursor c= db.query("Englishtable",null,null,null,null,null,null);
                if(c.moveToFirst()){
                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int engColIndex = c.getColumnIndex("english");
                    int rusColIndex = c.getColumnIndex("russian");
                    do {

                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) + ", name = "
                                        + c.getString(engColIndex) + ", email = "
                                        + c.getString(rusColIndex));

                    }
                    while (c.moveToNext());
                }

                int rusColIndex = c.getColumnIndex("russian");
                int engColIndex = c.getColumnIndex("english");
                c.moveToPosition(position);//ишу позицию курсора соответствующуую к нажатому
                Log.d(LOG_TAG, "itemClick: position = " + position + ", rus = " + c.getString(rusColIndex));

               // Нужно разобраться что такое  String utteranceId=this.hashCode() + "";
                sharedPreferences = getSharedPreferences("mysettings", MODE_PRIVATE);
                int savedRadioIndex1 = sharedPreferences.getInt("butt1", 0);
                int savedRadioIndex2 = sharedPreferences.getInt("butt2", 0);
                //Если чекнуто то базарить/беру инфу из настроек
                if(savedRadioIndex1==1) {
                    String utteranceId = this.hashCode() + "";
                    mTTS.speak(c.getString(engColIndex), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                }
                if(savedRadioIndex2==1){
                //если не чекнуто то только тост выводить
                Toast rusTranslate=Toast.makeText(getApplicationContext(),c.getString(rusColIndex),Toast.LENGTH_SHORT);
                rusTranslate.show();}
                else {
                    Toast rusTranslate=Toast.makeText(getApplicationContext(),c.getString(engColIndex),Toast.LENGTH_SHORT);
                    rusTranslate.show();
                }
                c.close();
                db.close();
            }


        });

    }
    //контекстное меню

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID,0,"Do you want to delete?");
        menu.add(0,CM_OK_ID,0,"OK");
    }

    //**
    //**Cсобытие для удаления** Для контекстного меню
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if (item.getItemId() == CM_OK_ID) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // удаляем Map из коллекции, используя позицию пункта в списке
            Log.d(LOG_TAG, "selectedpunkt"+acmi.position);

//   подключаюсь к бд и полуаю курсор

            db = dBhelper.getReadableDatabase();
            final Cursor c = db.query("Englishtable", null, null, null, null, null, null);
            c.moveToPosition(acmi.position);
            @SuppressLint("Range") int bb = c.getInt(c.getColumnIndex("id"));
            Log.d(LOG_TAG, "Setid" + bb);
            db.delete("Englishtable", "english = " + bb, null);
            db.delete("Englishtable", "russian = " + bb, null);
            db.delete("Englishtable", "id = " + bb, null);

            Log.d(LOG_TAG, "deleted rows count = " + bb);

            c.close();
            db.close();
            onResume();
            return true;

        }
        return super.onContextItemSelected(item);


    }
    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "resume");
        super.onResume();
        final ArrayList<String> baseData = new ArrayList<>();//for keep data
        // adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, baseData);
        myadapt=new myAdapter(this, myadapter,baseData);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        Log.d(LOG_TAG, "update");
        final ListView listView = (ListView)findViewById(R.id.listView);
        final Cursor c= db.query("Englishtable",null,null,null,null,null,null);
        listView.clearFocus();
        if(c.moveToFirst()){
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int engColIndex = c.getColumnIndex("english");
            int rusColIndex = c.getColumnIndex("russian");
            do {

                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(engColIndex) + ", email = "
                                + c.getString(rusColIndex));

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
                // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
                listView.setAdapter(myadapt);
                sharedPreferences = getSharedPreferences("mysettings", MODE_PRIVATE);
                int savedRadioIndex2 = sharedPreferences.getInt("butt2", 0);
                if(savedRadioIndex2==1) {
                    baseData.add(c.getString(engColIndex));//добавляю в список даые из колонки английских слов
                }
                else {
                    baseData.add(c.getString(rusColIndex));//добавляю в список даые из колонки английских слов
                }

            }
            while (c.moveToNext());

        }
        c.close();
        db.close();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch (item.getItemId())
       {

           case R.id.Setings:
               Log.d(LOG_TAG, "Menu");

             Intent intentmenu=new Intent(this,settings_menu.class);
             startActivity(intentmenu);



               break;
       }

        return super.onOptionsItemSelected(item);
    }

//для говорилки
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            Locale locale = new Locale("en");

            int result = mTTS.setLanguage(locale);
            //int result = mTTS.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Ошибка!");
            } else {
               // mButton.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Ошибка!");
        }
    }


//Нажатие на кнопку поиск
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchbutton:
                //получаю сохранёные данные
                sharedPreferences = getSharedPreferences("mysettings", MODE_PRIVATE);
                int savedRadioIndex2 = sharedPreferences.getInt("butt2", 0);



                // listView.smoothScrollToPosition(13);
                db = dBhelper.getWritableDatabase();
                String[] inputText={inputSearch.getText().toString()};
                Cursor cursor = null;
                if(savedRadioIndex2==1) {
                    cursor = db.query("Englishtable", new String[]{"russian", "english", "id"}, "english = ?", inputText, null, null, null);
                }
                else{cursor = db.query("Englishtable", new String[]{"russian", "english", "id"}, "russian = ?", inputText, null, null, null);}
                int idColIndex = cursor.getColumnIndex("id");
                int engColIndexx = cursor.getColumnIndex("english");
                int ruColIndexx = cursor.getColumnIndex("russian");
                cursor.moveToFirst();
                if((cursor.getCount() == 0)){Log.d(LOG_TAG,"CursorNull");
                    Toast nosearch=Toast.makeText(getApplicationContext(),"Не найденно в вашей базе",Toast.LENGTH_SHORT);
                    nosearch.show();}
                else {
                    cursor.moveToFirst();
                    String test1 = cursor.getString(engColIndexx);
                    String rustranslate = cursor.getString(ruColIndexx);


                    String input = inputText[0];

                    Log.d(LOG_TAG, "inputText" + inputText[0] + "test1" + test1 + "test2" + input);
                    if (savedRadioIndex2 == 1) {
                        if (test1.equalsIgnoreCase(input) & (input.equalsIgnoreCase("") != true)) {
                            Log.d(LOG_TAG, String.valueOf(engColIndexx));
                            Log.d(LOG_TAG, "IDbut = " + cursor.getInt(idColIndex) + ", namebut = "
                                    + cursor.getString(engColIndexx));
                            //использую свой метод чтоб найти в адаптере слово и его позицию
                            int cursorPos = myadapt.search(input);
                            Log.d(LOG_TAG, "Cursor position" + cursorPos + 1);
                            listView.smoothScrollToPosition(cursorPos + 1);
                            //конец
                            TextView translate = (TextView) findViewById(R.id.translate);

                            translate.setText(rustranslate);
                            //очистка едит текст после вывода найденного
                            inputSearch.setText(null);
                            cursor.close();
                            db.close();
                        } else {
                            Toast nosearch = Toast.makeText(getApplicationContext(), "Введите слово", Toast.LENGTH_SHORT);
                            nosearch.show();
                        }
                    }
                    //если ищу в русском варианте
                    else{
                        if (rustranslate.equalsIgnoreCase(input) & (input.equalsIgnoreCase("") != true)) {
                        Log.d(LOG_TAG, String.valueOf(ruColIndexx));
                        Log.d(LOG_TAG, "IDbut = " + cursor.getInt(idColIndex) + ", namebut = "
                                + cursor.getString(ruColIndexx));
                        //использую свой метод чтоб найти в адаптере слово и его позицию
                        int cursorPos = myadapt.search(input);
                        Log.d(LOG_TAG, "Cursor position" + cursorPos + 1);
                        listView.smoothScrollToPosition(cursorPos + 1);
                        //конец
                        TextView translate = (TextView) findViewById(R.id.translate);

                        translate.setText(test1);
                        //очистка едит текст после вывода найденного
                        inputSearch.setText(null);
                        cursor.close();
                        db.close();
                    } else {
                        Toast nosearch = Toast.makeText(getApplicationContext(), "Введите слово", Toast.LENGTH_SHORT);
                        nosearch.show();
                    }}
                    break;
                }
        }

    }
}

