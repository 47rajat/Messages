package com.wssholmes.stark.messagesotp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by stark on 26/11/16.
 */

public class HomeScreenViewPagerAdapter extends FragmentPagerAdapter {
    private static final String LOG_TAG = HomeScreenViewPagerAdapter.class.getSimpleName();

    private static final int TOTAL_PAGE = 2;
    private final Context mContext;

    public HomeScreenViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ContactsFragment();
            case 1:
                return new MessagesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE;
    }

    @Override
    public CharSequence getPageTitle(int position) { //TODO: MAKE THE TITLE ALIGNED TO CENTER IN LANDSCAPE MODE
        switch (position){
            case 0:
                return mContext.getString(R.string.contacts_tab_title);
            case 1:
                return mContext.getString(R.string.messages_tab_title);
            default:
                return null;
        }
    }
}
