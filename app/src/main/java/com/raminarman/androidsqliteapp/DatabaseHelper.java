package com.raminarman.androidsqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db = this.getWritableDatabase();

    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";

    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_SURNAME = "SURNAME";
    public static final String COL_MARKS = "MARKS";

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " + COL_SURNAME + " TEXT, " + COL_MARKS + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNewStudent(Student student) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, student.getName());
        contentValues.put(COL_SURNAME, student.getSurname());
        contentValues.put(COL_MARKS, student.getMarks());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllStudents() {

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateStudent(String id, Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_NAME, student.getName());
        contentValues.put(COL_SURNAME, student.getSurname());
        contentValues.put(COL_MARKS, student.getMarks());

        int affected = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return affected > 0;
    }

    public  boolean deleteStudent(String id) {
        int res = db.delete(TABLE_NAME, "id = ?", new String[]{id});
        return res > 0;
    }

    public Student getStudent(String id) {
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id, null);
        if (res.getCount() > 0) {
            res.moveToNext();
            Student student = new Student(res.getString(1), res.getString(2), res.getString(3));
            return student;
        }
        return null;
    }
}
