package com.example.providerdemo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 向其他应用程序提供了访问平台数据(联系人信息)的接口,
 * 其他应程序可以通过这些接口获取联系人的信息,
 * 也可以向这个数据库添加、更新或者删除联系人,当然前提是遵守该平台的一些规则
 * http://blog.csdn.net/hehe26/article/details/51784355
 * Created by chm00 on 2017/7/5.
 */

public class MyContentProvider extends ContentProvider {
    private final static int CONTACT = 1;

    /*
    Uri 代表了要操作的数据，所以我们经常需要解析Uri，并从 Uri 中获取数据。
    Android 系统提供了两个用于操作 Uri 的工具类，分别为 UriMatcher 和 ContentUris 。
    掌握它们的使用，会便于我们的开发工作。

    UriMatcher 类用于匹配Uri，它的用法如下：
    if(uriMatcher.match(uri) == CONTACT)

    首先第一步把你需要匹配Uri路径全部给注册上，如下：
    uriMatcher.addURI("com.example.providerdemo","contact",CONTACT);

    注册完需要匹配的Uri后，就可以使用 sMatcher.match(uri) 方法对输入的Uri进行匹配，
    如果匹配就返回匹配码，匹配码是调用 addURI() 方法传入的第三个参数。

    --------------------------------------------------------------------------------

    ContentUris类用于操作Uri路径后面的ID部分，它有个比较实用的方法：
    u = ContentUris.withAppendedId(uri,d);  // 用于为路径加上ID部分
    */
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("com.example.providerdemo", "contact", CONTACT);
    }

    private ContentResolver contentResolver;
    private MyDBHelp dbHelp;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        assert context != null;
        contentResolver = context.getContentResolver();
        dbHelp = new MyDBHelp(context, "contact.db", null, 1);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        if (uriMatcher.match(uri) == CONTACT) {
            SQLiteDatabase database = dbHelp.getReadableDatabase();
            cursor = database.query("contact", projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(contentResolver, uri);
        }
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri u = null;
        if (uriMatcher.match(uri) == CONTACT) {
            SQLiteDatabase database = dbHelp.getWritableDatabase();
            long d = database.insert("contact", "_id", values);
            u = ContentUris.withAppendedId(uri, d);
            contentResolver.notifyChange(u, null);
        }
        return u;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int id = 0;
        if (uriMatcher.match(uri) == CONTACT) {
            SQLiteDatabase database = dbHelp.getWritableDatabase();
            id = database.delete("contact", selection, selectionArgs);
            contentResolver.notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     */
    private static class MyDBHelp extends SQLiteOpenHelper {

        MyDBHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table contact(_id integer primary key autoincrement," +
                    "name text not null,number text not null);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }
}
