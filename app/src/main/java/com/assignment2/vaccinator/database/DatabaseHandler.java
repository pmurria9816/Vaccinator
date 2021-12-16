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

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler dbInstance;

    private static final String dbName = "Vaccinator";
    private static final int version = 1;

    //User Table
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COL1 = "userId";
    private static final String USER_COL2 = "email";
    private static final String USER_COL3 = "password";
    private static final String USER_COL4 = "firstName";
    private static final String USER_COL5 = "lastName";
    private static final String USER_COL6 = "phone";

    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "("
            + USER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COL2 + " TEXT NOT NULL, "
            + USER_COL3 + " TEXT NOT NULL, "
            + USER_COL4 + " TEXT NOT NULL, "
            + USER_COL5 + " TEXT NOT NULL, "
            + USER_COL6 + " TEXT NOT NULL" +
            ")" ;

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

    //Appointment Table
    private static final String APPT_TABLE_NAME = "appointment";
    private static final String APPT_COL1 = "appointmentId";
    private static final String APPT_COL2 = "user";
    private static final String APPT_COL3 = "firstName";
    private static final String APPT_COL4 = "lastName";
    private static final String APPT_COL5 = "email";
    private static final String APPT_COL6 = "hospital";
    private static final String APPT_COL7 = "vaccine";
    private static final String APPT_COL8 = "age";
    private static final String APPT_COL9 = "dateSlot";
    private static final String APPT_COL10 = "time";

    private static final String CREATE_APPT_TABLE = "CREATE TABLE IF NOT EXISTS " + APPT_TABLE_NAME + "("
            + APPT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + APPT_COL2 + " INTEGER NOT NULL REFERENCES user(userid) ON UPDATE CASCADE ON DELETE CASCADE, "
            + APPT_COL3 + " TEXT NOT NULL, "
            + APPT_COL4 + " TEXT NOT NULL, "
            + APPT_COL5 + " TEXT NOT NULL, "
            + APPT_COL6 + " TEXT NOT NULL, "
            + APPT_COL7 + " TEXT NOT NULL, "
            + APPT_COL8 + " INTEGER NOT NULL, "
            + APPT_COL9 + " TEXT NOT NULL, "
            + APPT_COL10 + " TEXT NOT NULL "
            + ")" ;

    private static final String DROP_APPT_TABLE = "DROP TABLE IF EXISTS " + APPT_TABLE_NAME;


    private DatabaseHandler(@Nullable Context context)
    {
        super(context, dbName, null, version);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return dbInstance;
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

    @SuppressLint("Range")
    public int authenticate(String login, String password){

        Cursor cursor = getUserByEmail(login);

        if(cursor.getCount() < 1)
            return -1;

        if (cursor.getString(1).compareTo(login) == 0 && cursor.getString(2).compareTo(password) == 0) {
            return cursor.getInt(cursor.getColumnIndex(USER_COL1));
        }

        return -1;
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
        values.put(APPT_COL10, data.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(APPT_TABLE_NAME, null, values);
        if(result == -1)
            return false;
        return true;
    }

    public boolean updateAppointment(Appointment data, int id){
        ContentValues values = new ContentValues();
        values.put(APPT_COL1, id);
        values.put(APPT_COL2, data.getUser());
        values.put(APPT_COL3, data.getFirstName());
        values.put(APPT_COL4, data.getLastName());
        values.put(APPT_COL5, data.getEmail());
        values.put(APPT_COL6, data.getHospital());
        values.put(APPT_COL7, data.getVaccine());
        values.put(APPT_COL8, data.getAge());
        values.put(APPT_COL9, data.getSlot());
        values.put(APPT_COL10, data.getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(APPT_TABLE_NAME, values, APPT_COL1 + " = " +id, null);
        if(result == -1)
            return false;
        return true;
    }

    public boolean deleteAppointment(int id){
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

        if (id == null)
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

                //appointment.set(cursor.getInt(cursor.getColumnIndex(COL1)));
                appointment.setFirstName(cursor.getString(cursor.getColumnIndex(APPT_COL3)));
                appointment.setLastName(cursor.getString(cursor.getColumnIndex(APPT_COL4)));
                appointment.setEmail(cursor.getString(cursor.getColumnIndex(APPT_COL5)));
                appointment.setHospital(cursor.getString(cursor.getColumnIndex(APPT_COL6)));
                appointment.setVaccine(cursor.getString(cursor.getColumnIndex(APPT_COL7)));
                appointment.setAge(cursor.getInt(cursor.getColumnIndex(APPT_COL8)));
                appointment.setSlot(cursor.getString(cursor.getColumnIndex(APPT_COL9)));
                appointment.setTime(cursor.getString(cursor.getColumnIndex(APPT_COL10)));
                appointments.add(appointment);
            }
        }

        return appointments;
    }
}