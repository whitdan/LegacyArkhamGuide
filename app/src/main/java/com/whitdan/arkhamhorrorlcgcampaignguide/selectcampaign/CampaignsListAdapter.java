package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;

/**
 * CursorAdapter to display all saved campaigns from the campaigns cursor.
 */

class CampaignsListAdapter extends CursorAdapter {

    CampaignsListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_campaign, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView campaignNameView = (TextView) view.findViewById(R.id.campaign_name);
        TextView currentCampaignView = (TextView) view.findViewById(R.id.current_campaign);
        TextView currentScenarioView = (TextView) view.findViewById(R.id.current_scenario);
        // Extract properties from cursor
        String campaignName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                .COLUMN_CAMPAIGN_NAME));
        int currentCampaign = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                .COLUMN_CURRENT_CAMPAIGN));
        int currentScenario = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                .COLUMN_CURRENT_SCENARIO));
        // Populate fields with extracted properties
        campaignNameView.setText(campaignName);
        switch (currentCampaign) {
            case 1:
                currentCampaignView.setText(R.string.night_campaign_name);
                switch (currentScenario) {
                    case 1:
                        currentScenarioView.setText(R.string.night_scenario_one);
                        break;
                    case 2:
                        currentScenarioView.setText(R.string.night_scenario_two);
                        break;
                    case 3:
                        currentScenarioView.setText(R.string.night_scenario_three);
                        break;
                }
                break;
            case 2:
                currentCampaignView.setText(R.string.dunwich_campaign_name);
                switch (currentScenario) {
                    case 1:
                        currentScenarioView.setText(R.string.dunwich_scenario_one);
                        break;
                    case 2:
                        currentScenarioView.setText(R.string.dunwich_scenario_two);
                        break;
                    case 3:
                        currentScenarioView.setText(R.string.dunwich_interlude_one);
                        break;
                    case 4:
                        currentScenarioView.setText(R.string.dunwich_scenario_three);
                        break;
                    case 5:
                        currentScenarioView.setText(R.string.dunwich_scenario_four);
                        break;
                }
        }
    }
}
