package com.example.vitaliy.myenglishproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends ArrayAdapter {
    int flag=0;
    Context ctx;
    int Resours;
    LayoutInflater lInflater;
    ArrayList<String> objects;

    public myAdapter( Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.objects=objects;
        ctx=context;
        Resours=resource;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
//Мой метод для поиска
  protected int search(String intext){

    return objects.indexOf(intext);

   }
    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // используем созданные, но не используемые view
        View view = convertView;
        //if (view == null) {
            view = lInflater.inflate(R.layout.myadapter, parent, false);
       // }

        //Product p = getProduct(position);
        // заполняем View данными

        TextView engtext=(TextView)view.findViewById(R.id.engtext);
        TextView number=(TextView)view.findViewById(R.id.number);
        String d;


        d=String.valueOf(position+1);
        number.setText(d);
        engtext.setText(objects.get(position));
        //Вставить разделитель если нет значений
        if(objects.get(position).equals("")& objects.get(position).getBytes().length<1) {
            view.setBackgroundColor(Color.RED);
            engtext.setText("Delimiter");
            number.setText(null);
            flag=1;
        }

        return view;
    }
}