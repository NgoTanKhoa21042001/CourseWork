package com.example.asm21;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm21.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    TextInputLayout nameContainer, desinationContainer, dateContainer, descriptionContainer;
    TextInputEditText nameEditText, desinationEditText, dateEditText, descriptionEditText;
    TextView textName, textDesination, textDate, textRadio, textDescription;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String Colector="";
    Button btnAddDB , expenses_button;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> trip_id, trip_title, trip_desination, trip_date, trip_require;
    CustomAdapter customAdapter;
    private Object BottomNavigationView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Bottom
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.search:
                    startActivity(new Intent(getApplicationContext(), Search.class));
                    overridePendingTransition(0,0);
                    return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), Upload.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        nameEditText = findViewById(R.id.nameEditText);
        desinationEditText = findViewById(R.id.desinationEditText);
        dateEditText = findViewById(R.id.dateEditText);
        radioGroup = findViewById(R.id.radioGroup);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        btnAddDB = findViewById(R.id.btnAddDB);



        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        expenses_button = findViewById(R.id.expenses_button);
        // btnAddDB
        add_button.setOnClickListener(new View.OnClickListener() {
            // bấm vào icon qua màn hình khác
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        myDB = new MyDatabaseHelper(MainActivity.this);
        trip_id = new ArrayList<>();
        trip_title = new ArrayList<>();
        trip_desination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_require = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,this, trip_id, trip_title, trip_desination, trip_date, trip_require);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager  fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }
    void storeDataInArrays() {
        // read all data
        Cursor cursor =  myDB.readAllData();
        if(cursor.getCount() == 0 ) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                trip_id.add(cursor.getString(0));
                trip_title.add(cursor.getString(1));
                trip_desination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_require.add(cursor.getString(4));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                // Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}