package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup.CampaignSetupActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;

/*
Main Activity - Allows the user to select a campaign to start.
 */

/*
    TODO: Players jumping in and out
    TODO: Extra scenarios
 */

public class SelectCampaignActivity extends AppCompatActivity {

    private CampaignsListAdapter campaignsListAdapter;
    private Cursor campaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_campaign);

        // Create a new dbHelper and get access to the SQLite Database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get a cursor from the database of all saved campaigns (and all columns of that campaign)
        campaigns = db.rawQuery("SELECT  * FROM " + ArkhamContract.CampaignEntry.TABLE_NAME, null);
        // Find saved campaigns ListView
        ListView campaignItems = (ListView) findViewById(R.id.saved_campaigns);
        // Setup and attach cursor adapter to the list to display all saved campaigns
        campaignsListAdapter = new CampaignsListAdapter(this, campaigns);
        campaignItems.setAdapter(campaignsListAdapter);
        // Setup and attach onItemClickListener to the ListView to allow resuming a campaign on click
        CampaignsOnClickListener campaignsOnClickListener = new CampaignsOnClickListener((GlobalVariables) this
                .getApplication(), this);
        campaignItems.setOnItemClickListener(campaignsOnClickListener);
        // Setup and attach onItemLongClickListener to the ListView to allow deleting campaigns on long click
        CampaignsOnLongClickListener campaignsOnLongClickListener = new CampaignsOnLongClickListener(this);
        campaignItems.setOnItemLongClickListener(campaignsOnLongClickListener);
    }

    // Close the cursor when the Activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        campaigns.close();
    }

    // Sets up overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_campaign_select_menu, menu);
        return true;
    }

    // Controls what to do when a menu item is clicked on
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expacs_owned:
                // Creates a multi-selection dialog to select which expansions are owned
                ExpacsOwnedDialogFragment newFragment = new ExpacsOwnedDialogFragment();
                newFragment.show(this.getFragmentManager(), "owned");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Starts a Night of the Zealot campaign [attached to xml onclick for the relevant button]
    public void startNight(View v) {
        GlobalVariables globalVariables = (GlobalVariables) this.getApplication();
        // Set current campaign to Night of the Zealot (id = 1)
        globalVariables.setCurrentCampaign(1);
        // Set current scenario to setup (id = 0)
        globalVariables.setCurrentScenario(0);
        // Reset a couple of variables used elsewhere
        globalVariables.investigatorNames.clear();
        globalVariables.investigatorsInUse = new int[]{0, 0, 0, 0, 0, 0};
        // Go to campaign setup
        Intent intent = new Intent(this, CampaignSetupActivity.class);
        startActivity(intent);
    }

    public void startDunwich(View v) {
        Toast toast = Toast.makeText(this, "The Dunwich Legacy has not yet been released.", Toast.LENGTH_SHORT);
        toast.show();
    }

    /* Starts a Dunwich Legacy campaign [will be attached to xml onclick for the relevant button when released]
    public void startDunwich(View v){
        // Set current campaign to Dunwich Legacy (id = 2)
        ((GlobalVariables) this.getApplication()).setCurrentCampaign(2);
        // Set current scenario to setup (id = 0)
        ((GlobalVariables) this.getApplication()).setCurrentScenario(0);
        // Go to campaign setup
        Intent intent = new Intent(this, CampaignSetupActivity.class);
        startActivity(intent);
    }

    /* These exist to allow passing the adapter to the DeleteCampaignDialogFragment to allow refreshing
        the ListView on delete */
    public CampaignsListAdapter getCampaignsListAdapter() {
        return campaignsListAdapter;
    }


    // Dialog fragment for controlling which expansions are owned
    public static class ExpacsOwnedDialogFragment extends DialogFragment {

        boolean dunwichOwned;
        SharedPreferences settings;
        String dunwichOwnedString;
        String sharedPrefs;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            sharedPrefs = getActivity().getResources().getString(R.string.expacs_owned);
            dunwichOwnedString = getActivity().getResources().getString(R.string.dunwich_campaign_name);

            settings = getActivity().getSharedPreferences(sharedPrefs, 0);
            dunwichOwned = settings.getBoolean(dunwichOwnedString, true);

            boolean[] startChecked = new boolean[1];
            startChecked[0] = dunwichOwned;

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle(R.string.pick_expacs_owned)
                    // Set the items and to all start checked and to change the local variable onClick
                    .setMultiChoiceItems(R.array.expansion_names, startChecked,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    switch (which) {
                                        case 0:
                                            dunwichOwned = isChecked;
                                    }
                                }
                            })
                    // Set the action buttons
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // Save the settings
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean(dunwichOwnedString,  dunwichOwned);
                            editor.apply();
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
