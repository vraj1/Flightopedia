package fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Sliding layout class for the admin.
 */
public class ViewPagerAdapterAdmin extends FragmentStatePagerAdapter{

    CharSequence Titles[];
    int NumbOfTabs;

    /**
     * Creates a new adapter.
     * @param fm handles the fragments
     * @param mTitles titles for tabs
     * @param mNumbOfTabsumb number of tabs
     */
    public ViewPagerAdapterAdmin(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new admin_client_tab();
            case 1:
                return new admin_setting_tab();
            case 2:
                return new flights_tab();
            default:
                return new flightschangetab();
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
