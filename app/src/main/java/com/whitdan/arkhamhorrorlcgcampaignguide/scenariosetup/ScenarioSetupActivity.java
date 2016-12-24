package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/*
    Displays the relevant scenario setup fragments (Investigators, Intro and Setup)
 */

public class ScenarioSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_setup);

        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Find the view pager that will allow the user to swipe between fragments and set the adapter onto it
        ViewPager viewPager = (ViewPager) findViewById(R.id.scenario_viewpager);
        ScenarioSetupPagerAdapter adapter = new ScenarioSetupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Setup tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.scenario_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Enables up navigation (goes back to home page - SelectCampaignActivity)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Disable system back button [Might change this to work the same as the up button]
    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(getApplicationContext(), "Cannot go back.", Toast.LENGTH_SHORT);
        toast.show();
    }

    // Allows swiping between the scenario setup fragments
    private class ScenarioSetupPagerAdapter extends FragmentPagerAdapter {

        private ScenarioSetupPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ScenarioInvestigatorsFragment();
            } else if (position == 1) {
                return new ScenarioIntroFragment();
            } else {
                return new LogFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 3;
        }

        // Set titles of scenario setup tabs
        private final String[] tabTitles = new String[]{"Investigators", "Introduction", "Setup"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
