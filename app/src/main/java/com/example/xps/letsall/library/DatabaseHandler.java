package com.example.xps.letsall.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "LetsAll_DB";

    private static final String TABLE_SESSION = "session";


    private static final String FB_ID = "fb_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SESSION_TABLE = "CREATE TABLE " + TABLE_SESSION + "("
                + FB_ID + " PRIMARY KEY)";
        db.execSQL(CREATE_SESSION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);

        onCreate(db);
    }


    public int getSessionRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SESSION;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }

    public void addUser(String fb_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FB_ID, fb_id);

Log.e("DBHAND", fb_id);

        db.insert(TABLE_SESSION, null, values);
        db.close();
    }

    public void removeUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SESSION, null, null);
        db.close();
    }

    public String getUserFBID() {
        String fb_id;

        String selectQuery = "SELECT  * FROM " + TABLE_SESSION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        //cursor.moveToNext();
        fb_id = cursor.getString(0);

        cursor.close();
        db.close();

        return fb_id;
    }


}