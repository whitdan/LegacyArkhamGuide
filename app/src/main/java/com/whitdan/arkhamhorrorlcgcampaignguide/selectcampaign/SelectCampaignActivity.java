package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup.CampaignSetupActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;

/*
Main Activity - Allows the user to select a campaign to start.
 */

/*
    TODO: Investigator death
    TODO: Investigator exclusivity
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

        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        // Get access to the underlying writeable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Query for items from the database and get a cursor back
        campaigns = db.rawQuery("SELECT  * FROM " + ArkhamContract.CampaignEntry.TABLE_NAME, null);
        // Find ListView to populate
        ListView campaignItems = (ListView) findViewById(R.id.saved_campaigns);
        // Setup cursor adapter using cursor from last step
        campaignsListAdapter = new CampaignsListAdapter(this, campaigns);
        CampaignsOnClickListener campaignsOnClickListener = new CampaignsOnClickListener((GlobalVariables) this
                .getApplication(), this);
        CampaignsOnLongClickListener campaignsOnLongClickListener = new CampaignsOnLongClickListener(this);
        // Attach cursor adapter to the ListView
        campaignItems.setAdapter(campaignsListAdapter);
        campaignItems.setOnItemClickListener(campaignsOnClickListener);
        campaignItems.setOnItemLongClickListener(campaignsOnLongClickListener);
    }

    // Starts a Night of the Zealot campaign
    public void startNight(View v) {
        // Set current campaign to Night of the Zealot (id = 1)
        ((GlobalVariables) this.getApplication()).setCurrentCampaign(1);
        // Set current scenario to setup (id = 0)
        ((GlobalVariables) this.getApplication()).setCurrentScenario(0);
        // Go to campaign setup
        Intent intent = new Intent(this, CampaignSetupActivity.class);
        startActivity(intent);
    }

    public CampaignsListAdapter getCampaignsListAdapter(){return campaignsListAdapter;}
    public Cursor getCampaignsCursor(){return campaigns;}
}
