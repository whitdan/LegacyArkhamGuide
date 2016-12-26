package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Shows the campaign log, as well as setup instructions (if on scenario setup).
 */

public class LogFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log, container, false);

        GlobalVariables globalVariables = ((GlobalVariables) this.getActivity().getApplication());

        /*
         Set setup instructions if on setup
          */
        if (globalVariables.getScenarioStage() == 1) {

            // Get the various views and set the visibility of the LinearLayout to VISIBLE
            LinearLayout setup = (LinearLayout) v.findViewById(R.id.setup);
            TextView sets = (TextView) v.findViewById(R.id.sets);
            ImageView setsImage = (ImageView) v.findViewById(R.id.sets_image);
            TextView setsTwo = (TextView) v.findViewById(R.id.sets_two);
            ImageView setsTwoImage = (ImageView) v.findViewById(R.id.sets_two_image);
            TextView setAside = (TextView) v.findViewById(R.id.set_aside);
            ImageView setAsideImage = (ImageView) v.findViewById(R.id.set_aside_image);
            TextView locations = (TextView) v.findViewById(R.id.starting_locations);
            TextView additional = (TextView) v.findViewById(R.id.additional_instructions);
            setup.setVisibility(VISIBLE);

            switch (globalVariables.getCurrentScenario()) {
                // Scenario One - The Gathering
                case 1:
                    sets.setText(R.string.gathering_sets);
                    setsImage.setImageResource(R.drawable.gathering_sets);
                    setAside.setText(R.string.gathering_set_aside);
                    locations.setText(R.string.gathering_locations);
                    additional.setText(R.string.no_changes);
                    break;
                // Scenario Two - The Midnight Masks
                case 2:
                    sets.setText(R.string.midnight_sets);
                    setsImage.setImageResource(R.drawable.midnight_sets);
                    setAside.setText(R.string.midnight_set_aside);
                    setAsideImage.setImageResource(R.drawable.cult_set);
                    setAsideImage.setVisibility(VISIBLE);
                    // Check if house is standing
                    if (globalVariables.getHouseStanding() == 1) {
                        locations.setText(R.string.midnight_locations);
                    } else {
                        locations.setText(R.string.midnight_locations_no_house);
                    }
                    // StringBuilder for the additional instructions
                    StringBuilder midnightAdditionalBuilder = new StringBuilder();
                    // Check how many players
                    switch (globalVariables.investigators.size()) {
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
                    // Check if Ghoul Priest is alive
                    if (globalVariables.getGhoulPriestAlive() == 1) {
                        midnightAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                    }
                    if(midnightAdditionalBuilder.length()==0){
                        midnightAdditionalBuilder.append(getString(R.string.no_changes));
                    }
                    // Show additional instructions
                    String midnightAdditional = midnightAdditionalBuilder.toString();
                    additional.setText(midnightAdditional);
                    break;
                case 3:
                    sets.setText(R.string.devourer_sets);
                    setsImage.setImageResource(R.drawable.devourer_sets);
                    setsTwo.setText(R.string.devourer_sets_two);
                    setsTwoImage.setImageResource(R.drawable.devourer_sets_two);
                    setsTwo.setVisibility(VISIBLE);
                    setsTwoImage.setVisibility(VISIBLE);
                    setAside.setText(R.string.devourer_set_aside);
                    locations.setText(R.string.devourer_locations);
                    // StringBuilder for the additional instructions
                    StringBuilder devouringAdditionalBuilder = new StringBuilder();
                    // Check how many cultists were interrogated
                    switch (globalVariables.getCultistsInterrogated()) {
                        case 0:
                            break;
                        case 1:
                        case 2:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_one));
                            break;
                        case 3:
                        case 4:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_two));
                            break;
                        case 5:
                        case 6:
                            devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_three));
                            break;
                    }
                    // Check if it is past midnight
                    if (globalVariables.getMidnightStatus() == 1) {
                        devouringAdditionalBuilder.append(getString(R.string.devourer_additional_midnight));
                    }
                    // Check if the Ghoul Priest is alive
                    if (globalVariables.getGhoulPriestAlive() == 1) {
                        devouringAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                    }
                    if(devouringAdditionalBuilder.length()==0){
                        devouringAdditionalBuilder.append(getString(R.string.no_changes));
                    }
                    // Show the additional text
                    String devouringAdditional = devouringAdditionalBuilder.toString();
                    additional.setText(devouringAdditional);
                    break;
            }
        }

        // Hide nothing to show if past current scenario
        if (globalVariables.getCurrentScenario() != 1) {
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
                    houseStatus.setText(R.string.house_standing);
                } else {
                    houseStatus.setText(R.string.house_burned);
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
                    cultistsInterrogated.append(getString(R.string.drew) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.drew) + "\n");
                }
                if (globalVariables.getPeterInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.peter) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.peter) + "\n");
                }
                if (globalVariables.getHermanInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.herman) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.herman) + "\n");
                }
                if (globalVariables.getRuthInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.ruth) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.ruth) + "\n");
                }
                if (globalVariables.getVictoriaInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.victoria) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.victoria) + "\n");
                }
                if (globalVariables.getMaskedInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.masked_hunter) + "\n");
                } else {
                    cultistsGotAway.append(getString(R.string.masked_hunter) + "\n");
                }
                String allCultistsInterrogated = cultistsInterrogated.toString();
                String allCultistsGotAway = cultistsGotAway.toString();
                TextView cultistsInterrogatedView = (TextView) v.findViewById(R.id.cultists_interrogated);
                cultistsInterrogatedView.setText(allCultistsInterrogated);
                TextView cultistsGotAwayView = (TextView) v.findViewById(R.id.cultists_got_away);
                cultistsGotAwayView.setText(allCultistsGotAway);
            }
        }

        // Set click listener on continue button
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this.getActivity()));

        return v;
    }
}
