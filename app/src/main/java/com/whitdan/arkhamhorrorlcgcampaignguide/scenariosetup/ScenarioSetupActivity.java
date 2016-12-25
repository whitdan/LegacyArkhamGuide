package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/*
    Displays the relevant scenario setup fragments (Investigators, Intro and Setup)
 */

public class ScenarioSetupActivity extends AppCompatActivity {

    GlobalVariables globalVariables;
    Intent starterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_setup);
        globalVariables = (GlobalVariables) this.getApplication();
        starterIntent = getIntent();

        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Check if any investigators are dead
        boolean investigatorDead = false;
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            Log.i("Fragment", "Current investigator: " + globalVariables.investigators.get(i).getStatus());
            if (globalVariables.investigators.get(i).getStatus() == 2) {
                investigatorDead = true;
            }
        }

        // Find the view pager that will allow the user to swipe between fragments and set the adapter onto it
        ViewPager viewPager = (ViewPager) findViewById(R.id.scenario_viewpager);
        // If any investigators are dead, go to new investigator fragment
        if (investigatorDead) {
            NewInvestigatorPagerAdapter adapter = new NewInvestigatorPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        // If no investigators are dead, go to normal scenario setup
        else {
            ScenarioSetupPagerAdapter adapter = new ScenarioSetupPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }

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

    // Returns the new investigator fragment instead
    private class NewInvestigatorPagerAdapter extends FragmentPagerAdapter {

        private NewInvestigatorPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            return new ScenarioNewInvestigatorFragment();
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 1;
        }

        // Set titles of scenario setup tabs
        private final String[] tabTitles = new String[]{"Dead Investigator"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Used when an investigator has died to restart the scenario;
    public void restartScenario(){
        boolean[] removeInvestigator = new boolean[]{};
        // If the investigator is dead, recreates it
        for(int i = 0; i < globalVariables.investigators.size(); i++){
            Investigator currentInvestigator = globalVariables.investigators.get(i);
            if(currentInvestigator.getStatus()==2){
                if(globalVariables.investigatorNames[i] > 0){
                    currentInvestigator.setupInvestigator(globalVariables.investigatorNames[i]);
                }
                else
                {
                    removeInvestigator[i] = true;
                }
            }
        }
        // Removes any unused investigators
        for(int i = 0; i < removeInvestigator.length; i++){
            globalVariables.investigators.remove(i);
        }
        // Sets up the page again (effectively advancing to scenario setup normally)
        finish();
        startActivity(starterIntent);
    }
}
