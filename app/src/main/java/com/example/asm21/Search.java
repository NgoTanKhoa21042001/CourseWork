package com.example.asm21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

public class Search extends AppCompatActivity {
    ListView listView;
//    String[] name = {"Holiday", "Black Panther"};
    ArrayList<String> trip_id, trip_title, trip_desination, trip_date, trip_require;
    ArrayAdapter<String> arrayAdapter;
    EditText nm;
    Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        nm = findViewById(R.id.nm);
        search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = nm.getText().toString();

                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("AllTrip.db", Context.MODE_PRIVATE, null);
                Cursor c = simpledb.rawQuery("select * from my_trip where trip_title='"+n+"'", null);
                if(c.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Id:  \t"+ c.getString(0)+ "\n");
                    buffer.append("Title:  \t"+ c.getString(1)+ "\n");
                    buffer.append("Desination:  \t"+ c.getString(2)+ "\n");
                    buffer.append("Date:  \t"+ c.getString(3)+ "\n");
                    buffer.append("Require:  \t"+ c.getString(4)+ "\n");
                }
                Toast.makeText(getApplicationContext(), "Result: \n" + buffer.toString(), Toast.LENGTH_LONG).show();
            }
        });
        listView = findViewById(R.id.listview);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        listView.setAdapter(arrayAdapter);

        // Bottom
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                       return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), Upload.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Type here to search");
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                arrayAdapter.getFilter().filter(newText);
//
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}