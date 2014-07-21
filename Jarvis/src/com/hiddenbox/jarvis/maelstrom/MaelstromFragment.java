package com.hiddenbox.jarvis.maelstrom;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hiddenbox.jarvis.R;

public class MaelstromFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "jarvis_section";
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
    
    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static MaelstromFragment newInstance(int sectionNumber) {
        MaelstromFragment fragment = new MaelstromFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MaelstromFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maelstrom, container, false);
        mContentResolver = getActivity().getContentResolver();
        resultCursor = mContentResolver.query(mUri, null, null, null, null);
        // The desired columns to be bound
        String[] columns = new String[] {
                LocalDHTable.COLUMN_FIRST
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.label
        };
        
        dataAdapter = new MaelStromLVAdapter(getActivity(), R.layout.rowlayout, resultCursor, columns, to);
        ListView listView = (ListView) (getActivity().findViewById(R.id.listView1));
        // Assign adapter to ListView
//        listView.setAdapter(dataAdapter);
//        registerForContextMenu(listView);

        getActivity().findViewById(R.id.add).setOnClickListener(new OnClickListener() {

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
        return rootView;
    }

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        // uriBuilder.appendPath("dht");
        return uriBuilder.build();
    }
    
    private void editContact() {
        Intent i = new Intent(getActivity(), UpdateActivity.class);
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
        Intent i = new Intent(getActivity(), UpdateActivity.class);
        startActivity(i);
    }
    
    private AlertDialog confirmDelete() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
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
