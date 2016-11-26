package com.wssholmes.stark.messagesotp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by stark on 26/11/16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListAdapterViewHolder> {
    private static final String LOG_TAG = ContactListAdapter.class.getSimpleName();

    private Cursor mCursor;
    private final Context mContext;

    public ContactListAdapter(Context context){
        mContext = context;
    }

    @Override
    public ContactListAdapter.ContactListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);
            view.setFocusable(true);
            return new ContactListAdapterViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to Recycler View");
        }
    }

    @Override
    public void onBindViewHolder(ContactListAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        if(mCursor != null){
            holder.mContactName.setText(mCursor.getString(ContactsFragment.COLUMN_NAME));
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

    public class ContactListAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView mContactName;
        private TextView mContactEmail;
        private ImageView mContactImage;
        private TextView mContactLastContact;

        public ContactListAdapterViewHolder(View itemView) {
            super(itemView);

            mContactName = (TextView) itemView.findViewById(R.id.contact_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ContactDetailsActivity.class);
                    if(mCursor != null){
                        mCursor.moveToPosition(getAdapterPosition());
                        intent.putExtra(ContactDetailsActivity.INTENT_CONTACT_LOOKUP_KEY,
                                mCursor.getString(ContactsFragment.COLUMN_LOOKUP_KEY));
                        intent.putExtra(ContactDetailsActivity.INTENT_CONTANT_NAME_KEY,
                                mCursor.getString(ContactsFragment.COLUMN_NAME));

                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
