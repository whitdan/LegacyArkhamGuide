package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.InvestigatorsListAdapter;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Shows info for all of the investigators in use, and allows the selection of a lead investigator for the scenario.
 */

public class ScenarioInvestigatorsFragment extends Fragment {

    private GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_investigators, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        /*
            Setup spinner with investigators to select lead investigator
        */
        // Get all investigator names from String array
        String[] allInvestigatorNames = getResources().getStringArray(R.array.investigators);
        // Setup ArrayList and add the names of the investigators in use to it
        List<String> investigatorNames = new ArrayList<>();
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            int currentInvestigator = globalVariables.investigators.get(i).getName();
            investigatorNames.add(allInvestigatorNames[currentInvestigator]);
        }
        // Find the relevant spinner and attach the adapter with the names to it and an OnItemSelectedListener
        Spinner leadInvestigator = (Spinner) v.findViewById(R.id.lead_investigator_spinner);
        ArrayAdapter<String> leadInvestigatorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, investigatorNames);
        leadInvestigator.setAdapter(leadInvestigatorAdapter);
        leadInvestigatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadInvestigator.setOnItemSelectedListener(new ListenerLeadInvestigatorSpinner());

        // Hide spinner if on unreleased scenario or end of campaign
        if (globalVariables.getScenarioStage()==3 ||
                (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 6)) {
            LinearLayout lead = (LinearLayout) v.findViewById(R.id.lead_investigator_view);
            lead.setVisibility(GONE);
            TextView completed = (TextView) v.findViewById(R.id.campaign_completed);
            completed.setVisibility(VISIBLE);
        }

        // Setup ListView from the InvestigatorsListAdapter
        ListView listView = (ListView) v.findViewById(R.id.investigator_list);
        InvestigatorsListAdapter investigatorsAdapter = new InvestigatorsListAdapter(this.getActivity(), globalVariables.investigators, globalVariables);
        listView.setAdapter(investigatorsAdapter);

        // Set click listener for continue button
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity(), this.getActivity()));

        return v;
    }

    // OnItemSelectedListener for the LeadInvestigatorSpinner
    private class ListenerLeadInvestigatorSpinner extends Activity implements AdapterView.OnItemSelectedListener {

        // Sets the selected investigator as lead investigator
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            globalVariables.setLeadInvestigator(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
