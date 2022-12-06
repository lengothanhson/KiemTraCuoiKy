package com.example.kiemtracuoiky.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kiemtracuoiky.utils.Utils;

import java.util.ArrayList;

public class NVDataQuery {
    public static long insert(Context context, NV nv) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_NV_NAME, nv.name);
        values.put(Utils.COLUMN_NV_PBID, nv.pbid);
        values.put(Utils.COLUMN_NV_SDT, nv.sdt);
        values.put(Utils.COLUMN_NV_MAIL, nv.mail);
        long rs = sqLiteDatabase.insert(Utils.TABLE_NV, null, values);
        return (rs);
    }

    public static ArrayList<NV> getAll(Context context, int pbid) {
        ArrayList<NV> lstUser = new ArrayList<>();
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase db = tpdbHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + Utils.TABLE_NV+ " where pbid = " + pbid, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String sdt = cs.getString(2);
            String mail = cs.getString(3);
            int Pbid = cs.getInt(4);
            lstUser.add(new NV(id, Pbid, name, sdt, mail ));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;
    }

    public static boolean delete(Context context, int id) {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Utils.TABLE_NV, Utils.COLUMN_NV_ID +"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    public static int update(Context context, NV nv)
    {
        TPDBHelper tpdbHelper = new TPDBHelper(context);
        SQLiteDatabase sqLiteDatabase = tpdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_NV_NAME,nv.getName());
        values.put(Utils.COLUMN_NV_PBID,nv.getPbid());
        values.put(Utils.COLUMN_NV_SDT,nv.getSdt());
        values.put(Utils.COLUMN_NV_MAIL,nv.getMail());
        int rs = sqLiteDatabase.update(Utils.TABLE_NV,values,Utils.COLUMN_NV_ID+"=?",new String[]{String.valueOf(nv.id)});
        return (rs);
    }
}
