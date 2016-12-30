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

    final String TEST_ID = "test_id";
    final String TEST_TABLE = "test";

    final String SQL_CREATE_TEST =
            "CREATE TABLE " + TEST_TABLE + " (id INTEGER PRIMARY KEY, varr TEXT)";

    final String SQL_CREATE_MANAGE_TABLES =
            "CREATE TABLE manage (id INTEGER PRIMARY KEY, " + TEST_ID + " INTEGER, vehicle_id INTEGER, FOREIGN KEY ("+TEST_ID+") REFERENCES "+TEST_TABLE+"(id), FOREIGN KEY (vehicle_id) REFERENCES vehicle(id) )";

    public HelperSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_TEST);
        db.execSQL(SQL_CREATE_MANAGE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS manage");
        onCreate(db);
    }

    public void addVehicle(String type, String manufacturer, String model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TYPE, type);
        values.put(MANUFACTURER, manufacturer);
        values.put(MODEL, model);

        long newVehicleRowId = db.insert(VEHICLE_TABLE, null, values);

        ContentValues test = new ContentValues();
        test.put("varr", "");

        long testt = db.insert(TEST_TABLE, null, test);

        ContentValues testing = new ContentValues();
        testing.put(TEST_ID, newVehicleRowId);
        testing.put("vehicle_id", newVehicleRowId);

        long testtt = db.insert("manage", null, testing);

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

    public void update(String tst, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE test SET varr='" + tst + "' WHERE id=" + String.valueOf(id));
        db.close();
    }

    public String showTest(int id) {
        String test = new String();
        String selectQ = "SELECT * FROM test JOIN manage ON test.id=manage.test_id JOIN vehicle ON manage.vehicle_id=" + String.valueOf(id);//WHERE id=" + String.valueOf(id);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQ, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            test = cursor.getString(cursor.getColumnIndex("varr"));
        }

        return test;
    }

}
