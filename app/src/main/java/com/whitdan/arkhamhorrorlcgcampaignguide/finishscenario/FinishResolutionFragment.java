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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.VISIBLE;

/**
 * Allows the user to select the relevant resolution and any additional necessary information to resolve a scenario
 * (including the value of the victory display)
 */

public class FinishResolutionFragment extends Fragment {

    GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finish_resolution, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        /*
         Setup resolution choice spinner
        */
        Spinner resolutionSpinner = (Spinner) v.findViewById(R.id.resolution_selection);
        ArrayAdapter<CharSequence> adapter;
        // Three resolutions for scenarios 1 and 3
        if ((globalVariables.getCurrentScenario() == 1) || (globalVariables.getCurrentScenario() == 3)) {
            adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.resolutions_three, android.R.layout.simple_spinner_item);
        }
        // Two resolutions for scenario 2
        else {
            adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.resolutions_two, android.R.layout.simple_spinner_item);
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
        // Setup increment button to increase the victory display value and display teh new amount
        Button victoryIncrement = (Button) v.findViewById(R.id.victory_increment);
        victoryIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = globalVariables.getVictoryDisplay();
                if (current < 100) {
                    globalVariables.setVictoryDisplay(current + 1);
                    victoryDisplay.setText(String.valueOf(globalVariables.getVictoryDisplay()));
                }
            }
        });

        // If on second scenario, set cultists view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() == 2) {
            LinearLayout cultists = (LinearLayout) v.findViewById(R.id.cultists_interrogated);
            cultists.setVisibility(VISIBLE);
        }

        // If on second or third scenario and Ghoul Priest is alive, set ghoul priest view to visible
        if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() > 1 && globalVariables
                .getGhoulPriestAlive() == 1) {
            CheckBox ghoulPriest = (CheckBox) v.findViewById(R.id.ghoul_priest_killed);
            ghoulPriest.setVisibility(VISIBLE);
        }

        // Set continue button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));

        return v;
    }


    /*
     OnItemSelectedListener for the Resolution Spinner - Applies relevant resolution text and sets the resolution value
      */
    private class ResolutionSpinnerListener extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            TextView resolutionText = (TextView) view.getRootView().findViewById(R.id.resolution_text);
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

        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
