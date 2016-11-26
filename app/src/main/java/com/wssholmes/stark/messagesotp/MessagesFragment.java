package com.wssholmes.stark.messagesotp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wssholmes.stark.messagesotp.data.DataContract;

/**
 * Created by stark on 26/11/16.
 */

public class MessagesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MessagesFragment.class.getSimpleName();

    private static final int MESSAGES_LOADER_ID = 0;

    private static final String[] PROJECTIONS = new String[]{
            DataContract.SentMessageEntry.COLUMN_CONTACT_NAME,
            DataContract.SentMessageEntry.COLUMN_MESSAGE_SENT,
            DataContract.SentMessageEntry.COLUMN_TIME_SENT
    };

    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_MESSAGE = 1;
    public static final int COLUMN_TIME = 2;

    private static final String SORT_ORDER = DataContract.SentMessageEntry.COLUMN_TIME_SENT + " DESC";

    private RecyclerView mMessageList;
    private MessageListAdapter mMessageListAdpater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(MESSAGES_LOADER_ID, null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_list_fragment, container, false);
        mMessageList = (RecyclerView) rootView.findViewById(R.id.sent_message_list);
        mMessageList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMessageListAdpater = new MessageListAdapter(getActivity());

        mMessageList.setAdapter(mMessageListAdpater);


        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                DataContract.SentMessageEntry.CONTENT_URI,
                PROJECTIONS,
                null,
                null,
                SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.getCount() > 0){
            mMessageListAdpater.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
