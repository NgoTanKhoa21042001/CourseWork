package com.example.asm21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Expensive extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton expenses_button;
    MyDatabaseHelper1 myDB;
    ArrayList<String> expenses_id ,expenses_type, expenses_amount, expenses_time;
    CustomAdapter1 customAdapter;
    String trip_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensive);


        trip_id = getIntent().getStringExtra("trip_id");
        recyclerView  = findViewById(R.id.recyclerView);
        expenses_button = findViewById(R.id.expenses_button);

        expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Expensive.this, AddActivity1.class);
                intent.putExtra("trip_id", trip_id);
                startActivity(intent);
            }
        });
        myDB = new MyDatabaseHelper1(Expensive.this);
        expenses_id = new ArrayList<>();
        expenses_type = new ArrayList<>();
        expenses_amount = new ArrayList<>();
        expenses_time = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter1(Expensive.this,expenses_id, expenses_type, expenses_amount, expenses_time);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Expensive.this));
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData(trip_id);
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                expenses_id.add(cursor.getString(0));
                expenses_type.add(cursor.getString(1));
                expenses_amount.add(cursor.getString(2));
                expenses_time.add(cursor.getString(3));
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu2, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all1) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            MyDatabaseHelper1 myDB = new MyDatabaseHelper1(this);
            myDB.deleteAllData();
            Intent intent = new Intent(this, Expensive.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}