package com.example.asm21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "AllTrip.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_trip";
    private static final String COLUMN_TRIP = "_id";
    private static final String COLUMN_TITLE = "trip_title";
    private static final String COLUMN_DESINATION = "trip_desination";
    private static final String COLUMN_DATE = "trip_date";
    private static final String COLUMN_REQUIRE = "trip_require";
//    private static final String COLUMN_DESCRIPTION = "trip_description";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "+ TABLE_NAME +
                        " (" + COLUMN_TRIP + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_DESINATION + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_REQUIRE + " TEXT) ; " ;
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTrip(String title, String desination, String date, String require) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESINATION, desination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REQUIRE, require);
        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
            return cursor;
    }
    void updateData(String row_id, String title, String desination, String date, String require) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESINATION, desination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REQUIRE, require);

       long result =  db.update(TABLE_NAME, cv, "_id=?", new String[] {row_id});
       if(result == -1) {
           Toast.makeText(context, "Failed to Updated", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(context, "Succeffuly to Updated", Toast.LENGTH_SHORT).show();
       }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //let's store the result of our delete
        long result =  db.delete(TABLE_NAME, "_id=?", new String[] {row_id});

        if(result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }

    public Cursor searchTrips(String text) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from" +TABLE_NAME+" WHERE "+ COLUMN_TITLE+" Like '%"+text+"%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

}
