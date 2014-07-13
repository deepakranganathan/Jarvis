
package com.maelstrom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ContentResolver mContentResolver;
    private final Uri mUri = buildUri("content", "com.maelstrom.provider");
    private ContentValues mContentValues;
    private MaelStromLVAdapter dataAdapter;
    private Cursor resultCursor;
    private static final int INSERT_ID = 1;
    private static final int DELETE_ID = 2;
    private static final int UPDATE_ID = 3;
    protected static final String MAIN_FNAME = "FirstName";
    protected static final String MAIN_LNAME = "LastName";
    protected static final String MAIN_STATUS = "Status";
    protected static final String MAIN_ID = "_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContentResolver = getContentResolver();
        resultCursor = mContentResolver.query(mUri, null,
                null, null, null);
        // The desired columns to be bound
        String[] columns = new String[] {
                LocalDHTable.COLUMN_FIRST
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.label
        };
        
        dataAdapter = new MaelStromLVAdapter(this, R.layout.rowlayout, resultCursor, columns, to);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        registerForContextMenu(listView);

        findViewById(R.id.add).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    createContact();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView1) {
            menu.setHeaderTitle("Menu");
            menu.add(0, UPDATE_ID, 1, R.string.menu_edit);
            menu.add(0, DELETE_ID, 2, R.string.menu_delete);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                deleteContact();
                break;
            case UPDATE_ID:
                editContact();
                break;
        }
        return true;
    }
    
    @Override
    public void onDestroy() {
      super.onDestroy();
      resultCursor.close();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Fix refresh issue
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, INSERT_ID, 1, R.string.menu_add);
        menu.add(0, DELETE_ID, 2, R.string.menu_delete);
        return true;
    }

    /*
     * @Override public boolean onMenuItemSelected(int featureId, MenuItem item)
     * { // TODO Auto-generated method stub super.onMenuItemSelected(featureId,
     * item); switch (item.getItemId()) { case INSERT_ID: createContact();
     * break; case DELETE_ID: break; case UPDATE_ID: editContact(); break; }
     * return true; }
     */

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        // uriBuilder.appendPath("dht");
        return uriBuilder.build();
    }

    private void editContact() {
        Intent i = new Intent(this, UpdateActivity.class);
        i.putExtra(MAIN_FNAME, resultCursor.getString(resultCursor.getColumnIndex(LocalDHTable.COLUMN_FIRST)));
        i.putExtra(MAIN_LNAME, resultCursor.getString(resultCursor.getColumnIndex(LocalDHTable.COLUMN_SECOND)));
        i.putExtra(MAIN_STATUS, String.valueOf(resultCursor.getInt(resultCursor.getColumnIndex(LocalDHTable.COLUMN_STATUS))));
        i.putExtra(MAIN_ID, String.valueOf(resultCursor.getInt(resultCursor.getColumnIndex(LocalDHTable.COLUMN_ID))));
        startActivity(i);
    }
    private void deleteContact() {
        AlertDialog confirmDel = confirmDelete();
        confirmDel.show();
    }
    private void createContact() {
        Intent i = new Intent(this, UpdateActivity.class);
        startActivity(i);
    }
    
    private AlertDialog confirmDelete() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Confirm Delete")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        // your deleting code
                        String where = String.valueOf(resultCursor.getInt(resultCursor.getColumnIndex(LocalDHTable.COLUMN_ID)));
                        mContentResolver.delete(mUri, where, null);
                        dataAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}
