package com.thinkfoss.signupapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by tina on 2/10/15.
 */
public class DbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public static final String DB_NAME = "SignUpApp.db";
    public static final int DB_VERSION = 2;

    //table columns
    public static final String TABLE_NAME = "registration";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_PNAME = "name";
    public static final String COLUMN_NAME_DESIGN = "design";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_MOBL = "mobnum";
    public static final String COLUMN_NAME_CITY = "city";
    public static final String COLUMN_NAME_AREA = "interestedarea";
    public static final String COLUMN_NAME_COMMENTS = "comments";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_PNAME + " TEXT NOT NULL, " +
                COLUMN_NAME_DESIGN + " TEXT NOT NULL, " + COLUMN_NAME_EMAIL + " TEXT NOT NULL, " +
                COLUMN_NAME_MOBL + " TEXT NOT NULL, " + COLUMN_NAME_CITY + " TEXT NOT NULL, " +
                COLUMN_NAME_AREA + " TEXT, " + COLUMN_NAME_COMMENTS + " TEXT);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DbHelper(Context context ) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate ( SQLiteDatabase db ) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        if (newVersion > oldVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
