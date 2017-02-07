package com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.LogFragment;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/*
    Displays the relevant scenario finish fragments (Investigators, Resolution and Log)
 */


public class FinishScenarioActivity extends AppCompatActivity {

    private static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_scenario);
        globalVariables = (GlobalVariables) this.getApplication();

        // Setup back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Find the view pager that will allow the user to swipe between fragments and set the adapter to it
        ViewPager viewPager = (ViewPager) findViewById(R.id.finish_viewpager);
        viewPager.setOffscreenPageLimit(2);
        FinishScenarioPagerAdapter adapter = new FinishScenarioPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Setup tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.finish_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*
     Sets up overflow menu with option to add side story
      */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_finish_scenario_menu, menu);
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
            case R.id.player_cards:
                // Creates a dialog to select player cards
                PlayerCardsDialog newFragment = new PlayerCardsDialog();
                newFragment.show(this.getFragmentManager(), "player_cards");
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

    /*
        DialogFragment for player cards
     */
    public static class PlayerCardsDialog extends DialogFragment {
        boolean StrangeSolution;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            boolean[] startChecked = new boolean[1];
            StrangeSolution = globalVariables.getStrangeSolution() != 0;
            startChecked[0] = StrangeSolution;
            // Set the dialog title
            builder.setTitle(R.string.pick_option)
                    // Set the items and to all start checked and to change the local variable onClick
                    .setMultiChoiceItems(R.array.player_cards, startChecked,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    switch (which) {
                                        case 0:
                                            StrangeSolution = isChecked;
                                    }
                                }
                            })
                    // Set the action buttons
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // Save the player card settings locally
                            if (StrangeSolution) {
                                globalVariables.setStrangeSolution(1);
                            } else {
                                globalVariables.setStrangeSolution(0);
                            }
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled
                        }
                    });

            return builder.create();
        }
    }
}
