package com.wssholmes.stark.messagesotp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ContactDetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ContactDetailsActivity.class.getSimpleName();

    public static final String INTENT_CONTACT_LOOKUP_KEY = "Contact Lookup key";
    private String mLookupKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        if(getIntent().hasExtra(INTENT_CONTACT_LOOKUP_KEY)){
            mLookupKey = getIntent().getStringExtra(INTENT_CONTACT_LOOKUP_KEY);
        }
        Log.v(LOG_TAG, mLookupKey);


    }
}
