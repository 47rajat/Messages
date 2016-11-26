package com.wssholmes.stark.messagesotp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = ContactDetailsActivity.class.getSimpleName();

    public static final String INTENT_CONTACT_LOOKUP_KEY = "Contact Lookup key";
    public static final String INTENT_CONTANT_NAME_KEY = "Contact Name Key";
    private String mLookupKey;

    private static final int CONTACT_DETAILS_LOADER_ID = 0;

    private static final String[] PROJECTION = new String[]
            {
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.LABEL
            };
    public static final int COLUMN_NUMBER = 0;

    private static final String SELECTION = ContactsContract.Contacts.LOOKUP_KEY + " = ?"
            + " AND "
            + ContactsContract.Data.MIMETYPE + " = " +
            "'" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";

    private static final String SORT_ORDER_PHONE = ContactsContract.CommonDataKinds.Phone.TYPE + " ASC";


    private TextView mContactName;
    private RecyclerView mContactNumbers;
    private ContactNumbersAdapter mNumbersAdapter;
    private String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        if(getIntent().hasExtra(INTENT_CONTACT_LOOKUP_KEY)){
            mLookupKey = getIntent().getStringExtra(INTENT_CONTACT_LOOKUP_KEY);
        }

        mContactName = (TextView) findViewById(R.id.contact_name);
        if(getIntent().hasExtra(INTENT_CONTANT_NAME_KEY)){
            contactName = getIntent().getStringExtra(INTENT_CONTANT_NAME_KEY);
            mContactName.setText(getIntent().getStringExtra(INTENT_CONTANT_NAME_KEY));
        }


        mContactNumbers = (RecyclerView) findViewById(R.id.contact_number);
        mContactNumbers.setLayoutManager(new LinearLayoutManager(this));

        mNumbersAdapter = new ContactNumbersAdapter(this, contactName);
        mContactNumbers.setAdapter(mNumbersAdapter);

        getLoaderManager().initLoader(CONTACT_DETAILS_LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                new String[]{mLookupKey},
                SORT_ORDER_PHONE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null && cursor.getCount() > 0){
            Log.v(LOG_TAG, "Total numbers: " + cursor.getCount());
            mNumbersAdapter.swapCursor(cursor);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
