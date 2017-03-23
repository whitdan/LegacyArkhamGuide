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
import com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup.CampaignDifficultyFragment;
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
        // If on campaign finish, go to campaign finish setup
        if (globalVariables.getScenarioStage() == 3) {
            CampaignFinishPagerAdapter adapter = new CampaignFinishPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        // If any investigators are dead, go to new investigator fragment
        else if (investigatorDead || globalVariables.editInvestigators) {
            NewInvestigatorPagerAdapter adapter = new NewInvestigatorPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        // If appropriate, go to interlude
        else if (interlude) {
            InterludePagerAdapter adapter = new InterludePagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        // If appropriate, go to new campaign
        else if (globalVariables.getCurrentScenario() == 1000) {
            NewCampaignPagerAdapter adapter = new NewCampaignPagerAdapter(getSupportFragmentManager());
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
            case R.id.edit_investigators:
                // Set edit investigators to true and recreate activity
                globalVariables.editInvestigators = true;
                finish();
                startActivity(starterIntent);
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
                            // Set scenario stage back to 1
                            globalVariables.setScenarioStage(1);
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
                                    if (globalVariables.getRougarouStatus() > 0) {
                                        Toast toast = Toast.makeText(getActivity(), "You have already completed this " +
                                                "scenario.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else if (XP < 1) {
                                        Toast toast = Toast.makeText(getActivity(), "You do not have enough XP (cost:" +
                                                " 1 per investigator).", Toast.LENGTH_SHORT);
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
                                case 1:
                                    if (globalVariables.getCarnevaleStatus() > 0) {
                                        Toast toast = Toast.makeText(getActivity(), "You have already completed this " +
                                                "scenario.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else if (XP < 3) {
                                        Toast toast = Toast.makeText(getActivity(), "You do not have enough XP (cost:" +
                                                " 3 per investigator).", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        globalVariables.setPreviousScenario(globalVariables.getCurrentScenario());
                                        globalVariables.setCurrentScenario(102);
                                        for (int i = 0; i < globalVariables.investigators.size(); i++) {
                                            globalVariables.investigators.get(i).changeXP(-3);
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
        private final String[] tabTitles = new String[]{getString(R.string.edit_investigators)};

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

    // Returns the new campaign fragment instead
    private class NewCampaignPagerAdapter extends FragmentPagerAdapter {

        private NewCampaignPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ScenarioInterludeFragment();
            } else {
                return new CampaignDifficultyFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // Set titles of scenario setup tabs
        private final String[] tabTitles = new String[]{"Intro", "Difficulty"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Returns the campaign finish fragments instead
    private class CampaignFinishPagerAdapter extends FragmentPagerAdapter {

        private CampaignFinishPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the right fragment to page to
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ScenarioInvestigatorsFragment();
            } else {
                return new LogFragment();
            }
        }

        // Number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // Set titles of scenario setup tabs
        private final String[] tabTitles = new String[]{"Investigators", "Log"};

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    // Used when an investigator has died to restart the scenario
    public void restartScenario(Context context) {

        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
            for (int a = 0; a < globalVariables.investigators.size(); a++) {
                if (globalVariables.investigators.get(a).getName() == globalVariables.investigatorNames.get(i)) {
                    globalVariables.investigators.get(a).setStatus(999);
                }
            }
            for (int a = 0; a < globalVariables.savedInvestigators.size(); a++) {
                if (globalVariables.savedInvestigators.get(a).getName() == globalVariables.investigatorNames.get(i)) {
                    globalVariables.savedInvestigators.get(a).setStatus(999);
                }
            }
        }
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            if (globalVariables.investigators.get(i).getStatus() != 999) {
                globalVariables.investigators.get(i).setStatus(3);
                globalVariables.savedInvestigators.add(globalVariables.investigators.get(i));
            }
        }
        for(int i = globalVariables.savedInvestigators.size() - 1; i >= 0; i--){
            if (globalVariables.savedInvestigators.get(i).getStatus() == 999) {
                globalVariables.savedInvestigators.remove(i);
            }
        }

        globalVariables.investigators.clear();
        if (ScenarioNewInvestigatorFragment.investigatorOne != null) {
            globalVariables.investigators.add(ScenarioNewInvestigatorFragment.investigatorOne);
        }
        if (ScenarioNewInvestigatorFragment.investigatorTwo != null) {
            globalVariables.investigators.add(ScenarioNewInvestigatorFragment.investigatorTwo);
        }
        if (ScenarioNewInvestigatorFragment.investigatorThree != null) {
            globalVariables.investigators.add(ScenarioNewInvestigatorFragment.investigatorThree);
        }
        if (ScenarioNewInvestigatorFragment.investigatorFour != null) {
            globalVariables.investigators.add(ScenarioNewInvestigatorFragment.investigatorFour);
        }

        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
            if (globalVariables.investigatorsInUse[globalVariables.investigatorNames.get(i)] == 0) {
                globalVariables.investigators.add(new Investigator(globalVariables.investigatorNames.get(i),
                        globalVariables.playerNames[i], globalVariables.deckNames[i], globalVariables.decklists[i]));
            }
            globalVariables.investigatorsInUse[globalVariables.investigatorNames.get(i)] = 1;
        }
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            globalVariables.investigators.get(i).setStatus(1);
        }

        globalVariables.investigatorNames.clear();
        globalVariables.playerNames = new String[4];
        globalVariables.deckNames = new String[4];
        globalVariables.decklists = new String[4];

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
