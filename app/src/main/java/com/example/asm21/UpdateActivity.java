package com.example.asm21;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    TextInputEditText nameEditText, desinationEditText, dateEditText, descriptionEditText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button update_button, expenses_button, delete_button;
    String id, title, desination, date, require, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nameEditText = findViewById(R.id.nameEditText2);
        desinationEditText = findViewById(R.id.desinationEditText2);
        dateEditText = findViewById(R.id.dateEditText2);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        dateEditText.setText(date);

                    }
                },year, month,day);
                dialog.show();
            }
        });
        radioGroup = findViewById(R.id.radioGroup);
        descriptionEditText = findViewById(R.id.descriptionEditText2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        expenses_button = findViewById(R.id.expenses_button);
        getAndSetIntentData();
        // set action bar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                // And oonly then we call

                title = nameEditText.getText().toString().trim();
                desination = desinationEditText.getText().toString().trim();
                date = dateEditText.getText().toString().trim();
                require = radioButton.getText().toString().trim();
                description = descriptionEditText.getText().toString().trim();
                if(title.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    nameEditText.setError("Fied can't be empty");
                    return;
                }
                else if(desination.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    desinationEditText.setError("Fied can't be empty");
                    return;

                }
                else if(date.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    dateEditText.setError("Fied can't be empty");
                    return;
                }

                else if(description.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    descriptionEditText.setError("Fied can't be empty");
                    return;
                }
                myDB.updateData(id, title, desination, date, require, description);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
        // Plus icon
        expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateActivity.this, Expensive.class);
                intent.putExtra("trip_id",id);
                startActivity(intent);
            }
        });

    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("desination") &&
                getIntent().hasExtra("date") &&
                getIntent().hasExtra("require") &&
                getIntent().hasExtra("description")
        ) {
            // Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            desination = getIntent().getStringExtra("desination");
            date = getIntent().getStringExtra("date");
            require = getIntent().getStringExtra("require");
            description = getIntent().getStringExtra("description");
            // Setting Intent Data
            nameEditText.setText(title);
            desinationEditText.setText(desination);
            dateEditText.setText(date);
//            radioButton.setChecked(true);
            descriptionEditText.setText(description);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete" + title + " ?");
        builder.setMessage("Are you sure you want to delete" + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
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