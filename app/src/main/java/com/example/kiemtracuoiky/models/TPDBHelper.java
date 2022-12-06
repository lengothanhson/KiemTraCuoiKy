package com.example.kiemtracuoiky.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.kiemtracuoiky.utils.Utils;

public class TPDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = Utils.DATABASE_NAME;
    private static final int DATABASE_VERSION = 3;

    public TPDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TP_TABLE = "CREATE TABLE " + Utils.TABLE_TP + "("
                + Utils.COLUMN_TP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Utils.COLUMN_TP_NAME + " TEXT "
                + ")";
        sqLiteDatabase.execSQL(CREATE_TP_TABLE);

        String CREATE_CN_TABLE = "CREATE TABLE " + Utils.TABLE_CN + "("
                + Utils.COLUMN_CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Utils.COLUMN_CN_NAME + " TEXT,"
                + Utils.COLUMN_CN_TPID + " INTEGER," +
                "   FOREIGN KEY(tpid) REFERENCES tp(id)"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CN_TABLE);

        String CREATE_PB_TABLE = "CREATE TABLE " + Utils.TABLE_PB + "("
                + Utils.COLUMN_PB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Utils.COLUMN_PB_NAME + " TEXT, "
                + Utils.COLUMN_PB_CNID + " INTEGER," +
                "   FOREIGN KEY(cnid) REFERENCES cn(id)"
                + ")";
        sqLiteDatabase.execSQL(CREATE_PB_TABLE);

        String CREATE_NV_TABLE = "CREATE TABLE " + Utils.TABLE_NV + "("
                + Utils.COLUMN_NV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Utils.COLUMN_NV_NAME + " TEXT, "
                + Utils.COLUMN_NV_SDT + " TEXT, "
                + Utils.COLUMN_NV_MAIL + " TEXT, "
                + Utils.COLUMN_NV_PBID + " INTEGER," +
                "   FOREIGN KEY(pbid) REFERENCES pb(id)"
                + ")";
        sqLiteDatabase.execSQL(CREATE_NV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_TP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_CN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NV);
        onCreate(sqLiteDatabase);
    }
}
