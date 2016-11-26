package com.wssholmes.stark.messagesotp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stark on 26/11/16.
 */

public class DbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "messagesOTP.db";

    public DbHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SENT_MESSAGE_TABLE = "CREATE TABLE " + DataContract.SentMessageEntry.TABLE_NAME + " ("+
                DataContract.SentMessageEntry._ID + " INTEGER PRIMARY KEY, " +
                DataContract.SentMessageEntry.COLUMN_CONTACT_NAME + " TEXT NOT NULL, "+
                DataContract.SentMessageEntry.COLUMN_MESSAGE_SENT + " TEXT NOT NULL, " +
                DataContract.SentMessageEntry.COLUMN_TIME_SENT + " INTEGER NOT NULL "+
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_SENT_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.SentMessageEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
