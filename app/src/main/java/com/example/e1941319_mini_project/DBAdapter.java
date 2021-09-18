package com.example.e1941319_mini_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.e1941319_mini_project.dto.LoginDTO;
import com.example.e1941319_mini_project.dto.LoginResponseDTO;
import com.example.e1941319_mini_project.models.UserType;

public class DBAdapter extends SQLiteOpenHelper {
    public DBAdapter(@Nullable Context context) {
        super(context, "package_tracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Start - DB");
        String createTableStatement = "CREATE TABLE USER_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT, PASSWORD TEXT, USER_TYPE TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS USER_TABLE");
        onCreate(db);
    }

    public Boolean addUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("USER_NAME", "admin");
        contentValues.put("PASSWORD", "admin");
        contentValues.put("USER_TYPE", UserType.USER.toString());

        long res = db.insert("USER_TABLE", null, contentValues);

        return res != -1;
    }

    public LoginResponseDTO checkUsernameAndPassword(LoginDTO user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT USER_TYPE FROM USER_TABLE WHERE USER_NAME=? AND PASSWORD=?", new String[]{user.getUsername(), user.getPassword()});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                return new LoginResponseDTO(UserType.valueOf(cursor.getString(0)), true);
            }
        }

        return new LoginResponseDTO(null, false);
    }
}
