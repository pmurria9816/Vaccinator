package com.assignment2.vaccinator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.assignment2.vaccinator.modules.User;

public class UserDAO extends SQLiteOpenHelper {

    public static final String dbName = "Vaccinator";
    public static final int version = 1;
    public static final String TABLE_NAME = "user";
    public static final String COL1 = "id";
    public static final String COL2 = "email";
    public static final String COL3 = "password";
    public static final String COL4 = "firstName";
    public static final String COL5 = "lastName";
    public static final String COL6 = "phone";


    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2 + " TEXT NOT NULL, "
            + COL3 + " TEXT NOT NULL, "
            + COL4 + " TEXT NOT NULL, "
            + COL5 + " TEXT NOT NULL, "
            + COL6 + " TEXT NOT NULL" +
            ")" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public UserDAO(@Nullable Context context)
    {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(User data){ //insert user method
        ContentValues values = new ContentValues();
        values.put(COL2, data.getEmail());
        values.put(COL3, data.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1)
            return false;
        return true;
    }

    public Cursor viewData(){ //retrieving all users
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from " + TABLE_NAME, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor searchData(String email){ //retrieving a single user
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + TABLE_NAME+ " WHERE email =  '" + email + "'", null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean authenticate(String login, String password){
        Cursor cursor = searchData(login);

        if(cursor == null)
            return false;

        if (login == cursor.getString(1) && password == cursor.getString(2))
            return true;
        return false;
    }


}
