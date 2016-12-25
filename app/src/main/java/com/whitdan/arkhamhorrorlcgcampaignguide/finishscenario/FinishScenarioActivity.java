package com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/*
    Displays the relevant scenario finish fragments (Investigators, Resolution and Log)
 */


public class FinishScenarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_scenario);

        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Find the view pager that will allow the user to swipe between fragments and set the adapter to it
        ViewPager viewPager = (ViewPager) findViewById(R.id.finish_viewpager);
        FinishScenarioPagerAdapter adapter = new FinishScenarioPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Setup tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.finish_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Enables up navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Makes back button go up (back to home page - SelectCampaignActivity)
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    // Allows swiping between the scenario finish fragments
    private class FinishScenarioPagerAdapter extends FragmentPagerAdapter {

        private FinishScenarioPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FinishInvestigatorsFragment();
            } else if (position == 1) {
                return new FinishResolutionFragment();
            } else {
                return new LogFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 3;
        }

        // Set titles of Campaign Setup tabs
        private final String[] tabTitles = new String[]{"Investigators", "Resolution", "Campaign Log"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
