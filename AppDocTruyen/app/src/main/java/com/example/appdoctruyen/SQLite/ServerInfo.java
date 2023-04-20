package com.example.appdoctruyen.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ServerInfo extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "serverInfo.db";
    private static int DATABASE_VERSION = 1;

    private String serverIP;
    private int user, content, payment;

    public ServerInfo(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        SQLiteDatabase st=getReadableDatabase();

        String[] selectionArgs = {"1"};
        Cursor rs = st.query("server", null,"id =?",selectionArgs,null,null,null);
        while(rs!=null && rs.moveToNext()){
            serverIP = rs.getString(1);
            user = rs.getInt(2);
            content = rs.getInt(3);
            payment = rs.getInt(4);
        }
        st.close();

    }

    public ServerInfo(@Nullable Context context, String ip, String user, String content, String payment){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        SQLiteDatabase st=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("ip",ip);
        values.put("user",user);
        values.put("content",content);
        values.put("payment",payment);
        String where = "id =?";
        String[] args = {"1"};
        st.update("server",values,where,args);
        st.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE if NOT EXISTS server(" +
                "id INTEGER PRIMARY KEY ," +
                "ip TEXT, user TEXT,content TEXT,payment TEXT)";
        db.execSQL(sql);

        sql = "INSERT INTO server(ip,user,content,payment) VALUES (?,?,?,?)";
        String[] args = {"ip", "1", "2", "3"};
        db.execSQL(sql,args);
    }


    public String getServerUrl(){
        return "http://"+serverIP+":";
    }

    public String getUserServiceUrl(){
        return getServerUrl()+user+"/";
    }

    public String getContentServiceUrl(){
        return getServerUrl()+content+"/";
    }

    public String getPaymentServiceUrl(){
        return getServerUrl()+payment+"/";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
