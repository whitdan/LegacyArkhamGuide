package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

/**
 * Created by danie on 12/12/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * Displays the introductory flavour text for the scenario.
 */

public class ScenarioIntroFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro, container, false);
        GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();
        TextView textView = (TextView) v.findViewById(R.id.introductory_text);

        if(globalVariables.getCurrentCampaign()==1)
        {
            switch(globalVariables.getCurrentScenario()){
                case 1:
                    textView.setText(R.string.gathering_setup);
                    break;
                case 2:
                    if(globalVariables.getLitaStatus() == 2){
                        textView.setText(getString(R.string.midnight_setup_a) + getString(R.string.midnight_setup));
                    }
                    else{
                        textView.setText(getString(R.string.midnight_setup_b) + getString(R.string.midnight_setup));
                    }
                    break;
                case 3:
                    textView.setText(R.string.devourer_setup);
                    break;
            }
        }

        // Set button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));

        return v;
    }
}
