package com.smis935820.club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    DatabaseHandler DB;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        showData();
    }

    private void showData() {
        DB = new DatabaseHandler(ListActivity.this);
        c = DB.getData();

        //Evaluar si existe registros

        if (c.moveToFirst()){
            ListView listData = (ListView)findViewById(R.id.listData);

            //Arraylist
            final ArrayList<String> allData = new ArrayList<String>();
            final ArrayAdapter<String> aData = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_expandable_list_item_1, allData);

            //Adaptador a ListView
            listData.setAdapter(aData);

            //Mostrar registros
            do {
                allData.add(c.getString(2));
            }while (c.moveToNext());
        } else{
            Toast.makeText(this, "No hay registros para mostrar", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public  void addData(View v){
        Intent i = new Intent(ListActivity.this, MainActivity.class);
        startActivity(i);
    }
}