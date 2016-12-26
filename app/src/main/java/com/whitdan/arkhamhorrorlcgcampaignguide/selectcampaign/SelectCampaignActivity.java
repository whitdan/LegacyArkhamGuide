package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    protected void onDestroy(){
        super.onDestroy();
        campaigns.close();
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
        globalVariables.investigatorsInUse = new int[]{0,0,0,0,0,0};
        // Go to campaign setup
        Intent intent = new Intent(this, CampaignSetupActivity.class);
        startActivity(intent);
    }

    public void startDunwich(View v){
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
    public CampaignsListAdapter getCampaignsListAdapter(){return campaignsListAdapter;}
}
