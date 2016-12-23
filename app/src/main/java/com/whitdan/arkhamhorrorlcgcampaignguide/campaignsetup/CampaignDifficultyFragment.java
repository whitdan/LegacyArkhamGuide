package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * TODO: Allows the selection of a difficulty and displays the relevant chaos bag makeup.
 */

public class CampaignDifficultyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campaign_difficulty, container, false);
    }
}
