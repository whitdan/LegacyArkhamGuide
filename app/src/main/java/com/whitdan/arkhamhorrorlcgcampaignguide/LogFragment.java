package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by danie on 12/12/2016.
 */

public class LogFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log, container, false);

        GlobalVariables globalVariables = ((GlobalVariables) this.getActivity().getApplication());

        // Set setup instructions if on setup
        if (globalVariables.getScenarioStage() == 1) {
            LinearLayout setup = (LinearLayout) v.findViewById(R.id.setup);
            TextView sets = (TextView) v.findViewById(R.id.sets);
            TextView setAside = (TextView) v.findViewById(R.id.set_aside);
            TextView locations = (TextView) v.findViewById(R.id.starting_locations);
            TextView additional = (TextView) v.findViewById(R.id.additional_instructions);
            setup.setVisibility(VISIBLE);

            switch (globalVariables.getCurrentScenario()) {
                case 1:
                    sets.setText(R.string.gathering_sets);
                    setAside.setText(R.string.gathering_set_aside);
                    locations.setText(R.string.gathering_locations);
                    additional.setVisibility(GONE);
                    break;
                case 2:
                    sets.setText(R.string.midnight_sets);
                    setAside.setText(R.string.midnight_set_aside);
                    if (globalVariables.getHouseStanding() == 1) {
                        locations.setText(R.string.midnight_locations);
                    } else {
                        locations.setText(R.string.midnight_locations_no_house);
                    }
                    StringBuilder midnightAdditionalBuilder = new StringBuilder();
                    switch(globalVariables.investigators.size()){
                        case 1:
                            break;
                        case 2:
                            midnightAdditionalBuilder.append(getString(R.string.midnight_additional_two));
                            break;
                        case 3:
                            midnightAdditionalBuilder.append(getString(R.string.midnight_additional_three));
                            break;
                        case 4:
                            midnightAdditionalBuilder.append(getString(R.string.midnight_additional_four));
                            break;
                    }
                    if(globalVariables.getGhoulPriestAlive()==1){
                        midnightAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                    }
                    String midnightAdditional = midnightAdditionalBuilder.toString();
                    if(midnightAdditionalBuilder.length() > 0){
                    additional.setText(midnightAdditional);}
                    else{
                        additional.setVisibility(GONE);
                    }
                    break;
                case 3:
                    sets.setText(R.string.devourer_sets);
                    setAside.setText(R.string.devourer_set_aside);
                    locations.setText(R.string.devourer_locations);
                    StringBuilder devouringAdditionalBuilder = new StringBuilder();
                    switch(globalVariables.getCultistsInterrogated()){
                        case 0:
                            break;
                        case 1:case 2:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_one));
                            break;
                        case 3:case 4:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_two));
                            break;
                        case 5:case 6:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_three));
                            break;
                    }
                    if(globalVariables.getMidnightStatus()==1){
                        devouringAdditionalBuilder.append(getString(R.string.devourer_additional_midnight));
                    }
                    if(globalVariables.getGhoulPriestAlive()==1){
                        devouringAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                    }
                    String devouringAdditional = devouringAdditionalBuilder.toString();
                    if(devouringAdditionalBuilder.length() > 0){
                        additional.setText(devouringAdditional);}
                    else{
                        additional.setVisibility(GONE);
                    }                    break;
            }
        }

        // Set nothing to show if on first scenario
        if (globalVariables.getCurrentScenario() == 1)

        {
        } else

        {
            TextView campaignStart = (TextView) v.findViewById(R.id.log_campaign_start);
            campaignStart.setVisibility(GONE);
        }

        /*
        Night of the Zealot log
         */
        if (globalVariables.getCurrentCampaign() == 1)

        {
            // First scenario log
            if (globalVariables.getCurrentScenario() > 1) {
                // Set house status
                TextView houseStatus = (TextView) v.findViewById(R.id.log_house_status);
                houseStatus.setVisibility(VISIBLE);
                if (globalVariables.getHouseStanding() == 1) {
                    houseStatus.setText("Your house is still standing.");
                } else {
                    houseStatus.setText("Your house has burned to the ground.");
                }
                // Set ghoul priest status
                if (globalVariables.getGhoulPriestAlive() == 1) {
                    TextView priestStatus = (TextView) v.findViewById(R.id.log_ghoul_priest);
                    priestStatus.setVisibility(VISIBLE);
                }
                // Set Lita status
                if (globalVariables.getLitaStatus() == 2) {
                    TextView litaStatus = (TextView) v.findViewById(R.id.log_lita_status);
                    litaStatus.setVisibility(VISIBLE);
                }
            }
            // Second scenario log
            if (globalVariables.getCurrentScenario() > 2) {
                // Set midnight status
                if (globalVariables.getMidnightStatus() == 1) {
                    TextView midnightStatus = (TextView) v.findViewById(R.id.log_midnight_status);
                    midnightStatus.setVisibility(VISIBLE);
                }

                // Set cultists
                LinearLayout cultistsView = (LinearLayout) v.findViewById(R.id.cultists_view);
                cultistsView.setVisibility(VISIBLE);
                StringBuilder cultistsInterrogated = new StringBuilder();
                StringBuilder cultistsGotAway = new StringBuilder();
                if (globalVariables.getDrewInterrogated() == 1) {
                    cultistsInterrogated.append("'Wolf-Man' Drew\n");
                } else {
                    cultistsGotAway.append("'Wolf-Man' Drew\n");
                }
                if (globalVariables.getPeterInterrogated() == 1) {
                    cultistsInterrogated.append("Peter Warren\n");
                } else {
                    cultistsGotAway.append("Peter Warren\n");
                }
                if (globalVariables.getHermanInterrogated() == 1) {
                    cultistsInterrogated.append("Herman Collins\n");
                } else {
                    cultistsGotAway.append("Herman Collins\n");
                }
                if (globalVariables.getRuthInterrogated() == 1) {
                    cultistsInterrogated.append("Ruth Turner\n");
                } else {
                    cultistsGotAway.append("Ruth Turner\n");
                }
                if (globalVariables.getVictoriaInterrogated() == 1) {
                    cultistsInterrogated.append("Victoria Devereux\n");
                } else {
                    cultistsGotAway.append("Victoria Devereux\n");
                }
                if (globalVariables.getMaskedInterrogated() == 1) {
                    cultistsInterrogated.append("The Masked Hunter\n");
                } else {
                    cultistsGotAway.append("The Masked Hunter\n");
                }
                String allCultistsInterrogated = cultistsInterrogated.toString();
                String allCultistsGotAway = cultistsGotAway.toString();
                TextView cultistsInterrogatedView = (TextView) v.findViewById(R.id.cultists_interrogated);
                cultistsInterrogatedView.setText(allCultistsInterrogated);
                TextView cultistsGotAwayView = (TextView) v.findViewById(R.id.cultists_got_away);
                cultistsGotAwayView.setText(allCultistsGotAway);
            }
        }

        // Set button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new

                ContinueOnClickListener(globalVariables, this.getActivity()

        ));

        return v;
    }
}
