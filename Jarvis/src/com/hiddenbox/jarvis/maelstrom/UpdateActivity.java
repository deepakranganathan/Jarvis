package com.hiddenbox.jarvis.maelstrom;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hiddenbox.jarvis.R;

public class UpdateActivity extends Activity {

    private ContentResolver mContentResolver;
    private final Uri mUri = buildUri("content", "com.maelstrom.provider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Bundle extras = getIntent().getExtras();
        final EditText etFName = (EditText) findViewById(R.id.et_fname);
        final EditText etLName = (EditText) findViewById(R.id.et_lname);
        final CheckBox cbPresence = (CheckBox) findViewById(R.id.cb_presence);
        if(extras != null) {
            String fName = extras.getString(MaelstromFragment.MAIN_FNAME);
            String lName = extras.getString(MaelstromFragment.MAIN_LNAME);
            String presence = extras.getString(MaelstromFragment.MAIN_STATUS);
            if( fName != null) {
                etFName.setText(fName);
            } else {
                etFName.setText("");
            }
            if( lName != null) {
                etLName.setText(lName);
            } else {
                etLName.setText("");
            }
            if( presence != null) {
                cbPresence.setChecked(presence.equals("1") ? true : false);
            }
        } else {
            etFName.setText("");
            etLName.setText("");
            getIntent().putExtra(MaelstromFragment.MAIN_ID, "-1");
        }
       
        findViewById(R.id.button_ok).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (etFName.getText().toString().equals("") && etLName.getText().toString().equals("")) {

                    } else {
                        mContentResolver = getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put(LocalDHTable.COLUMN_FIRST, etFName.getText().toString());
                        values.put(LocalDHTable.COLUMN_SECOND, etLName.getText().toString());
                        values.put(LocalDHTable.COLUMN_STATUS, cbPresence.isChecked() ? 1 : 0);
                        String _id = getIntent().getStringExtra(MaelstromFragment.MAIN_ID);
                        if(!_id.equals("-1")) {
                            values.put(LocalDHTable.COLUMN_ID, Integer.parseInt(_id));
                        }
                        
                        mContentResolver.insert(mUri, values);
                        finish();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.button_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        // uriBuilder.appendPath("dht");
        return uriBuilder.build();
    }
}