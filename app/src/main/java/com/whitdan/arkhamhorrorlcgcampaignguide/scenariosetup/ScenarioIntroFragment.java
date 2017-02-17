package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.standalone.StandaloneOnClickListener;

/**
 * Displays the introductory flavour text for the scenario.
 */

public class ScenarioIntroFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro, container, false);
        GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();
        TextView textView = (TextView) v.findViewById(R.id.introductory_text);

        switch (globalVariables.getCurrentCampaign()) {
            // Night of the Zealot
            case 1:
                switch (globalVariables.getCurrentScenario()) {
                    case 1:
                        textView.setText(R.string.gathering_setup);
                        break;
                    case 2:
                        if (globalVariables.getLitaStatus() == 2) {
                            textView.setText(R.string.midnight_setup_a);
                        } else {
                            textView.setText(R.string.midnight_setup_b);
                        }
                        break;
                    case 3:
                        textView.setText(R.string.devourer_setup);
                        break;

                }
                break;
            // The Dunwich Legacy
            case 2:
                switch(globalVariables.getCurrentScenario()){
                    case 1:
                        textView.setText(R.string.extracurricular_setup);
                        break;
                    case 2:
                        textView.setText(R.string.house_setup);
                        break;
                    case 4:
                        if(globalVariables.getHenryArmitage()==0){
                            textView.setText(R.string.miskatonic_setup_a);
                        }
                        else{
                            textView.setText(R.string.miskatonic_setup_b);
                        }
                        break;
                }
        }

        // Side stories
        if(globalVariables.getCurrentScenario()>100){
            switch(globalVariables.getCurrentScenario()){
                case 101:
                    textView.setText(R.string.rougarou_setup);
                    break;
                case 102:
                    textView.setText(R.string.carnevale_setup);
                    break;
            }
        }


        // Standalone scenario
        if(globalVariables.getCurrentCampaign()==999){
            // Set click listener on continue button
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new StandaloneOnClickListener(this.getActivity()));
        } else{
        // Set continue button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));}

        return v;
    }
}
