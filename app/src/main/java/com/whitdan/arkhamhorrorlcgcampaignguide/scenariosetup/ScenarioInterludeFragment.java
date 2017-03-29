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

/**
 * Fragment for interludes
 */

public class ScenarioInterludeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_interlude, container, false);
        final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();

        // Set interlude text
        TextView interlude = (TextView) v.findViewById(R.id.interlude_text);
        if (globalVariables.getCurrentScenario() == 1000) {
            switch (globalVariables.getCurrentCampaign()) {
                case 1:
                    interlude.setText(R.string.night_setup);
                    break;
                case 2:
                    interlude.setText(R.string.dunwich_setup);
                    break;
            }
        }
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 3) {
            if (globalVariables.getInvestigatorsUnconscious() == 1) {
                interlude.setText(R.string.dunwich_interlude_one_text);
                globalVariables.setHenryArmitage(0);
                for (int i = 0; i < globalVariables.investigators.size(); i++) {
                    globalVariables.investigators.get(i).changeXP(2);
                }
            } else if (globalVariables.getInvestigatorsUnconscious() == 0) {
                interlude.setText(R.string.dunwich_interlude_two_text);
                globalVariables.setHenryArmitage(1);
            }
        }

        // Set onClickListener on the continue button
        TextView continueButton = (TextView) v.findViewById(R.id.continue_button);
        if (globalVariables.getCurrentScenario() == 1000) {
            continueButton.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this
                    .getActivity()));
        } else {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalVariables.setCurrentScenario(globalVariables.getCurrentScenario() + 1);
                    ((ScenarioSetupActivity) getActivity()).interludeRestartScenario(getActivity());
                }
            });
        }

        return v;
    }
}
