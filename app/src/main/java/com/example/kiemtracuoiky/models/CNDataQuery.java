package com.example.kiemtracuoiky.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiemtracuoiky.utils.Utils;

import java.util.ArrayList;

public class CNDataQuery {
    public static long insert(Context context, CN cn) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_CN_NAME, cn.name);
        values.put(Utils.COLUMN_CN_TPID, cn.tpid);
        //values.put(Utils.COLUMN_USER_AVATAR, us.avatar);
        long rs = sqLiteDatabase.insert(Utils.TABLE_CN, null, values);
        return (rs);
    }

    public static ArrayList<CN> getAll(Context context, int tpid) {
        ArrayList<CN> lstUser = new ArrayList<>();
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase db = tpdbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + Utils.TABLE_CN  + " where tpid = " + tpid, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int Tpid = cs.getInt(2);
            lstUser.add(new CN(id, name,Tpid));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;
    }

    public static boolean delete(Context context, int id) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Utils.TABLE_CN, Utils.COLUMN_CN_ID +"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    public static int update(Context context, CN cn)
    {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_CN_NAME,cn.getName());
        values.put(Utils.COLUMN_CN_TPID,cn.getTpid());
        //values.put(Utils.COLUMN_USER_AVATAR,us.getAvatar());
        int rs = sqLiteDatabase.update(Utils.TABLE_CN,values,
                Utils.COLUMN_CN_ID+"=?",new String[]{String.valueOf(cn.id)});
        return (rs);
    }
}
