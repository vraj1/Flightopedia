package fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Sliding layout class for the client.
 */
public class ViewPagerAdapterClient extends FragmentStatePagerAdapter{

    CharSequence Titles[];
    int NumbOfTabs;

    /**
     * Creates a new adapter.
     * @param fm handles the fragments
     * @param mTitles titles for the tabs
     * @param mNumbOfTabsumb number of tabs
     */
    public ViewPagerAdapterClient(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new client_setting_tab();
            case 1:
                return new client_booking_tab();
            default:
                return new flights_tab();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
