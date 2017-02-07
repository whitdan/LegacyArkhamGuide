package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign.SelectCampaignActivity;

/*
    Displays the relevant scenario setup fragments (Investigators, Intro and Setup)

    Also contains method for showing the dead investigator screen if there is a dead investigator
 */

public class ScenarioSetupActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    static Intent starterIntent;

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
            if (globalVariables.investigators.get(i).getStatus() == 2) {
                investigatorDead = true;
            }
        }

        // Check if on interlude
        boolean interlude = false;
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 3) {
            interlude = true;
        }

        // Find the view pager that will allow the user to swipe between fragments and set the adapter onto it
        ViewPager viewPager = (ViewPager) findViewById(R.id.scenario_viewpager);
        viewPager.setOffscreenPageLimit(2);
        // If any investigators are dead, go to new investigator fragment
        if (investigatorDead) {
            NewInvestigatorPagerAdapter adapter = new NewInvestigatorPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        // If appropriate, go to interlude
        else if (interlude) {
            InterludePagerAdapter adapter = new InterludePagerAdapter(getSupportFragmentManager());
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

    /*
     Sets up overflow menu with option to add side story
      */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_scenario_setup_menu, menu);
        return true;
    }

    // Enables up navigation (goes back to home page - SelectCampaignActivity)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.add_side_story:
                // Creates a dialog to select side story
                SideStoryDialog newFragment = new SideStoryDialog();
                newFragment.show(this.getFragmentManager(), "side_story");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        DialogFragment for side stories
     */
    public static class SideStoryDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_option)
                    .setItems(R.array.side_stories, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Work out lowest XP level
                            int XP = 9999999;
                            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                                if (globalVariables.investigators.get(i).getAvailableXP() < XP) {
                                    XP = globalVariables.investigators.get(i).getAvailableXP();
                                }
                            }
                            switch (which) {
                                // Check XP, set current scenario and charge XP
                                case 0:
                                    if (XP < 1) {
                                        Toast toast = Toast.makeText(getActivity(), "You do not have enough XP (cost:" +
                                                " 1 per investigator).", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else if (globalVariables.getRougarouStatus() > 0) {
                                        Toast toast = Toast.makeText(getActivity(), "You have already completed this " +
                                                "scenario.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        globalVariables.setPreviousScenario(globalVariables.getCurrentScenario());
                                        globalVariables.setCurrentScenario(101);
                                        for (int i = 0; i < globalVariables.investigators.size(); i++) {
                                            globalVariables.investigators.get(i).changeXP(-1);
                                        }
                                        getActivity().finish();
                                        startActivity(starterIntent);
                                    }
                                    break;
                            }
                        }
                    });
            return builder.create();
        }
    }

    // Makes back button go up (back to home page - SelectCampaignActivity)
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
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
        private final String[] tabTitles = new String[]{"Dead Investigators"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Returns the interlude fragment instead
    private class InterludePagerAdapter extends FragmentPagerAdapter {

        private InterludePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            return new ScenarioInterludeFragment();
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 1;
        }

        // Set titles of scenario setup tabs
        private final String[] tabTitles = new String[]{"Interlude"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Used when an investigator has died to restart the scenario;
    public void restartScenario(Context context) {
        boolean[] removeInvestigator = new boolean[4];
        // If the investigator is dead, recreates it
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            Investigator currentInvestigator = globalVariables.investigators.get(i);
            if (currentInvestigator.getStatus() == 2) {
                removeInvestigator[i] = true;
            }
        }
        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
            globalVariables.investigators.add(new Investigator(globalVariables.investigatorNames.get(i)));
            globalVariables.investigatorsInUse[globalVariables.investigatorNames.get(i)] = 1;
        }
        globalVariables.investigatorNames.clear();

        // Removes any unused investigators (works backwards to avoid reindexing)
        for (int i = 3; i >= 0; i--) {
            if (removeInvestigator[i]) {
                globalVariables.investigators.remove(i);
            }
        }

        if (globalVariables.investigators.size() == 0) {
            // Kick back to Campaign Select if no replacement investigators are selected
            Intent restartIntent = new Intent(context, SelectCampaignActivity.class);
            context.startActivity(restartIntent);
        } else {
            // Sets up the page again (effectively advancing to scenario setup normally)
            finish();
            startActivity(starterIntent);
        }
    }
}
