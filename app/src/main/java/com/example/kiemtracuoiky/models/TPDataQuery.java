package com.example.kiemtracuoiky.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiemtracuoiky.utils.Utils;

import java.util.ArrayList;

public class TPDataQuery {
    public static long insert(Context context, TP tp) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_TP_NAME, tp.name);
        //values.put(Utils.COLUMN_USER_AVATAR, us.avatar);
        long rs = sqLiteDatabase.insert(Utils.TABLE_TP, null, values);
        return (rs);
    }

    public static ArrayList<TP> getAll(Context context) {
        ArrayList<TP> lstUser = new ArrayList<>();
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase db = tpdbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + Utils.TABLE_TP, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            lstUser.add(new TP(id, name));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;
    }

    public static boolean delete(Context context, int id) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Utils.TABLE_TP, Utils.COLUMN_TP_ID +"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    public static int update(Context context, TP tp)
    {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_TP_NAME,tp.getName());
        //values.put(Utils.COLUMN_USER_AVATAR,us.getAvatar());
        int rs = sqLiteDatabase.update(Utils.TABLE_TP,values,Utils.COLUMN_TP_ID+"=?",new String[]{String.valueOf(tp.id)});
        return (rs);
    }
}
