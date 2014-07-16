package com.hiddenbox.jarvis.maelstrom;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hiddenbox.jarvis.R;


public class MaelStromLVAdapter extends SimpleCursorAdapter {
    private final Context context;
    private final Cursor mCursor;
    private final LayoutInflater mInflater;
    private final Contact contact;
    
    @SuppressWarnings("deprecation")
    public MaelStromLVAdapter(Context context, int layout, Cursor mCursor, String[] from, int[] to) {
        super(context, layout, mCursor, from, to);
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mCursor = mCursor;
        contact = new Contact();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View v;
        if (convertView == null) {
            v = newView(context, mCursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, context, mCursor);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.textView1 = (TextView) view.findViewById(R.id.label);
            holder.imageView1 = (ImageView) view.findViewById(R.id.icon);
            view.setTag(holder);
        }
        
        contact.setFirstName(cursor.getString(cursor.getColumnIndex(LocalDHTable.COLUMN_FIRST)));
        contact.setLastName(cursor.getString(cursor.getColumnIndex(LocalDHTable.COLUMN_SECOND)));
        contact.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LocalDHTable.COLUMN_ID))));
        contact.setInOut(Integer.parseInt(cursor.getString(cursor.getColumnIndex(LocalDHTable.COLUMN_STATUS))) > 0 ? true : false);
        holder.textView1.setText(contact.getFirstName() + " " + contact.getLastName());
        if(contact.isInOut()) {
            holder.imageView1.setImageResource(R.drawable.on);
        } else {
            holder.imageView1.setImageResource(R.drawable.off);
        }
        
    }

    static class ViewHolder {
        TextView textView1;
        ImageView imageView1;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.rowlayout, parent, false);
        return view;
    }

}
