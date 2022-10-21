package com.example.asm21;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

//        btn_upload = findViewById(R.id.btn_upload);
        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        get_data = findViewById(R.id.get_data);

        get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<Post> call = apiInterface.getAllData();

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Log.e(TAG, "onResponse: "+response.code()  );
                        Log.e(TAG, "onResponse: date : "+response.body().getDate()  );
                        Log.e(TAG, "onResponse: temperatureC : "+response.body().getTemperatureC()  );
                        Log.e(TAG, "onResponse: temperatureF : "+response.body().getTemperatureF()  );
                        Log.e(TAG, "onResponse: summary : "+response.body().getSummary()  );
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
            }
        });

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

}