package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "due";
    public static final String table_name = "users";
    public static final String col_1 = "u_id";
    public static final String col_2 = "u_name";
    public static final String col_3 = "u_pass";
    public static final String col_4 = "u_fine";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (u_id integer primary key autoincrement," + "u_name text," + "u_pass text," + "u_fine integer);");
        db.execSQL("CREATE TABLE books (b_id INTEGER PRIMARY KEY AUTOINCREMENT, b_name TEXT, b_author TEXT,ddate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
    public void addusers(SQLiteDatabase db,String name,String pass)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,name);
        contentValues.put(col_3,pass);
        contentValues.put(col_4,"0");
        db.insert("users",null,contentValues);

    }

    public static int  validatelogin(SQLiteDatabase db, String name, String pass)
    {
        //Cursor cursor = db.query("users",new String[]{"u_name","u_password"},"u_name = ? and u_pass = ?",null,null,null,null);
        Cursor cursor = db.rawQuery("SELECT *FROM " + table_name + " WHERE " + col_2 + "=? AND " + col_3 + "=?", new String[]{name,pass});
        if (cursor != null)
        {
            int count = cursor.getCount();
            cursor.close();
            db.close();
            if(count >0)
                return count;
        }
        return 10;
    }
    public long addBook(SQLiteDatabase db, String title, String author, String current_date){
        ContentValues cv = new ContentValues();
        cv.put("b_name",title);
        cv.put("b_author",author);
        cv.put("ddate",current_date);
        long res = db.insert("books", null, cv);
        return res;

    }
    public Cursor readData(String name){
        String query = "SELECT * FROM books";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}
