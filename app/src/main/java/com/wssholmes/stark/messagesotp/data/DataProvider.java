package com.wssholmes.stark.messagesotp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by stark on 26/11/16.
 */

public class DataProvider extends ContentProvider {
    private static final String LOG_TAG = DataProvider.class.getSimpleName();

    private DbHelper mDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int SENT_MESSAGE = 100;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_MESSAGES, SENT_MESSAGE);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case SENT_MESSAGE:
            {
                cursor = mDbHelper.getReadableDatabase().query(DataContract.SentMessageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case SENT_MESSAGE:
                return DataContract.SentMessageEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match  = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case SENT_MESSAGE:
            {
                long _id = database.insert(DataContract.SentMessageEntry.TABLE_NAME,null,contentValues);
                if(_id > 0){
                    returnUri = DataContract.SentMessageEntry.buildMessageUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
