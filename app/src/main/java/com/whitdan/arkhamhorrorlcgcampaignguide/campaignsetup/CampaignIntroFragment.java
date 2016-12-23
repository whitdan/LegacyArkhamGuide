package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * Displays the introductory flavour text for the campaign.
 */

public class CampaignIntroFragment extends Fragment {

    GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        TextView textView = (TextView) v.findViewById(R.id.introductory_text);
        if(globalVariables.getCurrentCampaign()==1)
        {
            textView.setText(R.string.night_setup);
        }

        // Set button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CampaignSetupActivity)getActivity()).startScenario(v);
            }
        });

        return v;
    }
}
