package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.NightEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup.ScenarioSetupActivity;

/**
 * Created by danie on 22/12/2016.
 */

public class CampaignsOnClickListener implements AdapterView.OnItemClickListener {

    private GlobalVariables globalVariables;
    private Context context;

    public CampaignsOnClickListener(GlobalVariables global, Context con) {
        globalVariables = global;
        context = con;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] selectionArgs = {Long.toString(id)};

        // Update campaign variables
        String[] campaignProjection = {
                CampaignEntry._ID,
                CampaignEntry.COLUMN_CURRENT_CAMPAIGN,
                CampaignEntry.COLUMN_CURRENT_SCENARIO
        };
        String campaignSelection = CampaignEntry._ID + " = ?";
        Cursor campaignCursor = db.query(
                CampaignEntry.TABLE_NAME,  // The table to query
                campaignProjection,                       // The columns to return
                campaignSelection,                        // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
        while (campaignCursor.moveToNext()) {
            globalVariables.setCampaignID(campaignCursor.getLong(campaignCursor
                    .getColumnIndexOrThrow(CampaignEntry._ID)));
            globalVariables.setCurrentCampaign(campaignCursor.getInt(campaignCursor
                    .getColumnIndexOrThrow(CampaignEntry.COLUMN_CURRENT_CAMPAIGN)));
            globalVariables.setCurrentScenario(campaignCursor.getInt(campaignCursor
                    .getColumnIndexOrThrow(CampaignEntry.COLUMN_CURRENT_SCENARIO)));
        }
        campaignCursor.close();

        // Update investigators
        String[] investigatorProjection = {
                InvestigatorEntry.COLUMN_INVESTIGATOR_NAME,
                InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE,
                InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR,
                InvestigatorEntry.COLUMN_INVESTIGATOR_XP
        };
        String investigatorSelection = InvestigatorEntry.PARENT_ID + " = ?";
        Cursor investigatorCursor = db.query(
                InvestigatorEntry.TABLE_NAME,
                investigatorProjection,
                investigatorSelection,
                selectionArgs,
                null,
                null,
                null
        );
        globalVariables.investigators.clear();
        for (int i = 0; investigatorCursor.moveToNext(); i++) {
            globalVariables.investigators.add(new Investigator());
            globalVariables.investigators.get(i).setName(investigatorCursor.getInt
                    (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                            .COLUMN_INVESTIGATOR_NAME)));
            globalVariables.investigators.get(i).changeDamage(investigatorCursor.getInt
                    (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                            .COLUMN_INVESTIGATOR_DAMAGE)));
            globalVariables.investigators.get(i).changeHorror(investigatorCursor.getInt
                    (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                            .COLUMN_INVESTIGATOR_HORROR)));
            globalVariables.investigators.get(i).changeXP(investigatorCursor.getInt
                    (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                            .COLUMN_INVESTIGATOR_XP)));
        }
        investigatorCursor.close();

        String[] nightProjection = {
                NightEntry.COLUMN_HOUSE_STANDING,
                NightEntry.COLUMN_GHOUL_PRIEST,
                NightEntry.COLUMN_LITA_STATUS,
                NightEntry.COLUMN_MIDNIGHT_STATUS,
                NightEntry.COLUMN_CULTISTS_INTERROGATED,
                NightEntry.COLUMN_DREW_INTERROGATED,
                NightEntry.COLUMN_PETER_INTERROGATED,
                NightEntry.COLUMN_HERMAN_INTERROGATED,
                NightEntry.COLUMN_VICTORIA_INTERROGATED,
                NightEntry.COLUMN_RUTH_INTERROGATED,
                NightEntry.COLUMN_MASKED_INTERROGATED
        };
        String nightSelection = NightEntry.PARENT_ID + " = ?";
        Cursor nightCursor = db.query(
                NightEntry.TABLE_NAME,
                nightProjection,
                nightSelection,
                selectionArgs,
                null,
                null,
                null
        );
        while (nightCursor.moveToNext()) {
            globalVariables.setHouseStanding(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_HOUSE_STANDING)));
            globalVariables.setGhoulPriestAlive(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_GHOUL_PRIEST)));
            globalVariables.setLitaStatus(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_LITA_STATUS)));
            globalVariables.setMidnightStatus(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_MIDNIGHT_STATUS)));
            globalVariables.setCultistsInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_CULTISTS_INTERROGATED)));
            globalVariables.setDrewInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_DREW_INTERROGATED)));
            globalVariables.setPeterInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_PETER_INTERROGATED)));
            globalVariables.setHermanInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_HERMAN_INTERROGATED)));
            globalVariables.setVictoriaInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_VICTORIA_INTERROGATED)));
            globalVariables.setRuthInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_RUTH_INTERROGATED)));
            globalVariables.setMaskedInterrogated(nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                    .COLUMN_MASKED_INTERROGATED)));
        }
        nightCursor.close();

        // Advance to scenario setup
        globalVariables.setScenarioStage(1);
        Intent intent = new Intent(context, ScenarioSetupActivity.class);
        context.startActivity(intent);
    }
}
