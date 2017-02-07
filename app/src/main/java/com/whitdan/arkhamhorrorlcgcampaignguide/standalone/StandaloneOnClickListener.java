package com.whitdan.arkhamhorrorlcgcampaignguide.standalone;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign.SelectCampaignActivity;

/**
 * OnClickListener to finish standalone scenario
 */

public class StandaloneOnClickListener implements View.OnClickListener {

    private Context context;

    public StandaloneOnClickListener(Context mContext){
        context = mContext;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, SelectCampaignActivity.class);
        context.startActivity(intent);
    }
}
