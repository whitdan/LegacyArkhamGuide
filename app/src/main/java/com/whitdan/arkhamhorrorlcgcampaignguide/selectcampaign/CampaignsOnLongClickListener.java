package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Calls a dialog box when item is long clicked, to allow deleting saved campaigns.
 */

class CampaignsOnLongClickListener implements AdapterView.OnItemLongClickListener {

    private final Activity activity;

    CampaignsOnLongClickListener(FragmentActivity fragmentActivity) {
        activity = fragmentActivity;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Create new dialog fragment
        DeleteCampaignDialogFragment newFragment = new DeleteCampaignDialogFragment();
        // Pass the position of the item long clicked to the fragment
        Bundle bundle = new Bundle();
        int pos = (int) id;
        bundle.putInt("pos", pos);
        newFragment.setArguments(bundle);
        // Show the dialog fragment
        newFragment.show(activity.getFragmentManager(), "delete");

        return true;
    }
}
