package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.VISIBLE;

/**
 * Allows selection of a new investigator when one has died.
 */

public class ScenarioNewInvestigatorFragment extends Fragment {

    GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_new_investigator, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();
        String[] investigatorNames = getResources().getStringArray(R.array.investigators);

        // Create an ArrayAdapter using the investigators string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.investigators, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setup first spinner and apply the adapter
        if (globalVariables.investigators.size() > 0) {
            if (globalVariables.investigators.get(0).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(0).getName()] + " has died. Choose " +
                        "a new investigator:";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_one_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
                Spinner spinner = (Spinner) v.findViewById(R.id.investigator_one);
                spinner.setVisibility(VISIBLE);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new InvestigatorDeadSpinnerListener());
            }
        }

        // Setup second spinner and apply the adapter
        if (globalVariables.investigators.size() > 1) {
            if (globalVariables.investigators.get(1).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(1).getName()] + " has died. Choose " +
                        "a new investigator:";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_two_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
                Spinner spinner = (Spinner) v.findViewById(R.id.investigator_two);
                spinner.setVisibility(VISIBLE);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new InvestigatorDeadSpinnerListener());
            }
        }
        // Setup third spinner and apply the adapter
        if (globalVariables.investigators.size() > 2) {
            if (globalVariables.investigators.get(2).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(2).getName()] + " has died. Choose " +
                        "a new investigator:";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_three_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
                Spinner spinner = (Spinner) v.findViewById(R.id.investigator_three);
                spinner.setVisibility(VISIBLE);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new InvestigatorDeadSpinnerListener());
            }
        }

        // Setup fourth spinner and apply the adapter
        if (globalVariables.investigators.size() > 3) {
            if (globalVariables.investigators.get(3).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(3).getName()] + " has died. Choose " +
                        "a new investigator:";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_four_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
                Spinner spinner = (Spinner) v.findViewById(R.id.investigator_four);
                spinner.setVisibility(VISIBLE);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new InvestigatorDeadSpinnerListener());
            }
        }

        // Set onClickListener on the continue button
        TextView continueButton = (TextView) v.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScenarioSetupActivity)getActivity()).restartScenario();
            }
        });

        return v;
    }

    // Custom OnItemSelectedListener for the investigator spinners
    private class InvestigatorDeadSpinnerListener implements AdapterView.OnItemSelectedListener {

        // Need to pass the GlobalVariables to the listener to allow it to be changed below
        private final GlobalVariables mGlobalVariables = (GlobalVariables) getActivity().getApplication();

        // Sets the correct investigator
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            switch (parent.getId()) {
                case R.id.investigator_one:
                    mGlobalVariables.investigatorNames[0] = pos;
                    break;
                case R.id.investigator_two:
                    mGlobalVariables.investigatorNames[1] = pos;
                    break;
                case R.id.investigator_three:
                    mGlobalVariables.investigatorNames[2] = pos;
                    break;
                case R.id.investigator_four:
                    mGlobalVariables.investigatorNames[3] = pos;
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
