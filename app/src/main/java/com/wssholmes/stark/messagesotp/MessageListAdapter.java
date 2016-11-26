package com.wssholmes.stark.messagesotp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stark on 26/11/16.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListAdapterViewHolder> {
    private static final String LOG_TAG = MessageListAdapter.class.getSimpleName();

    private Cursor mCursor;
    private final Context mContext;

    public MessageListAdapter(Context context){
        mContext = context;
    }

    @Override
    public MessageListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
            view.setFocusable(true);
            return new MessageListAdapter.MessageListAdapterViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to Recycler View");
        }
    }

    @Override
    public void onBindViewHolder(MessageListAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        if(mCursor != null){
            holder.mContactName.setText(mCursor.getString(MessagesFragment.COLUMN_NAME));
            holder.mSentMessage.setText(mCursor.getString(MessagesFragment.COLUMN_MESSAGE));
            holder.mSentDate.setText(getDate(mCursor.getLong(MessagesFragment.COLUMN_TIME)));
        }

    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0: mCursor.getCount();
    }

    public void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class MessageListAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mContactName;
        private TextView mSentMessage;
        private TextView mSentDate;

        public MessageListAdapterViewHolder(View itemView) {
            super(itemView);
            mContactName = (TextView) itemView.findViewById(R.id.contact_name);
            mSentMessage = (TextView) itemView.findViewById(R.id.message_text);
            mSentDate = (TextView) itemView.findViewById(R.id.message_time);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String sentTime = DateFormat.format("HH:mm", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return sentTime + "," + date;
    }
}
