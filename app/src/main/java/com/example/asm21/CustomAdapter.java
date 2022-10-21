package com.example.asm21;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList trip_id, trip_title, trip_desination, trip_date, trip_require;
    int position;
    CustomAdapter(Activity activity, Context context,
                  ArrayList trip_id,
                  ArrayList trip_title,
                  ArrayList trip_desination,
                  ArrayList trip_date,
                  ArrayList trip_require) {
        this.activity = activity;
        this.context = context;
        this.trip_id = trip_id;
        this.trip_title = trip_title;
        this.trip_desination = trip_desination;
        this.trip_date = trip_date;
        this.trip_require = trip_require;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  @SuppressLint("RecyclerView") final int position) {
        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_title_txt.setText(String.valueOf(trip_title.get(position)));
        holder.trip_desination_txt.setText(String.valueOf(trip_desination.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date.get(position)));
        holder.trip_require_txt.setText(String.valueOf(trip_require.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(trip_id.get(position)));
                intent.putExtra("title", String.valueOf(trip_title.get(position)));
                intent.putExtra("desination", String.valueOf(trip_desination.get(position)));
                intent.putExtra("date", String.valueOf(trip_date.get(position)));
                intent.putExtra("require", String.valueOf(trip_require.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trip_id.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, trip_title_txt, trip_desination_txt, trip_date_txt, trip_require_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_title_txt = itemView.findViewById(R.id.trip_title_txt);
            trip_desination_txt = itemView.findViewById(R.id.trip_desination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_require_txt = itemView.findViewById(R.id.trip_require_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
