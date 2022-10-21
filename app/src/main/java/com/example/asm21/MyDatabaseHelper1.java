package com.example.asm21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper1 extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ExpenseLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "you_library";
    private static final String COLUMN_EXPENSES = "_id";
    private static final String COLUMN_TYPE = "expenses_type";
    private  static final String COLUMN_AMOUNT = "expenses_amount";
    private static final String COLUMN_TIME = "expenses_time";
    private static final String COLUMN_TRIP = "trip_id";

    public MyDatabaseHelper1(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE "+ TABLE_NAME +
                        " (" + COLUMN_EXPENSES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_AMOUNT + " TEXT, " +
                        COLUMN_TIME + " TEXT, " +
                        COLUMN_TRIP + " TEXT, " +
                        "FOREIGN KEY (" + COLUMN_TRIP + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_EXPENSES+"));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);

    }
    void addExpenses(String type, String amount, String time, String trip_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE, type );
        cv.put(COLUMN_AMOUNT, amount );
        cv.put(COLUMN_TIME, time );
        cv.put(COLUMN_TRIP, trip_id);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(String trip_id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE trip_id =?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null) {
            cursor = db.rawQuery(query, new String[]{trip_id});
        }
        return cursor;
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
