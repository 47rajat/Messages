package com.wssholmes.stark.messagesotp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by stark on 26/11/16.
 */

public class DataContract {
    //Content authority for the app
    public static final String CONTENT_AUTHORITY = "com.wssholmes.stark.messagesotp";

    //The base of all URI the app will use to contact the Content Provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible path appended to the base uri
    public static final String PATH_MESSAGES = "sent_message";

    //Inner class that defines the table content of sent_message table
    public static final class SentMessageEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_MESSAGES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGES;

        //Table name
        public static final String TABLE_NAME = "stored_messages";

        //Name of the contact
        public static final String COLUMN_CONTACT_NAME = "name";

        //Message sent to the contact
        public static final String COLUMN_MESSAGE_SENT = "message";

        //Time at which message was sent
        public static final String COLUMN_TIME_SENT = "time";

        public static Uri buildMessageUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
