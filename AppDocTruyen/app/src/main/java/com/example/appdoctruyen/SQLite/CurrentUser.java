package com.example.appdoctruyen.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appdoctruyen.model.User;

public class CurrentUser extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "currentUser.db";
    private static int DATABASE_VERSION = 1;
    private User user;

    public CurrentUser(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        SQLiteDatabase st = getReadableDatabase();

        String[] selectionArgs = {"1"};
        Cursor rs = st.query("user", null,"id =?",selectionArgs,null,null,null);
        while(rs!=null && rs.moveToNext()){
            user = new User();
            user.setUsername(rs.getString(1));
            user.setAdmin(rs.getInt(2));
            user.setEmail(rs.getString(3));
            user.setToken(rs.getString(4));
        }
        st.close();
    }

    public CurrentUser(@Nullable Context context, User user){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        SQLiteDatabase st=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",user.getUsername());
        values.put("admin",user.getUsername());
        values.put("email", user.getEmail());
        values.put("token", user.getToken());

        String where = "id =?";
        String[] args = {"1"};
        st.update("user",values,where,args);
        st.close();
        this.user = user;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="CREATE TABLE if NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY ," +
                "username TEXT, admin INTEGER,email TEXT,token TEXT)";
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO user(username,admin,email,token) VALUES (?,?,?,?)";
        String[] args = {"username", "0", "email@email.com", "tokeakfj"};
        sqLiteDatabase.execSQL(sql,args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public User getCurrentUser(){
        return user;
    }
}
