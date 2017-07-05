package com.example.providerdemo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ContentResolver resolver;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        Cursor cursor = resolver.query(
                Uri.parse("content://com.example.providerdemo/contact"), null, null, null, null);
        MyAdapter adapter = new MyAdapter(this, cursor);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) lv.getItemAtPosition(i);
        String number = cursor.getString(cursor.getColumnIndex("number"));
        String[] numbers = new String[]{number};
        resolver.delete(
                Uri.parse("content://com.example.providerdemo/contact"), "number=?", numbers);
    }

    private static class MyAdapter extends CursorAdapter {

        MyAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.item_contacts, null);
            if (cursor != null) {
                view.setTag(cursor.getInt(cursor.getColumnIndex("_id")));
            }
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView name = view.findViewById(R.id.tv_name);
            TextView num = view.findViewById(R.id.tv_num);
            if (cursor != null) {
                name.setText(cursor.getString(cursor.getColumnIndex("name")));
                num.setText(cursor.getString(cursor.getColumnIndex("number")));
            }
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }
}
