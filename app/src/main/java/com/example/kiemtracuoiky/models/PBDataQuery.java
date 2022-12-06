package com.example.kiemtracuoiky.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiemtracuoiky.utils.Utils;

import java.util.ArrayList;

public class PBDataQuery {
    public static long insert(Context context, PB pb) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_PB_NAME, pb.name);
        values.put(Utils.COLUMN_PB_CNID, pb.cnid);

        long rs = sqLiteDatabase.insert(Utils.TABLE_PB, null, values);
        return (rs);
    }

    public static ArrayList<PB> getAll(Context context, int cnid) {
        ArrayList<PB> lstUser = new ArrayList<>();
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase db = tpdbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + Utils.TABLE_PB+ " where cnid = " + cnid, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int Cnid =cs.getInt(2);
            lstUser.add(new PB(id, name,Cnid));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;
    }

    public static boolean delete(Context context, int id) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Utils.TABLE_PB, Utils.COLUMN_PB_ID +"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    public static int update(Context context, PB pb)
    {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_PB_NAME,pb.getName());
        values.put(Utils.COLUMN_PB_CNID,pb.getCnid());
        int rs = sqLiteDatabase.update(Utils.TABLE_PB,values,Utils.COLUMN_PB_ID+"=?",new String[]{String.valueOf(pb.id)});
        return (rs);
    }
}
