package com.example.vitaliy.myenglishproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class settings_menu extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
     SharedPreferences mysettings;
    final String LOG_TAG = "myLogs";
    Switch switch1;
    Switch switch2;
    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "mysettings";
    String KEY_RADIOBUTTON_INDEX = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Settings");//название активити
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        Switch switch1=(Switch)findViewById(R.id.switch1);
        Switch switch2=(Switch)findViewById(R.id.switch2);
        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);

      //  mysettings=getSharedPreferences(APP_Settings, Context.MODE_PRIVATE );
        LoadPreferences();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int set1;
        int set2;
        switch (buttonView.getId()) {
            case R.id.switch1:
            Log.d(LOG_TAG, isChecked ? "on" : "off");
                if(isChecked){set1=1;}else{set1=0;}
                KEY_RADIOBUTTON_INDEX="butt1";
                SavePreferences(KEY_RADIOBUTTON_INDEX ,set1);
                Log.d(LOG_TAG, String.valueOf(isChecked));
                break;

            case R.id.switch2:
                if(isChecked){set2=1;}else{set2=0;}
                KEY_RADIOBUTTON_INDEX="butt2";
                SavePreferences(KEY_RADIOBUTTON_INDEX ,set2);
                Log.d(LOG_TAG, String.valueOf(isChecked));
                break;
        }
    }

    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    private void LoadPreferences() {
        switch1=(Switch)findViewById(R.id.switch1);
        switch2=(Switch)findViewById(R.id.switch2);
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        int savedRadioIndex1 = sharedPreferences.getInt("butt1", 0);
        int savedRadioIndex2 = sharedPreferences.getInt("butt2", 1);
        if(savedRadioIndex1==1){switch1.setChecked(true);}
        if(savedRadioIndex2==1){switch2.setChecked(true);}
        Log.d(LOG_TAG,"LOAD"+ String.valueOf(savedRadioIndex2));
       // savedCheckedRadioButton.setChecked(true);
    }


}
