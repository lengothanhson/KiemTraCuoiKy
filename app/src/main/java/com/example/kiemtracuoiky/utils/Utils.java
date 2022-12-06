package com.example.kiemtracuoiky.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static final String DATABASE_NAME = "db-tp";

    public static final String TABLE_TP = "tp";
    public static final String COLUMN_TP_ID = "id";
    public static final String COLUMN_TP_NAME = "name";

    public static final String TABLE_CN = "cn";
    public static final String COLUMN_CN_ID = "id";
    public static final String COLUMN_CN_NAME = "name";
    public static final String COLUMN_CN_TPID = "tpid";

    public static final String TABLE_PB = "pb";
    public static final String COLUMN_PB_ID = "id";
    public static final String COLUMN_PB_NAME = "name";
    public static final String COLUMN_PB_CNID = "cnid";

    public static final String TABLE_NV = "nv";
    public static final String COLUMN_NV_ID = "id";
    public static final String COLUMN_NV_NAME = "name";
    public static final String COLUMN_NV_SDT = "sdt";
    public static final String COLUMN_NV_MAIL = "mail";
    public static final String COLUMN_NV_PBID = "pbid";


    public static Bitmap convertToBitmapFromAssets(Context context, String nameImage)
    {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("Images/"+nameImage);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
