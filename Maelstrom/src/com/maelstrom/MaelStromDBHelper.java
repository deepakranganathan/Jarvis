
package com.maelstrom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaelStromDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "maelstrom.db";
    private static final int DATABASE_VERSION = 1;

    public MaelStromDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        LocalDHTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
        LocalDHTable.onUpgrade(database, oldVersion, newVersion);
    }
    
    public void createContact(ContentValues contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        long _id = db.replace(LocalDHTable.TABLE_MAELSTROM, null, contact);
        if (_id > 0) {
        } else {
            Log.e("Maelstrom", "Insertion failed!!");
        }
        db.close();
    }
    
    public Cursor getAllContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStr = "select * from " + LocalDHTable.TABLE_MAELSTROM;
        Cursor cursor = db.rawQuery(queryStr, null);
        return cursor;
    }
    
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStr = "select * from " + LocalDHTable.TABLE_MAELSTROM +
                "where _id = " + id;
        Cursor cursor = db.rawQuery(queryStr, null);
        Contact retContact = null;
        if (cursor != null) {
            cursor.moveToFirst();
            retContact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    (cursor.getInt(3) > 0) ? true : false);
        } else {
            retContact = new Contact();
        }
        return retContact;
    }
    
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LocalDHTable.TABLE_MAELSTROM,
                LocalDHTable.COLUMN_ID + " = " + id, null);
        db.close();
    }
}
