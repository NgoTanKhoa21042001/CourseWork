package com.example.asm21;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Tag;

public class Upload extends AppCompatActivity {
    private Button get_data;
    private static final String TAG = "Upload";
    TextView textView, responseHead,text;
    MyDatabaseHelper myDB = new MyDatabaseHelper(Upload.this);
    List<String> tripName = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getNameTrip();
        textView = findViewById(R.id.responseHead);

        responseHead = findViewById(R.id.response);
        Button button = findViewById(R.id.get_data);
        text = findViewById(R.id.textView);
        // return json type
        text.setText(finalJson(setJson(tripName)));
        // nhấn get data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPost();
            }
        });

//        btn_upload = findViewById(R.id.btn_upload);
        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        get_data = findViewById(R.id.get_data);

        // Bottom
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.upload);

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
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.upload:
                       return true;
                }
                return false;
            }
        });

    }
    private void UploadPost(){
        String json = finalJson(setJson(tripName));
        APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        Call<TripCloud> call = apiInterface.getTripInformation(json);
        call.enqueue(new Callback<TripCloud>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TripCloud> call, Response<TripCloud> response) {
                if(response.isSuccessful()){
                    textView.setText("Localhost Connect onResponse:" + response.code());
                    text.setText("{\n" +
                            "    \"uploadResponseCode\": \""+response.body().getUploadResponseCode()+"\",\n" +
                            "    \"userId\": \""+response.body().getUserid()+"\",\n" +
                            "    \"number\": "+response.body().getNumber()+",\n" +
                            "    \"names\": \""+response.body().getNames()+"\",\n" +
                            "    \"message\": \""+response.body().getMessage()+"\"\n" +
                            "}");
                }
            }

            @Override
            public void onFailure(Call<TripCloud> call, Throwable t) {
                textView.setText("Error found is " + t.getMessage());
            }
        });
    }
    private void getNameTrip(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(Upload.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                tripName.add(cursor.getString(1));
            }
        }
    }
    private String setJson(List<String> tripName){
        String json1 = "";
        for(int i = 0; i<tripName.size()-1;i++){
            String item = "{\n" +
                    "      \"name\": \""+tripName.get(i)+"\"\n" +
                    "    }";
            json1 = json1 + item +",\n";
        }
        json1 = json1 + "{\n" +
                "      \"name\": \""+tripName.get(tripName.size()-1)+"\"\n" +
                "    }";
        return json1;
    }
    private String finalJson(String json){
        return "{\n" +
                "  \"userId\": \"gcd1291215\",\n" +
                "  \"detailList\": [\n" +
                json+"\n" +
                "  ]\n" +
                "}";
    }
}