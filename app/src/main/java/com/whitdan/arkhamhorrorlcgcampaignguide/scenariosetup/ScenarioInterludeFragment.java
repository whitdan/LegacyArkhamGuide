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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
        TextView interludeTwo = (TextView) v.findViewById(R.id.interlude_text_two);
        TextView interludeThree = (TextView) v.findViewById(R.id.interlude_text_three);
        TextView interludeFour = (TextView) v.findViewById(R.id.interlude_text_four);
        TextView interludeFive = (TextView) v.findViewById(R.id.interlude_text_five);
        TextView interludeSix = (TextView) v.findViewById(R.id.interlude_text_six);
        TextView interludeSeven = (TextView) v.findViewById(R.id.interlude_text_seven);
        interludeTwo.setVisibility(GONE);
        interludeThree.setVisibility(GONE);
        interludeFour.setVisibility(GONE);
        interludeFive.setVisibility(GONE);
        interludeSix.setVisibility(GONE);
        interludeSeven.setVisibility(GONE);
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
        if(globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 7){
            boolean powder = false;
            interlude.setText(R.string.survivors_interlude_text);
            if(globalVariables.getHenryArmitage() == 3){
                interludeTwo.setVisibility(VISIBLE);
                interludeTwo.setText(R.string.survivors_interlude_armitage);
                powder = true;
            }
            if(globalVariables.getWarrenRice() == 3){
                interludeThree.setVisibility(VISIBLE);
                interludeThree.setText(R.string.survivors_interlude_rice);
                powder = true;
            }
            if(globalVariables.getFrancisMorgan() == 3){
                interludeFour.setVisibility(VISIBLE);
                interludeFour.setText(R.string.survivors_interlude_morgan);
                powder = true;
            }
            if(powder){
                interludeFive.setVisibility(VISIBLE);
                interludeFive.setText(R.string.survivors_interlude_powder);
            }
            if(globalVariables.getZebulonWhateley() == 0){
                interludeSix.setVisibility(VISIBLE);
                interludeSix.setText(R.string.survivors_interlude_zebulon);
            }
            if(globalVariables.getEarlSawyer() == 0){
                interludeSeven.setVisibility(VISIBLE);
                interludeSeven.setText(R.string.survivors_interlude_sawyer);
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
                    ((ScenarioSetupActivity) getActivity()).interludeRestartScenario();
                }
            });
        }

        return v;
    }
}
