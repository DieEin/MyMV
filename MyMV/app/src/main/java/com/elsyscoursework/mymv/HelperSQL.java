package com.elsyscoursework.mymv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Tomi on 28.12.2016 г..
 */

public class HelperSQL extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_motor_vehicle.db";

    final String CREATE_TABLE = "CREATE TABLE";
    final String VEHICLE_TABLE = "vehicle";
    final String ID = "id";
    final String TYPE = "type";
    final String MANUFACTURER = "manufacturer";
    final String MODEL = "model";

    final String SQL_CREATE_ENTRIES =
            CREATE_TABLE + " " + VEHICLE_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY," +
            TYPE + " TEXT," +
            MANUFACTURER + " TEXT," +
            MODEL + " TEXT)";

    final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VEHICLE_TABLE;

    public HelperSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addVehicle(String type, String manufacturer, String model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TYPE, type);
        values.put(MANUFACTURER, manufacturer);
        values.put(MODEL, model);

        long newVehicleRowId = db.insert(VEHICLE_TABLE, null, values);
        db.close();
    }

    public ArrayList getVehicleForList() {
        ArrayList vehicle = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + VEHICLE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                vehicle.add(cursor.getString(cursor.getColumnIndex(ID)) + "." + cursor.getString(cursor.getColumnIndex(MANUFACTURER)) + " " + cursor.getString(cursor.getColumnIndex(MODEL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return vehicle;
    }

    public HashMap<String, String> getVehicleFromId(int id) {
        HashMap<String, String> vehicle = new HashMap<>();
        String selectQuery = "SELECT * FROM " + VEHICLE_TABLE + " WHERE id = " + String.valueOf(id);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            vehicle.put(TYPE, cursor.getString(cursor.getColumnIndex(TYPE)));
            vehicle.put(MANUFACTURER, cursor.getString(cursor.getColumnIndex(MANUFACTURER)));
            vehicle.put(MODEL , cursor.getString(cursor.getColumnIndex(MODEL)));
        }
        cursor.close();
        db.close();

        return vehicle;
    }

    public ArrayList getVehicle() {
        ArrayList vehicle = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + VEHICLE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                vehicle.add(cursor.getString(cursor.getColumnIndex(TYPE)));
                vehicle.add(cursor.getString(cursor.getColumnIndex(MANUFACTURER)));
                vehicle.add(cursor.getString(cursor.getColumnIndex(MODEL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return vehicle;
    }

    public void update(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE vehicle SET model='aaaaaaa' WHERE id=" + String.valueOf(id));
        db.close();
    }

}
