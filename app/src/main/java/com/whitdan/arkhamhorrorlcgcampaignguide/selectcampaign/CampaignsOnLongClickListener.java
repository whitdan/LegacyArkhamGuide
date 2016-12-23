package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by danie on 23/12/2016.
 */

public class CampaignsOnLongClickListener implements AdapterView.OnItemLongClickListener {

    private Activity activity;

    public CampaignsOnLongClickListener(FragmentActivity activ) {
        activity = activ;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DeleteCampaignDialogFragment newFragment = new DeleteCampaignDialogFragment();
        Bundle bundle = new Bundle();
        int pos = (int) id;
        bundle.putInt("pos", pos);
        newFragment.setArguments(bundle);
        newFragment.show(activity.getFragmentManager(), "delete");

        return true;
    }
}
