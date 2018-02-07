package com.example.user.android_assignment_11_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DbHelper extends SQLiteOpenHelper {
    //public is a method and fields can be accessed by the members of any class.
    //class is a collections of objects.
    //created MainActivity and extends with SQLiteOpenHelper.

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "firstname";
    public static final String COL_3 = "lastname";


    public DbHelper(Context context) {
        //Context allows access to application-specific resources and classes, as well as calls for application-level operations
        // such as launching activities, broadcasting and receiving intents, etc.
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    public void onCreate(SQLiteDatabase db) {
        //Called when the database is created for the first time. This is where the creation of tables and the initial population of the tables should happen.
        // Parameters
        // db	SQLiteDatabase: The database.
        //execute the sql
        db.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+ " TEXT, " + COL_3 + " TEXT)");
    }

    @Override
    //we use override to tells the compiler that the following method overrides a method of its superclass.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables, or do
        // anything else it needs to// upgrade to the new schema version.
        //db	SQLiteDatabase: The database.
        //  oldVersion	int: The old database version.
        // newVersion	int: The new database version.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String firstName, String lastName) {
        //inserting the data
        //here we can write thedatabase
        SQLiteDatabase db = this.getWritableDatabase();
        //Context allows access to application-specific resources and classes, as well as calls for application-level operations
        // such as launching activities, broadcasting and receiving intents, etc.
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        //putting 2nd column as firstname
        contentValues.put(COL_3, lastName);
        //putting 3rd column as lastname
//inserting the data
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String firstname, String lastname) {
        //updating the data
        SQLiteDatabase db = this.getWritableDatabase();
        //giving as eritable the data
        ContentValues contentValues = new ContentValues();
        //creating object
        contentValues.put(COL_1, id);
        //setting 1st col as id
        contentValues.put(COL_2,firstname );
        //setting 2nd col as firstname
        contentValues.put(COL_3, lastname);
        //setting 3rd col as lastname
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        //deleteData() from the database
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + " = ?", new String[]{id});
    }
}
