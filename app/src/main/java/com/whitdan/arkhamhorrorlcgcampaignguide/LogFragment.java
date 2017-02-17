package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.standalone.StandaloneOnClickListener;

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
         Set setup instructions if on setup or in standalone scenario
          */
        if ((globalVariables.getScenarioStage() == 1 || globalVariables.getCurrentCampaign() == 999)
                && !(globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 5)) {

            // Get the various views and set the visibility of the LinearLayout to VISIBLE
            LinearLayout setup = (LinearLayout) v.findViewById(R.id.setup);
            TextView sets = (TextView) v.findViewById(R.id.sets);
            ImageView setsImage = (ImageView) v.findViewById(R.id.sets_image);
            TextView setsTwo = (TextView) v.findViewById(R.id.sets_two);
            ImageView setsTwoImage = (ImageView) v.findViewById(R.id.sets_two_image);
            TextView setAside = (TextView) v.findViewById(R.id.set_aside);
            ImageView setAsideImage = (ImageView) v.findViewById(R.id.set_aside_image);
            TextView setAsideTwo = (TextView) v.findViewById(R.id.set_aside_two);
            TextView locations = (TextView) v.findViewById(R.id.starting_locations);
            TextView additional = (TextView) v.findViewById(R.id.additional_instructions);
            setup.setVisibility(VISIBLE);

            switch (globalVariables.getCurrentCampaign()) {
                // Night of the Zealot
                case 1:
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
                            if (midnightAdditionalBuilder.length() == 0) {
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
                            devouringAdditionalBuilder.append(getString(R.string.devourer_additional));
                            // Check how many cultists were interrogated
                            switch (globalVariables.getCultistsInterrogated()) {
                                case 6:
                                    break;
                                case 5:
                                case 4:
                                    devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_one));
                                    break;
                                case 3:
                                case 2:
                                    devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_two));
                                    break;
                                case 1:
                                case 0:
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
                            if (devouringAdditionalBuilder.length() == 0) {
                                devouringAdditionalBuilder.append(getString(R.string.no_changes));
                            }
                            // Show the additional text
                            String devouringAdditional = devouringAdditionalBuilder.toString();
                            additional.setText(devouringAdditional);
                            break;
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    switch (globalVariables.getCurrentScenario()) {
                        // Extracurricular activity
                        case 1:
                            sets.setText(R.string.extracurricular_sets);
                            setsImage.setImageResource(R.drawable.extracurricular_sets);
                            //setsImage.setImageResource(R.drawable.extracurricular_sets);
                            if (globalVariables.getFirstScenario() == 1) {
                                setAside.setText(R.string.extracurricular_set_aside_one);
                            } else {
                                setAside.setText(R.string.extracurricular_set_aside_two);
                            }
                            locations.setText(R.string.extracurricular_locations);
                            additional.setText(R.string.no_changes);
                            break;
                        // The House Always Wins
                        case 2:
                            sets.setText(R.string.house_sets);
                            setsImage.setImageResource(R.drawable.house_sets);
                            setAside.setText(R.string.house_set_aside);
                            setAsideImage.setVisibility(VISIBLE);
                            setAsideImage.setImageResource(R.drawable.house_set_aside);
                            setAsideTwo.setVisibility(VISIBLE);
                            setAsideTwo.setText(R.string.house_set_aside_two);
                            locations.setText(R.string.house_locations);
                            additional.setText(R.string.house_additional);
                            break;
                        // The Miskatonic Museum
                        case 4:
                            sets.setText(R.string.miskatonic_sets);
                            setsImage.setImageResource(R.drawable.miskatonic_sets);
                            setAside.setText(R.string.miskatonic_set_aside);
                            locations.setText(R.string.miskatonic_locations);
                            additional.setText(R.string.miskatonic_additional);
                            break;
                    }
                    break;
            }
            // Side stories
            if(globalVariables.getCurrentScenario()>100){
                switch(globalVariables.getCurrentScenario()){
                    case 101:
                        sets.setText(R.string.rougarou_sets);
                        setsImage.setImageResource(R.drawable.rougarou_sets);
                        setAside.setText(R.string.rougarou_set_aside);
                        setAsideImage.setVisibility(VISIBLE);
                        setAsideImage.setImageResource(R.drawable.rougarou_set_aside);
                        setAsideTwo.setVisibility(VISIBLE);
                        setAsideTwo.setText(R.string.rougarou_set_aside_two);
                        locations.setText(R.string.rougarou_locations);
                        additional.setText(R.string.no_changes);
                        break;
                    case 102:
                        sets.setText(R.string.carnevale_sets);
                        setsImage.setImageResource(R.drawable.carnevale_sets);
                        setAside.setText(R.string.carnevale_set_aside);
                        locations.setText(R.string.carnevale_locations);
                        additional.setText(R.string.carnevale_additional);
                        break;
                }
            }
        }

        // Standalone scenario
        if(globalVariables.getCurrentCampaign()==999){
            LinearLayout logLayout = (LinearLayout) v.findViewById(R.id.log_layout);
            logLayout.setVisibility(GONE);
            // Set click listener on continue button
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new StandaloneOnClickListener(this.getActivity()));
            return v;
        }

        StringBuilder campaignLogBuilder = new StringBuilder();

        if(globalVariables.getCurrentScenario()<100){
            globalVariables.setPreviousScenario(globalVariables.getCurrentScenario());
        }

        /*
        Night of the Zealot log
         */
        if (globalVariables.getCurrentCampaign() == 1) {
            // First scenario log
            if (globalVariables.getPreviousScenario() > 1) {
                // Set house status
                if (globalVariables.getHouseStanding() == 1) {
                    campaignLogBuilder.append(getString(R.string.house_standing));
                } else if (globalVariables.getHouseStanding() == 0) {
                    campaignLogBuilder.append(getString(R.string.house_burned));
                }
                // Set ghoul priest status
                if (globalVariables.getGhoulPriestAlive() == 1) {
                    campaignLogBuilder.append(getString(R.string.ghoul_priest_alive));
                }
                // Set Lita status
                if (globalVariables.getLitaStatus() == 2) {
                    campaignLogBuilder.append(getString(R.string.lita_forced));
                }
            }
            // Second scenario log
            if (globalVariables.getPreviousScenario() > 2) {
                // Set midnight status
                if (globalVariables.getMidnightStatus() == 1) {
                    campaignLogBuilder.append(getString(R.string.past_midnight));
                }

                // Set cultists
                LinearLayout cultistsView = (LinearLayout) v.findViewById(R.id.cultists_view);
                cultistsView.setVisibility(VISIBLE);
                StringBuilder cultistsInterrogated = new StringBuilder();
                StringBuilder cultistsGotAway = new StringBuilder();
                if (globalVariables.getDrewInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.drew) + "\n");
                } else if (globalVariables.getDrewInterrogated() == 0) {
                    cultistsGotAway.append(getString(R.string.drew) + "\n");
                }
                if (globalVariables.getPeterInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.peter) + "\n");
                } else if (globalVariables.getPeterInterrogated() == 0) {
                    cultistsGotAway.append(getString(R.string.peter) + "\n");
                }
                if (globalVariables.getHermanInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.herman) + "\n");
                } else if (globalVariables.getHermanInterrogated() == 0) {
                    cultistsGotAway.append(getString(R.string.herman) + "\n");
                }
                if (globalVariables.getRuthInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.ruth) + "\n");
                } else if (globalVariables.getRuthInterrogated() == 0) {
                    cultistsGotAway.append(getString(R.string.ruth) + "\n");
                }
                if (globalVariables.getVictoriaInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.victoria) + "\n");
                } else if (globalVariables.getVictoriaInterrogated() == 0) {
                    cultistsGotAway.append(getString(R.string.victoria) + "\n");
                }
                if (globalVariables.getMaskedInterrogated() == 1) {
                    cultistsInterrogated.append(getString(R.string.masked_hunter) + "\n");
                } else if (globalVariables.getMaskedInterrogated() == 0) {
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

        /*
            Dunwich Legacy log
         */
        else if (globalVariables.getCurrentCampaign() == 2) {
            if (globalVariables.getInvestigatorsUnconscious() == 1) {
                campaignLogBuilder.append(getString(R.string.investigators_unconscious));
            }
            // Extracurricular Activity log
            if ((globalVariables.getPreviousScenario() > 1 && globalVariables.getFirstScenario() == 1) ||
                    globalVariables.getPreviousScenario() > 2) {
                if (globalVariables.getWarrenRice() == 0) {
                    campaignLogBuilder.append(getString(R.string.warren_kidnapped));
                } else if (globalVariables.getWarrenRice() == 1) {
                    campaignLogBuilder.append(getString(R.string.warren_rescued));
                }
                if (globalVariables.getStudents() == 0) {
                    campaignLogBuilder.append(getString(R.string.students_failed));
                } else if (globalVariables.getStudents() == 1) {
                    campaignLogBuilder.append(getString(R.string.students_rescued));
                } else if (globalVariables.getStudents() == 2) {
                    campaignLogBuilder.append(getString(R.string.experiment_defeated));
                }
            }
            // The House Always Wins log
            if ((globalVariables.getPreviousScenario() == 1 && globalVariables.getFirstScenario() == 2) ||
                    globalVariables.getPreviousScenario() > 2) {
                if (globalVariables.getFrancisMorgan() == 0) {
                    campaignLogBuilder.append(getString(R.string.morgan_kidnapped));
                } else if (globalVariables.getFrancisMorgan() == 1) {
                    campaignLogBuilder.append(getString(R.string.morgan_rescued));
                }
                if (globalVariables.getObannionGang() == 0) {
                    campaignLogBuilder.append(getString(R.string.obannion_failed));
                } else if (globalVariables.getObannionGang() == 1) {
                    campaignLogBuilder.append(getString(R.string.obannion_succeeded));
                }
                if (globalVariables.getInvestigatorsCheated() == 1) {
                    campaignLogBuilder.append(getString(R.string.cheated));
                }
            }
            // Interlude log
            if(globalVariables.getPreviousScenario() > 3){
                if(globalVariables.getHenryArmitage()==0){
                    campaignLogBuilder.append(getString(R.string.armitage_kidnapped));
                } else if(globalVariables.getHenryArmitage()==1){
                    campaignLogBuilder.append(getString(R.string.armitage_rescued));
                }
            }
            // The Miskatonic Museum log
            if(globalVariables.getPreviousScenario() > 4){
                if(globalVariables.getNecronomicon()==0){
                    campaignLogBuilder.append(getString(R.string.necronomicon_failed));
                } else if(globalVariables.getNecronomicon()==1){
                    campaignLogBuilder.append(getString(R.string.necronomicon_destroyed));
                } else if(globalVariables.getNecronomicon()==2){
                    campaignLogBuilder.append(getString(R.string.necronomicon_taken));
                }
            }
        }

        /*
            Side story log
         */
        if(globalVariables.getRougarouStatus()==1){
            campaignLogBuilder.append(getString(R.string.rougarou_alive));
        }else if (globalVariables.getRougarouStatus()==2){
            campaignLogBuilder.append(getString(R.string.rougarou_destroyed));
        }else if(globalVariables.getRougarouStatus()==3){
            campaignLogBuilder.append(getString(R.string.rougarou_escaped));
        }
        if(globalVariables.getCarnevaleStatus()==1){
            campaignLogBuilder.append(getString(R.string.carnevale_many_sacrificed));
        }else if(globalVariables.getCarnevaleStatus()==2){
            campaignLogBuilder.append(getString(R.string.carnevale_banished));
        }else if(globalVariables.getCarnevaleStatus()==3){
            campaignLogBuilder.append(getString(R.string.carnevale_retreated));
        }
        if(globalVariables.getCarnevaleReward()==1){
            campaignLogBuilder.append(getString(R.string.carnevale_sacrifice_made));
        }else if(globalVariables.getCarnevaleReward()==2){
            campaignLogBuilder.append(getString(R.string.carnevale_abbess_satisfied));
        }

        /*
            Player cards log
         */
        if(globalVariables.getStrangeSolution()==1){
            campaignLogBuilder.append(getString(R.string.strange_solution));
        }

        String campaignLog = campaignLogBuilder.toString();
        TextView campaignLogView = (TextView) v.findViewById(R.id.log_campaign);
        if (campaignLog.length() > 0) {
            campaignLogView.setText(campaignLog);
        }

        // Set click listener on continue button
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this.getActivity()));

        return v;
    }
}
