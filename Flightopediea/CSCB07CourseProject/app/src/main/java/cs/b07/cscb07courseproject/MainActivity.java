package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import fragments.ViewPagerAdapterClient;

/**
 * Opens up the client's account.
 */
public class MainActivity extends AppCompatActivity {

    // Declaring Your View and Variables
    public static String account = "";
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapterClient adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[];
    private int numberTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainacitivity);
        Intent intent = getIntent();
        account = intent.getStringExtra(Login.USER_MESSAGE);
        Log.d("UserName: ", account);

        Titles = getResources().getStringArray(R.array.client_tab_items);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter =  new ViewPagerAdapterClient(getSupportFragmentManager(),Titles, numberTabs);

        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

        pager.setCurrentItem(1, true);
    }

    /**
     * Update the bookings
     */
    public void updateBooking(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateBooking();
    }
}
