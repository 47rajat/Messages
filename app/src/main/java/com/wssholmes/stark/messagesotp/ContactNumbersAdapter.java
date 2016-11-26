package com.wssholmes.stark.messagesotp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by stark on 26/11/16.
 */

public class ContactNumbersAdapter extends
        RecyclerView.Adapter<ContactNumbersAdapter.ContactNumberAdapterViewHolder> {
    private static final String LOG_TAG = ContactNumbersAdapter.class.getSimpleName();

    private Cursor mCursor;
    private final Context mContext;

    public ContactNumbersAdapter(Context context){
        mContext = context;
    }

    @Override
    public ContactNumberAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list_item, parent, false);
            view.setFocusable(true);
            return new ContactNumbersAdapter.ContactNumberAdapterViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to Recycler View");
        }
    }

    @Override
    public void onBindViewHolder(ContactNumberAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        if(mCursor != null){
            holder.mContactNumber.setText(mCursor.getString(ContactDetailsActivity.COLUMN_NUMBER));
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

    public class ContactNumberAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mContactNumber;

        public ContactNumberAdapterViewHolder(View itemView) {
            super(itemView);
            mContactNumber = (TextView) itemView.findViewById(R.id.contact_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SendMessageActivity.class);
                    mCursor.moveToPosition(getAdapterPosition());
                    if(mCursor != null){
                        intent.putExtra(SendMessageActivity.INTENT_MOBILE_NUMBER_KEY,
                                mCursor.getString(ContactDetailsActivity.COLUMN_NUMBER));
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
