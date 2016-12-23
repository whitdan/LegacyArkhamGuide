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
        // Set nothing to show if on first scenario
        if (globalVariables.getCurrentScenario() == 1) {
        } else {
            TextView campaignStart = (TextView) v.findViewById(R.id.log_campaign_start);
            campaignStart.setVisibility(GONE);
        }

        /*
        Night of the Zealot log
         */
        if (globalVariables.getCurrentCampaign() == 1) {
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
                if(globalVariables.getDrewInterrogated()==1){
                    cultistsInterrogated.append("'Wolf-Man' Drew\n");
                }
                else{
                    cultistsGotAway.append("'Wolf-Man' Drew\n");
                }
                if(globalVariables.getPeterInterrogated()==1){
                    cultistsInterrogated.append("Peter Warren\n");
                }
                else{
                    cultistsGotAway.append("Peter Warren\n");
                }
                if(globalVariables.getHermanInterrogated()==1){
                    cultistsInterrogated.append("Herman Collins\n");
                }
                else{
                    cultistsGotAway.append("Herman Collins\n");
                }
                if(globalVariables.getRuthInterrogated()==1){
                    cultistsInterrogated.append("Ruth Turner\n");
                }
                else{
                    cultistsGotAway.append("Ruth Turner\n");
                }
                if(globalVariables.getVictoriaInterrogated()==1){
                    cultistsInterrogated.append("Victoria Devereux\n");
                }
                else{
                    cultistsGotAway.append("Victoria Devereux\n");
                }
                if(globalVariables.getMaskedInterrogated()==1){
                    cultistsInterrogated.append("The Masked Hunter\n");
                }
                else{
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
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));

        return v;
    }
}
