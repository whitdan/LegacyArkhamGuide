package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup.ScenarioSetupActivity;

/*
Activity for campaign setup. Includes a tablayout and three fragments, for the campaign introduction,
a difficulty selector, and investigator selector.
 */

public class CampaignSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_setup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.campaign_viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CampaignSetupPagerAdapter adapter = new CampaignSetupPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.campaign_sliding_tabs);
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


    // FragmentPagerAdapter for the CampaignSetupActivity fragments
    private class CampaignSetupPagerAdapter extends FragmentPagerAdapter {

        private CampaignSetupPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new CampaignInvestigatorsFragment();
            } else if (position == 1) {
                return new CampaignIntroFragment();
            } else {
                return new CampaignDifficultyFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 3;
        }

        // Set titles of Campaign Setup tabs
        private String tabTitles[] = new String[]{"Investigators", "Introduction", "Difficulty"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Increments the scenario, saves a new campaign, and moves to scenario setup
    public void startScenario(View v) {
        GlobalVariables globalVariables = (GlobalVariables) this.getApplication();

        // Clear and then set investigators
        globalVariables.investigators.clear();
        for (int i = 0; i < 4; i++) {
            if (globalVariables.investigatorNames[i] > 0) {
                globalVariables.investigators.add(new Investigator(globalVariables.investigatorNames[i]));
            }
        }

        if (globalVariables.investigators.size() > 0) {
            // Set current scenario to first scenario (id = 1)
            globalVariables.setCurrentScenario(1);
            globalVariables.setScenarioStage(1);

            // Save the new campaign
            newCampaign();

            // Go to scenario setup
            Intent intent = new Intent(this, ScenarioSetupActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "You must select an investigator.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Saves the new campaign to the database
    public void newCampaign() {
        GlobalVariables globalVariables = (GlobalVariables) this.getApplication();
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create campaign entry
        ContentValues campaignValues = new ContentValues();
        EditText campaignName = (EditText) findViewById(R.id.campaign_name);
        campaignValues.put(CampaignEntry.COLUMN_CAMPAIGN_NAME, campaignName.getText().toString().trim());
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables.getCurrentCampaign());
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables.getCurrentScenario());
        long newCampaignId = db.insert(CampaignEntry.TABLE_NAME, null, campaignValues);
        globalVariables.setCampaignID(newCampaignId);

        if (globalVariables.getCurrentCampaign() == 1) {
            // Create night entry
            ContentValues nightValues = new ContentValues();
            nightValues.put(ArkhamContract.NightEntry.PARENT_ID, newCampaignId);
            long newNightId = db.insert(ArkhamContract.NightEntry.TABLE_NAME, null, nightValues);
        }

        // Create investigator entries
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            ContentValues investigatorValues = new ContentValues();
            investigatorValues.put(ArkhamContract.InvestigatorEntry.PARENT_ID, newCampaignId);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID, i);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_NAME, globalVariables
                    .investigators.get(i).getName());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS, 1);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_XP, 0);
            long newInvestigatorId = db.insert(ArkhamContract.InvestigatorEntry.TABLE_NAME, null, investigatorValues);
        }
    }
}
