package com.wssholmes.stark.messagesotp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stark on 26/11/16.
 */

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = ContactsFragment.class.getSimpleName();

    private static final int CONTACTS_PERMISSION_ID = 0;
    private static final int CONTACTS_LOADER_ID = 100;

    private static final String[] PROJECTIONS = new String[]{
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };

    public static final int COLUMN_LOOKUP_KEY = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_THUMBNAIL = 2;

    private static final String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1";

    private static final String SORT_ORDER = ContactsContract.Contacts.DISPLAY_NAME + " ASC ";

    private RecyclerView mContactListView;
    private ContactListAdapter mContactListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkContactPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_list_fragment, container, false);
        mContactListView = (RecyclerView) rootView.findViewById(R.id.contacts_list);
        mContactListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactListAdapter = new ContactListAdapter(getActivity());

        mContactListView.setAdapter(mContactListAdapter);

        return rootView;
    }

    private void checkContactPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                    CONTACTS_PERMISSION_ID);
        } else {
            getLoaderManager().initLoader(CONTACTS_LOADER_ID, null, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CONTACTS_PERMISSION_ID:
                if(grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    getLoaderManager().initLoader(CONTACTS_LOADER_ID, null, this);
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.contacts_tab_layout),
                            getString(R.string.contact_permission_denied_message),
                            Snackbar.LENGTH_INDEFINITE).show();
                }
                break;
            default:
                Log.v(LOG_TAG, "Unknown permission requested");
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTIONS,
                SELECTION,
                null,
                SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0){
            Log.v(LOG_TAG, "Total number of contacts: " + data.getCount());
            mContactListAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
