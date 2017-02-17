package com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.standalone.StandaloneOnClickListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Allows the user to select the relevant resolution and any additional necessary information to resolve a scenario
 * (including the value of the victory display)
 */

public class FinishResolutionFragment extends Fragment {

    private GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_finish_resolution, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        /*
         Setup resolution choice spinner
        */
        Spinner resolutionSpinner = (Spinner) v.findViewById(R.id.resolution_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.resolutions_three, android.R.layout.simple_spinner_item);;
        // Setup the right number of resolutions in the adapter
        switch(globalVariables.getCurrentCampaign()){
            // Night of the Zealot
            case 1:
                switch(globalVariables.getCurrentScenario()){
                    case 1:
                    case 3:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_three, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_two, android.R.layout.simple_spinner_item);
                        break;
                }
                break;
            // The Dunwich Legacy
            case 2:
                switch(globalVariables.getCurrentScenario()){
                    case 1:
                    case 2:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_four, android.R.layout.simple_spinner_item);
                        break;
                    case 4:
                        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                                R.array.resolutions_two, android.R.layout.simple_spinner_item);
                        break;
                }
                break;
        }
        // Side stories
        if(globalVariables.getCurrentScenario()>100){
            switch(globalVariables.getCurrentScenario()){
                case 101:
                    adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.resolutions_three, android.R.layout.simple_spinner_item);
                    break;
                case 102:
                    adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.resolutions_two, android.R.layout.simple_spinner_item);
                    break;

            }
        }
        // Set the layout, adapter and OnItemSelectedListener to the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSpinner.setAdapter(adapter);
        resolutionSpinner.setOnItemSelectedListener(new ResolutionSpinnerListener());

        /*
         Setup victory display view
        */
        // Set value to the current victory display amount
        final TextView victoryDisplay = (TextView) v.findViewById(R.id.victory_display);
        victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
        // Setup decrement button to reduce the victory display value and display the new amount
        Button victoryDecrement = (Button) v.findViewById(R.id.victory_decrement);
        victoryDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = globalVariables.getVictoryDisplay();
                if (current > 0) {
                    globalVariables.setVictoryDisplay(current - 1);
                    victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
                }
            }
        });
        // Setup increment button to increase the victory display value and display the new amount
        Button victoryIncrement = (Button) v.findViewById(R.id.victory_increment);
        victoryIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = globalVariables.getVictoryDisplay();
                if (current < 99) {
                    globalVariables.setVictoryDisplay(current + 1);
                    victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
                }
            }
        });

        // If on first campaign, second scenario, set cultists view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() == 2) {
            LinearLayout cultists = (LinearLayout) v.findViewById(R.id.cultists_interrogated);
            cultists.setVisibility(VISIBLE);
        }

        // If on first campaign, second or third scenario and Ghoul Priest is alive, set ghoul priest view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() > 1 && globalVariables
                .getGhoulPriestAlive() == 1 && globalVariables.getCurrentScenario() < 100) {
            CheckBox ghoulPriest = (CheckBox) v.findViewById(R.id.ghoul_priest_killed);
            ghoulPriest.setVisibility(VISIBLE);
        }

        // If on second campaign, second scenario, set cheated view to visible
        if(globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 2){
            CheckBox cheated = (CheckBox) v.findViewById(R.id.cheated_checkbox);
            final TextView cheatedText = (TextView) v.findViewById(R.id.cheated_text);
            cheated.setVisibility(VISIBLE);
            cheated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cheatedText.setVisibility(VISIBLE);
                    }
                    if(!isChecked){
                        cheatedText.setVisibility(GONE);
                    }
                }
            });
        }

        // If on Carnevale of Horrors, set rewards display to visible and setup rewards display
        if(globalVariables.getCurrentScenario()==102){
            final LinearLayout rewards = (LinearLayout) v.findViewById(R.id.rewards_view);
            rewards.setVisibility(VISIBLE);
            Spinner rewardsSpinner = (Spinner) v.findViewById(R.id.rewards_selection);
            ArrayAdapter<CharSequence> rewardsAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.carnevale_rewards, android.R.layout.simple_spinner_item);;
            rewardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rewardsSpinner.setAdapter(rewardsAdapter);
            rewardsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView rewardsText = (TextView) v.findViewById(R.id.rewards_text);
                    switch(position){
                        case 0:
                            rewardsText.setText(R.string.carnevale_no_rewards);
                            globalVariables.setCarnevaleReward(0);
                            break;
                        case 1:
                            rewardsText.setText(R.string.carnevale_reward_sacrifice);
                            globalVariables.setCarnevaleReward(1);
                            break;
                        case 2:
                            rewardsText.setText(R.string.carnevale_reward_abbess);
                            globalVariables.setCarnevaleReward(2);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
       }

        // Standalone scenario
        if(globalVariables.getCurrentCampaign()==999){
            // Hide victory display
            LinearLayout victory = (LinearLayout) v.findViewById(R.id.victory_layout);
            victory.setVisibility(GONE);
            // Set click listener on continue button
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new StandaloneOnClickListener(this.getActivity()));
        } else{
            // Set continue button click listener
            TextView button = (TextView) v.findViewById(R.id.continue_button);
            button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this.getActivity()));}

        return v;
    }


    /*
     OnItemSelectedListener for the Resolution Spinner - Applies relevant resolution text and sets the resolution value
      */
    private class ResolutionSpinnerListener extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            TextView resolutionText = (TextView) view.getRootView().findViewById(R.id.resolution_text);
            switch (globalVariables.getCurrentCampaign()) {
                // Night of the Zealot
                case 1:
                    // Scenario One - The Gathering
                    if (globalVariables.getCurrentScenario() == 1) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.gathering_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            // Resolution one
                            case 1:
                                resolutionText.setText(R.string.gathering_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            // Resolution two
                            case 2:
                                resolutionText.setText(R.string.gathering_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            // Resolution three
                            case 3:
                                resolutionText.setText(R.string.gathering_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                    }
                    // Scenario Two - The Midnight Masks
                    else if (globalVariables.getCurrentScenario() == 2) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.midnight_resolution_one);
                                globalVariables.setResolution(0);
                                break;
                            case 1:
                                resolutionText.setText(R.string.midnight_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.midnight_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                        }
                    }
                    // Scenario Three - The Devourer Below
                    else if (globalVariables.getCurrentScenario() == 3) {
                        switch (pos) {
                            // No resolution
                            case 0:
                                resolutionText.setText(R.string.devourer_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            // Resolution one
                            case 1:
                                resolutionText.setText(R.string.devourer_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            // Resolution two
                            case 2:
                                resolutionText.setText(R.string.devourer_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            // Resolution three
                            case 3:
                                resolutionText.setText(R.string.devourer_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    switch(globalVariables.getCurrentScenario()){
                        // Scenario 1-A: Extracurricular Activity
                        case 1:
                            switch(pos){
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.extracurricular_no_resolution);
                                    globalVariables.setResolution(0);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.extracurricular_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.extracurricular_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                                // Resolution Three
                                case 3:
                                    resolutionText.setText(R.string.extracurricular_resolution_three);
                                    globalVariables.setResolution(3);
                                    break;
                                // Resolution Four
                                case 4:
                                    resolutionText.setText(R.string.extracurricular_resolution_four);
                                    globalVariables.setResolution(4);
                                    break;
                            }
                            break;
                        // Scenario 1-B: The House Always Wins
                        case 2:
                            switch(pos){
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.house_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.house_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.house_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                                // Resolution Three
                                case 3:
                                    resolutionText.setText(R.string.house_resolution_three);
                                    globalVariables.setResolution(3);
                                    break;
                                // Resolution Four
                                case 4:
                                    resolutionText.setText(R.string.house_resolution_four);
                                    globalVariables.setResolution(4);
                                    break;
                            }
                            break;
                        // Scenario 2: The Miskatonic Museum
                        case 4:
                            switch(pos){
                                // No resolution
                                case 0:
                                    resolutionText.setText(R.string.miskatonic_no_resolution);
                                    globalVariables.setResolution(0);
                                    break;
                                // Resolution One
                                case 1:
                                    resolutionText.setText(R.string.miskatonic_resolution_one);
                                    globalVariables.setResolution(1);
                                    break;
                                // Resolution Two
                                case 2:
                                    resolutionText.setText(R.string.miskatonic_resolution_two);
                                    globalVariables.setResolution(2);
                                    break;
                            }
                            break;
                    }
                    break;
            }
            // Side stories
            if(globalVariables.getCurrentScenario()>100){
                switch(globalVariables.getCurrentScenario()){
                    case 101:
                        switch(pos){
                            case 0:
                            case 1:
                                resolutionText.setText(R.string.rougarou_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.rougarou_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                            case 3:
                                resolutionText.setText(R.string.rougarou_resolution_three);
                                globalVariables.setResolution(3);
                                break;
                        }
                        break;
                    case 102:
                        switch(pos){
                            case 0:
                                resolutionText.setText(R.string.carnevale_no_resolution);
                                globalVariables.setResolution(0);
                                break;
                            case 1:
                                resolutionText.setText(R.string.carnevale_resolution_one);
                                globalVariables.setResolution(1);
                                break;
                            case 2:
                                resolutionText.setText(R.string.carnevale_resolution_two);
                                globalVariables.setResolution(2);
                                break;
                        }
                }
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
