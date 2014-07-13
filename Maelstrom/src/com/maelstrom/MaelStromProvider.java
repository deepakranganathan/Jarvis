package com.maelstrom;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MaelStromProvider extends ContentProvider {
    private MaelStromDBHelper database;

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        database.deleteContact(Integer.parseInt(arg1));
        return 0;
    }

    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        // TODO Auto-generated method stub
        database.createContact(arg1);
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        database = new MaelStromDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
        // TODO Auto-generated method stub
        return database.getAllContact();
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        database.createContact(arg1);
        return 0;
    }

}
