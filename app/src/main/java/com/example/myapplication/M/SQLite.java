/*
package com.example.myapplication.M;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class SQLite {
    private static String path = "/data/remember.db";
    static SQLiteDatabase db = openOrCreateDatabase(path, null);

    // 本地数据库有缓存则返回true
    public static boolean local() {
        //查询user表是否创建
        Cursor cursor=db.query("sqlite_master",null,"type=\"table\" && name=\"user\"",null,null,null,null);

        //第一次使用APP，创建user表
        if(cursor.moveToNext()){
        db.execSQL(
                "create table user(" +
                "name varchar(20)," +
                "email varchar(20) primary key," +
                "password varchar(20)," +
                "VIP boolean," +
                "manager boolean)");
        cursor.close();
        return false;
        } else {
            Cursor cursor1=db.query("user",null,null,null,null,null,null);
            if(cursor1.moveToNext()){
                return true;
            }
            // 有user表但是没有数据
            return false;
        }
    }

    // 记住当前用户
    public static void remember(List<UserBean> list) {
        // 插入本地数据库
        db.execSQL("insert into \"user\" values("+list.get(0).getName()+","+
                list.get(0).getEmail()+","+
                list.get(0).getPassword()+","+
                list.get(0).getVIP()+","+
                list.get(0).getManager()+")"
                );
    }

    // 注销
    public static void forget(){
        db.delete("user","true",null);
    }
}
*/
