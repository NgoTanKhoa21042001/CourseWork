package com.example.asm21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddActivity1 extends AppCompatActivity {
    TextInputLayout textInputLayout;
    TextInputEditText amountEditText, timeEditText;
    Button button_expenses;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add1);

        textInputLayout = findViewById(R.id.menu_drop);
        autoCompleteTextView = findViewById(R.id.drop_items);
        amountEditText = findViewById(R.id.amountEditText);
        timeEditText = findViewById(R.id.timeEditText);
        button_expenses = findViewById(R.id.button_expenses);
        // DROPDOWN, create dataset
        String [] items = {"Travel", "Food", "Transport"};

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(AddActivity1.this, R.layout.item_list, items);
        // connect autoCompleteTextView to itemAdapter
        autoCompleteTextView.setAdapter(itemAdapter);
        // date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddActivity1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        timeEditText.setText(date);

                    }
                },year, month,day);
                dialog.show();
            }
        });
        button_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trip_id = getIntent().getStringExtra("trip_id");
                String dropdown =autoCompleteTextView.getText().toString();
                String amount =amountEditText.getText().toString();
                String time =timeEditText.getText().toString();
                if(dropdown.isEmpty()) {
                    Toast.makeText(AddActivity1.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(amount.isEmpty()) {
                    Toast.makeText(AddActivity1.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(time.isEmpty()) {
                    Toast.makeText(AddActivity1.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDatabaseHelper1 myDB = new MyDatabaseHelper1(AddActivity1.this);
                myDB.addExpenses(autoCompleteTextView.getText().toString().trim(),
                        amountEditText.getText().toString().trim(),
                        timeEditText.getText().toString().trim(),
                        trip_id);
                Intent intent = new Intent(AddActivity1.this, Expensive.class);
                intent.putExtra("trip_id",trip_id);
                startActivity(intent);
            }
        });
    }
}