package com.hiddenbox.jarvis.maelstrom;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocalDHTable {

    // Database table
    public static final String TABLE_MAELSTROM  = "maelstrom_table";
    public static final String COLUMN_ID        = "_id";
    public static final String COLUMN_FIRST     = "FirstName";
    public static final String COLUMN_SECOND    = "SecondName";
    public static final String COLUMN_STATUS    = "Status";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MAELSTROM
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_FIRST + " TEXT, "
            + COLUMN_SECOND + " TEXT, "
            + COLUMN_STATUS + " INTEGER"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
        Log.w(LocalDHTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MAELSTROM);
        onCreate(database);
    }

}
