package com.example.asm21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    TextInputEditText nameEditText, desinationEditText, dateEditText, descriptionEditText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton yes, no;
    String Colector="";
    Button btnAddDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = findViewById(R.id.nameEditText);
        desinationEditText = findViewById(R.id.desinationEditText);
        dateEditText = findViewById(R.id.dateEditText);
        yes = findViewById(R.id.radioYes);
        no = findViewById(R.id.radioNo);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        // date
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        btnAddDB = findViewById(R.id.btnAddDB);
        btnAddDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String desination = desinationEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if(name.isEmpty()) {
                    Toast.makeText(AddActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    nameEditText.setError("Fied can't be empty");
                    return;
                }
                else if(desination.isEmpty()) {
                    Toast.makeText(AddActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    desinationEditText.setError("Fied can't be empty");
                    return;

                }
                else if(date.isEmpty()) {
                    Toast.makeText(AddActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    dateEditText.setError("Fied can't be empty");
                    return;

                }
                else if(description.isEmpty()) {
                    Toast.makeText(AddActivity.this, "You need to fill all required fields", Toast.LENGTH_SHORT).show();
                    descriptionEditText.setError("Fied can't be empty");
                    return;
                }

                else {
                    Colector+="Name: "+name+"\n";
                    Colector+="Desination: "+desination+"\n";
                    Colector+="Date of the Trip: "+date+"\n";
                    Colector+="Description: "+description+"\n";
                    if(yes.isChecked()) {
                        Colector+="Risk Accessment: YES"+"\n";
                        if(no.isChecked()) {
                            Colector+="Risk Accessment: NO"+"\n";
                        }
                    }
                    Toast.makeText(AddActivity.this,"\n"+Colector,Toast.LENGTH_LONG).show();
                }
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                MyDatabaseHelper myDB  = new MyDatabaseHelper(AddActivity.this);
                myDB.addTrip(nameEditText.getText().toString().trim(),
                        desinationEditText.getText().toString().trim(),
                        dateEditText.getText().toString().trim(),
                        radioButton.getText().toString().trim(),
                        descriptionEditText.getText().toString().trim()
                        );
            }
        });
    }
}

