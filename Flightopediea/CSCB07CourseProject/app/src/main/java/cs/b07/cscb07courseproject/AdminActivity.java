package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import fragments.ViewPagerAdapterAdmin;

/**
 * Opens up the admin layout.
 */
public class AdminActivity extends AppCompatActivity {

    // Declaring Your View and Variables
    public static String account = "";
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapterAdmin adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[];
    private int numberTabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainacitivity);
        Intent intent = getIntent();
        account = intent.getStringExtra(Login.USER_MESSAGE);
        Log.d("UserName: ", account);

        Titles = getResources().getStringArray(R.array.admin_tab_items);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter =  new ViewPagerAdapterAdmin(getSupportFragmentManager(),Titles, numberTabs);

        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

        tabs.setViewPager(pager);
        pager.setCurrentItem(1, true);
    }

    /**
     * Updates the client information and layout.
     */
    public void updateClient(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateClient();
    }
}
