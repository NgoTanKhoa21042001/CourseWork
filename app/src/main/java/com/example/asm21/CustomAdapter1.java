package com.example.asm21;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.MyViewHolder> {
   private Context context;
   Activity activity;
   private ArrayList expenses_id, expenses_type, expenses_amount, expenses_time;
    CustomAdapter1(Context context,
                   ArrayList expenses_id,
                   ArrayList expenses_type,
                   ArrayList expenses_time,
                   ArrayList expenses_amount) {
        this.activity = activity;
        this.context = context;
        this.expenses_id = expenses_id;
        this.expenses_type = expenses_type;
        this.expenses_time = expenses_time;
        this.expenses_amount = expenses_amount;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.expenses_id_txt.setText(String.valueOf(expenses_id.get(position)));
        holder.expenses_type_txt.setText(String.valueOf(expenses_type.get(position)));
        holder.expenses_time_txt.setText(String.valueOf(expenses_time.get(position)));
        holder.expenses_amount_txt.setText(String.valueOf(expenses_amount.get(position)));

    }

    @Override
    public int getItemCount() {
        return  expenses_id.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expenses_id_txt, expenses_type_txt, expenses_time_txt ,expenses_amount_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expenses_id_txt = itemView.findViewById(R.id.expenses_id_txt);
            expenses_type_txt = itemView.findViewById(R.id.expenses_type_txt);
            expenses_time_txt = itemView.findViewById(R.id.expenses_time_txt);
            expenses_amount_txt = itemView.findViewById(R.id.expenses_amount_txt);
        }
    }
}
