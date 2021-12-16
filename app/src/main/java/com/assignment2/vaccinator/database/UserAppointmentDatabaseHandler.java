package com.assignment2.vaccinator.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.assignment2.vaccinator.models.Appointment;
import com.assignment2.vaccinator.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserAppointmentDatabaseHandler extends SQLiteOpenHelper {

    public static final String dbName = "Vaccinator";
    public static final int version = 1;

    //User Table
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COL1 = "userId";
    public static final String USER_COL2 = "email";
    public static final String USER_COL3 = "password";
    public static final String USER_COL4 = "firstName";
    public static final String USER_COL5 = "lastName";
    public static final String USER_COL6 = "phone";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "("
            + USER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COL2 + " TEXT NOT NULL, "
            + USER_COL3 + " TEXT NOT NULL, "
            + USER_COL4 + " TEXT NOT NULL, "
            + USER_COL5 + " TEXT NOT NULL, "
            + USER_COL6 + " TEXT NOT NULL" +
            ")" ;

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

    //Appointment Table
    public static final String APPT_TABLE_NAME = "appointment";
    public static final String APPT_COL1 = "appointmentId";
    public static final String APPT_COL2 = "user";
    public static final String APPT_COL3 = "firstName";
    public static final String APPT_COL4 = "lastName";
    public static final String APPT_COL5 = "email";
    public static final String APPT_COL6 = "hospital";
    public static final String APPT_COL7 = "vaccine";
    public static final String APPT_COL8 = "age";
    public static final String APPT_COL9 = "dateSlot";

    public static final String CREATE_APPT_TABLE = "CREATE TABLE IF NOT EXISTS " + APPT_TABLE_NAME + "("
            + APPT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + APPT_COL2 + " INTEGER NOT NULL REFERENCES user(userid) ON UPDATE CASCADE ON DELETE CASCADE, "
            + APPT_COL3 + " TEXT NOT NULL, "
            + APPT_COL4 + " TEXT NOT NULL, "
            + APPT_COL5 + " TEXT NOT NULL, "
            + APPT_COL6 + " TEXT NOT NULL, "
            + APPT_COL7 + " TEXT NOT NULL, "
            + APPT_COL8 + " INTEGER NOT NULL, "
            + APPT_COL9 + " TEXT NOT NULL " +
            ")" ;

    private static final String DROP_APPT_TABLE = "DROP TABLE IF EXISTS " + APPT_TABLE_NAME;


    public UserAppointmentDatabaseHandler(@Nullable Context context)
    {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_APPT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_APPT_TABLE);
        onCreate(db);
    }

    public boolean addUser(User data){ //insert user method
        ContentValues values = new ContentValues();
        values.put(USER_COL2, data.getEmail());
        values.put(USER_COL3, data.getPassword());
        values.put(USER_COL4, data.getFirstName());
        values.put(USER_COL5, data.getLastName());
        values.put(USER_COL6, data.getPhone());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(USER_TABLE_NAME, null, values);
        if(result == -1)
            return false;
        return true;
    }

    public Cursor getUsers(){ //retrieving all users
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from " + USER_TABLE_NAME, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getUserByEmail(String email){ //retrieving a single user
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from " + USER_TABLE_NAME+ " WHERE email =  '" + email + "'", null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean authenticate(String login, String password){

        Cursor cursor = getUserByEmail(login);

        if(cursor.getCount() < 1)
            return false;

        if (cursor.getString(1).compareTo(login) == 0 && cursor.getString(2).compareTo(password) == 0)
            return true;
        return false;
    }

    public boolean addAppointment(Appointment data){ //insert user method
        ContentValues values = new ContentValues();
        values.put(APPT_COL2, data.getUser());
        values.put(APPT_COL3, data.getFirstName());
        values.put(APPT_COL4, data.getLastName());
        values.put(APPT_COL5, data.getEmail());
        values.put(APPT_COL6, data.getHospital());
        values.put(APPT_COL7, data.getVaccine());
        values.put(APPT_COL8, data.getAge());
        values.put(APPT_COL9, data.getSlot().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(APPT_TABLE_NAME, null, values);
        if(result == -1)
            return false;
        return true;
    }

    public boolean update(Appointment data, int id){
        ContentValues values = new ContentValues();
        values.put(APPT_COL1, id);
        values.put(APPT_COL2, data.getUser());
        values.put(APPT_COL3, data.getFirstName());
        values.put(APPT_COL4, data.getLastName());
        values.put(APPT_COL5, data.getEmail());
        values.put(APPT_COL6, data.getHospital());
        values.put(APPT_COL7, data.getVaccine());
        values.put(APPT_COL8, data.getAge());
        values.put(APPT_COL9, data.getSlot().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(APPT_TABLE_NAME, values, APPT_COL1 + " = " +id, null);
        if(result == -1)
            return false;
        return true;
    }

    public boolean delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(APPT_TABLE_NAME, APPT_COL1 + " = " +id, null);
        if(result == -1)
            return false;
        return true;
    }

    public List<Appointment> getAppointments(@Nullable Integer id) //retrieving data method
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        if(id.equals(null))
            cursor = db.rawQuery("select * from " + APPT_TABLE_NAME, null);
        else
            cursor = db.rawQuery("select * from " + APPT_TABLE_NAME + " WHERE user = " +id, null);

        return cursorToList(cursor);
    }

    // Converts the cursor to List of StudentGrade object by iterating through the cusrsor.
    @SuppressLint("Range")
    private List<Appointment> cursorToList(Cursor cursor) {

        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        if(cursor != null) {
            //Move cursor to first cursor.moveToFirst()
            // using cursor.moveToNext() to go one step at a time till not the last one !cursor.isAfterLast().
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                //populate the student grade object.
                Appointment appointment = new Appointment();
                try {
                    //appointment.set(cursor.getInt(cursor.getColumnIndex(COL1)));
                    appointment.setFirstName(cursor.getString(cursor.getColumnIndex(APPT_COL3)));
                    appointment.setLastName(cursor.getString(cursor.getColumnIndex(APPT_COL4)));
                    appointment.setEmail(cursor.getString(cursor.getColumnIndex(APPT_COL5)));
                    appointment.setHospital(cursor.getString(cursor.getColumnIndex(APPT_COL6)));
                    appointment.setVaccine(cursor.getString(cursor.getColumnIndex(APPT_COL7)));
                    appointment.setAge(cursor.getInt(cursor.getColumnIndex(APPT_COL8)));

                    String slot = cursor.getString(cursor.getColumnIndex(APPT_COL9));

                    appointment.setSlot(new SimpleDateFormat("dd/MM/yyyy").parse(slot));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                appointments.add(appointment);
            }
        }
        return appointments;
    }
}