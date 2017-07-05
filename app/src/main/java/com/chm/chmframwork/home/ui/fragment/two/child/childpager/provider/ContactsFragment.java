package com.chm.chmframwork.home.ui.fragment.two.child.childpager.provider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.base.BaseBackFragment;

/**
 * Created by chm00 on 2017/7/5.
 */

public class ContactsFragment extends BaseBackFragment {
    private ContentResolver resolver;
    private MyAdapter myAdapter;
    private Cursor cursor;

    public static ContactsFragment newInstance() {
        Bundle args = new Bundle();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initToolbar(view);
        initView(view);
        return view;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("通讯录");
        initToolbarNav(toolbar);
    }

    private void initView(View view) {
        ListView lv = (ListView) view.findViewById(R.id.lv);
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setText("添加联系人");
        resolver = _mActivity.getContentResolver();
        cursor = resolver.query(Uri.parse("content://com.example.providerdemo/contact"), null, null, null, null);
        myAdapter = new MyAdapter(_mActivity, cursor);
        lv.setAdapter(myAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AddContactsFragment.newInstance());
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resolver.delete(Uri.parse("content://com.example.providerdemo/contact"), null, null);
            }
        });
        resolver.registerContentObserver(Uri.parse("content://com.example.providerdemo/contact"), true,
                new MyContentObserver(new Handler()));
    }

    private class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            myAdapter.notifyDataSetChanged();
        }
    }

    private static class MyAdapter extends CursorAdapter {
        private Cursor cursor;
        private Context context;

        MyAdapter(Context context, Cursor c) {
            super(context, c);
            this.context = context;
            this.cursor = c;
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
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView num = (TextView) view.findViewById(R.id.tv_num);
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
