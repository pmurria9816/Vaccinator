package com.assignment2.vaccinator.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.assignment2.vaccinator.models.Appointment;
import com.assignment2.vaccinator.models.User;

public class AppointmentDAO extends SQLiteOpenHelper {

    public static final String dbName = "Vaccinator";
    public static final int version = 1;
    public static final String TABLE_NAME = "appointment";
    public static final String COL1 = "appointmentId";
    public static final String COL2 = "user";
    public static final String COL3 = "firstName";
    public static final String COL4 = "lastName";
    public static final String COL5 = "email";
    public static final String COL6 = "hospital";
    public static final String COL7 = "vaccine";
    public static final String COL8 = "age";
    public static final String COL9 = "dateSlot";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2 + " INTEGER NOT NULL REFERENCES user(userid) ON UPDATE CASCADE ON DELETE CASCADE, "
            + COL3 + " TEXT NOT NULL, "
            + COL4 + " TEXT NOT NULL, "
            + COL5 + " TEXT NOT NULL, "
            + COL6 + " TEXT NOT NULL, "
            + COL7 + " TEXT NOT NULL, "
            + COL8 + " INTEGER NOT NULL, "
            + COL9 + " TEXT NOT NULL " +
            ")" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AppointmentDAO(@Nullable Context context)
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

    public boolean insert(Appointment data){ //insert user method
        ContentValues values = new ContentValues();
        values.put(COL2, data.getUser());
        values.put(COL3, data.getFirstName());
        values.put(COL4, data.getLastName());
        values.put(COL5, data.getEmail());
        values.put(COL6, data.getHospital());
        values.put(COL7, data.getVaccine());
        values.put(COL8, data.getAge());
        values.put(COL9, data.getSlot().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1)
            return false;
        return true;
    }

    public boolean update(Appointment data, int id){
        ContentValues values = new ContentValues();
        values.put(COL1, id);
        values.put(COL2, data.getUser());
        values.put(COL3, data.getFirstName());
        values.put(COL4, data.getLastName());
        values.put(COL5, data.getEmail());
        values.put(COL6, data.getHospital());
        values.put(COL7, data.getVaccine());
        values.put(COL8, data.getAge());
        values.put(COL9, data.getSlot().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(TABLE_NAME, values, COL1 + " = " +id, null);
        if(result == -1)
            return false;
        return true;
    }

    public boolean delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL1 + " = " +id, null);
        if(result == -1)
            return false;
        return true;
    }

    public Cursor getAppointments(@Nullable Integer id) //retrieving data method
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        if(id.equals(null))
            cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        else
            cursor = db.rawQuery("select * from " + TABLE_NAME + "WHERE user = " +id, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }
}
