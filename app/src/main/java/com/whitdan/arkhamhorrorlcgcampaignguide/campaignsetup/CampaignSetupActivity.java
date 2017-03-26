package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
Activity for campaign setup. Includes a TabLayout and three fragments, for the campaign introduction,
a difficulty selector, and investigator selector.
 */

public class CampaignSetupActivity extends AppCompatActivity {

    static String campaignName = "";
    private static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_setup);
        globalVariables = (GlobalVariables) this.getApplication();

        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Find the view pager that will allow the user to swipe between fragments and setup an adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.campaign_viewpager);
        viewPager.setOffscreenPageLimit(2);
        CampaignSetupPagerAdapter adapter = new CampaignSetupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Setup tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.campaign_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupUI(findViewById(R.id.campaign_setup_layout), this);
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

    // Allows swiping between the campaign setup fragments
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
        private final String[] tabTitles = new String[]{"Investigators", "Introduction", "Difficulty"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    /*
    Increments the scenario, saves a new campaign, and moves to scenario setup (called from xml onClick)
     */
    public void startScenario(View v) {
        GlobalVariables globalVariables = (GlobalVariables) this.getApplication();

        // Clear and then set investigators
        globalVariables.investigators.clear();
        globalVariables.savedInvestigators.clear();
        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
            globalVariables.investigators.add(new Investigator(globalVariables.investigatorNames.get(i),
                    globalVariables.playerNames[i], globalVariables.deckNames[i], globalVariables.decklists[i]));
            globalVariables.investigatorsInUse[globalVariables.investigatorNames.get(i)] = 1;
        }

        // Check that an investigator has been selected and a campaign name has been entered
        if (globalVariables.investigators.size() > 0 && campaignName.length() > 0) {

            int currentCampaign = globalVariables.getCurrentCampaign();
            switch (currentCampaign) {
                // Night of the Zealot
                case 1:
                    // Set current scenario to first scenario
                    globalVariables.setCurrentScenario(1);
                    globalVariables.setScenarioStage(1);
                    globalVariables.investigatorNames.clear();
                    globalVariables.playerNames = new String[4];
                    globalVariables.decklists = new String[4];

                    // Save the new campaign
                    newCampaign();
                    campaignName = "";

                    // Go to scenario setup
                    Intent intent = new Intent(this, ScenarioSetupActivity.class);
                    startActivity(intent);
                    break;
                // The Dunwich Legacy
                case 2:
                    // Create dialogfragment for scenario selection
                    DialogFragment dunwichFragment = new DunwichDialog();
                    dunwichFragment.show(this.getFragmentManager(), "dunwich");
                    break;
            }
        }
        // Display an error and don't proceed if no investigator has been selected
        else if (globalVariables.investigators.size() == 0) {
            Toast toast = Toast.makeText(this, "You must select an investigator.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (campaignName.length() == 0) {
            Toast toast = Toast.makeText(this, "You must enter a campaign name.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Hides the soft keyboard when someone clicks outside the EditText

    public void setupUI(final View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) activity.getSystemService(
                                    Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    /*
     Saves a new campaign to the database (called from startScenario - included separately for neatness)
      */
    private void newCampaign() {

        // Get a writable database
        GlobalVariables globalVariables = (GlobalVariables) this.getApplication();
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create entry in campaigns table
        ContentValues campaignValues = new ContentValues();
        campaignValues.put(CampaignEntry.COLUMN_CAMPAIGN_NAME, campaignName);
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables.getCurrentCampaign());
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables.getCurrentScenario());
        campaignValues.put(CampaignEntry.COLUMN_ROLAND_INUSE, globalVariables.investigatorsInUse[Investigator
                .ROLAND_BANKS]);
        campaignValues.put(CampaignEntry.COLUMN_DAISY_INUSE, globalVariables.investigatorsInUse[Investigator
                .DAISY_WALKER]);
        campaignValues.put(CampaignEntry.COLUMN_SKIDS_INUSE, globalVariables.investigatorsInUse[Investigator
                .SKIDS_OTOOLE]);
        campaignValues.put(CampaignEntry.COLUMN_AGNES_INUSE, globalVariables.investigatorsInUse[Investigator
                .AGNES_BAKER]);
        campaignValues.put(CampaignEntry.COLUMN_WENDY_INUSE, globalVariables.investigatorsInUse[Investigator
                .WENDY_ADAMS]);
        campaignValues.put(CampaignEntry.COLUMN_ZOEY_INUSE, globalVariables.investigatorsInUse[Investigator
                .ZOEY_SAMARAS]);
        campaignValues.put(CampaignEntry.COLUMN_REX_INUSE, globalVariables.investigatorsInUse[Investigator
                .REX_MURPHY]);
        campaignValues.put(CampaignEntry.COLUMN_JENNY_INUSE, globalVariables.investigatorsInUse[Investigator
                .JENNY_BARNES]);
        campaignValues.put(CampaignEntry.COLUMN_JIM_INUSE, globalVariables.investigatorsInUse[Investigator
                .JIM_CULVER]);
        campaignValues.put(CampaignEntry.COLUMN_PETE_INUSE, globalVariables.investigatorsInUse[Investigator
                .ASHCAN_PETE]);
        long newCampaignId = db.insert(CampaignEntry.TABLE_NAME, null, campaignValues);
        globalVariables.setCampaignID(newCampaignId);

        // Create campaign specific table
        int currentCampaign = globalVariables.getCurrentCampaign();
        switch (currentCampaign) {
            // Night of the Zealot
            case 1:
                ContentValues nightValues = new ContentValues();
                nightValues.put(ArkhamContract.NightEntry.PARENT_ID, newCampaignId);
                db.insert(ArkhamContract.NightEntry.TABLE_NAME, null, nightValues);
                break;
        }

        // Create entries for every investigator in the investigators table
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
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER, globalVariables
                    .investigators.get(i).getPlayer());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME, globalVariables
                    .investigators.get(i).getDeckName());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST, globalVariables
                    .investigators.get(i).getDecklist());
            db.insert(ArkhamContract.InvestigatorEntry.TABLE_NAME, null, investigatorValues);
        }
    }

    /*
        DialogFragment for The Dunwich Legacy
     */
    public static class DunwichDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_option)
                    .setItems(R.array.dunwich_setup_options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    // Set current scenario and first scenario
                                    globalVariables.setCurrentScenario(1);
                                    globalVariables.setFirstScenario(1);
                                    break;
                                case 1:
                                    globalVariables.setCurrentScenario(2);
                                    globalVariables.setFirstScenario(2);
                                    break;
                            }
                            globalVariables.setScenarioStage(1);
                            globalVariables.investigatorNames.clear();
                            globalVariables.playerNames = new String[4];
                            globalVariables.deckNames = new String[4];
                            globalVariables.decklists = new String[4];

                            // Save the new campaign
                            // Get a writable database
                            ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            // Create entry in campaigns table
                            ContentValues campaignValues = new ContentValues();
                            campaignValues.put(CampaignEntry.COLUMN_CAMPAIGN_NAME, campaignName);
                            campaignValues.put(CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables
                                    .getCurrentCampaign());
                            campaignValues.put(CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables
                                    .getCurrentScenario());
                            campaignValues.put(CampaignEntry.COLUMN_ROLAND_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .ROLAND_BANKS]);
                            campaignValues.put(CampaignEntry.COLUMN_DAISY_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .DAISY_WALKER]);
                            campaignValues.put(CampaignEntry.COLUMN_SKIDS_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .SKIDS_OTOOLE]);
                            campaignValues.put(CampaignEntry.COLUMN_AGNES_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .AGNES_BAKER]);
                            campaignValues.put(CampaignEntry.COLUMN_WENDY_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .WENDY_ADAMS]);
                            campaignValues.put(CampaignEntry.COLUMN_ZOEY_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .ZOEY_SAMARAS]);
                            campaignValues.put(CampaignEntry.COLUMN_REX_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .REX_MURPHY]);
                            campaignValues.put(CampaignEntry.COLUMN_JENNY_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .JENNY_BARNES]);
                            campaignValues.put(CampaignEntry.COLUMN_JIM_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .JIM_CULVER]);
                            campaignValues.put(CampaignEntry.COLUMN_PETE_INUSE, globalVariables
                                    .investigatorsInUse[Investigator
                                    .ASHCAN_PETE]);
                            long newCampaignId = db.insert(CampaignEntry.TABLE_NAME, null, campaignValues);
                            globalVariables.setCampaignID(newCampaignId);

                            // Create campaign specific table
                            ContentValues dunwichValues = new ContentValues();
                            dunwichValues.put(ArkhamContract.DunwichEntry.PARENT_ID, newCampaignId);
                            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO,
                                    globalVariables.getFirstScenario());
                            db.insert(ArkhamContract.DunwichEntry.TABLE_NAME, null, dunwichValues);

                            // Create entries for every investigator in the investigators table
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
                                investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER, globalVariables
                                        .investigators.get(i).getPlayer());
                                investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME, globalVariables
                                        .investigators.get(i).getDeckName());
                                investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST, globalVariables
                                        .investigators.get(i).getDecklist());
                                db.insert(ArkhamContract.InvestigatorEntry.TABLE_NAME, null, investigatorValues);
                            }

                            campaignName = "";

                            // Go to scenario setup
                            Intent intent = new Intent(getActivity(), ScenarioSetupActivity.class);
                            startActivity(intent);
                        }
                    });
            return builder.create();
        }
    }
}
