package com.whitdan.arkhamhorrorlcgcampaignguide.standalone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.whitdan.arkhamhorrorlcgcampaignguide.ChaosBagActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup.CampaignDifficultyFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario.FinishResolutionFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup.ScenarioIntroFragment;

/**
 * Activity for standalone scenarios.
 */

public class StandaloneActivity extends AppCompatActivity {

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
        viewPager.setOffscreenPageLimit(3);
        StandalonePagerAdapter adapter = new StandalonePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Setup tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.finish_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*
     Sets up overflow menu
      */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_standalone_menu, menu);
        return true;
    }

    // Enables up navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.chaos_bag:
                Intent intent = new Intent(this, ChaosBagActivity.class);
                startActivity(intent);
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
    private class StandalonePagerAdapter extends FragmentPagerAdapter {

        private StandalonePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ScenarioIntroFragment();
            } else if (position == 1) {
                return new LogFragment();
            } else if (position == 2) {
                return new CampaignDifficultyFragment();
            } else {
                return new FinishResolutionFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 4;
        }

        // Set titles of Campaign Setup tabs
        private final String[] tabTitles = new String[]{"Intro", "Setup", "Difficulty", "Finish"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
