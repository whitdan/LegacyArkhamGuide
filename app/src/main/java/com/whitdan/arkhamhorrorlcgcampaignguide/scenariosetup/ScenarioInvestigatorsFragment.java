package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.InvestigatorsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 12/12/2016.
 */

public class ScenarioInvestigatorsFragment extends Fragment {

    GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_investigators, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        // Setup spinner with investigators to select lead investigator
        String[] allInvestigatorNames = getResources().getStringArray(R.array.investigators);
        List<String> investigatorNames = new ArrayList<>();
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            int currentInvestigator = globalVariables.investigators.get(i).getName();
            investigatorNames.add(allInvestigatorNames[currentInvestigator]);
        }
        Spinner leadInvestigator = (Spinner) v.findViewById(R.id.lead_investigator_spinner);
        ArrayAdapter<String> leadInvestigatorAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, investigatorNames);
        leadInvestigatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadInvestigator.setAdapter(leadInvestigatorAdapter);
        leadInvestigator.setOnItemSelectedListener(new ListenerLeadInvestigatorSpinner());

        // Setup listview
        ListView listView = (ListView) v.findViewById(R.id.investigator_list);
        InvestigatorsListAdapter investigatorsAdapter = new InvestigatorsListAdapter(this.getActivity(), globalVariables.investigators, globalVariables);
        listView.setAdapter(investigatorsAdapter);

        // Set button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));

        return v;
    }

    // OnItemSelectedListener for the LeadInvestigatorSpinner
    private class ListenerLeadInvestigatorSpinner extends Activity implements AdapterView.OnItemSelectedListener {

        // Sets the correct investigator using the below methods or deletes the investigator if none selected
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            globalVariables.setLeadInvestigator(pos);
            int currentLead = globalVariables.getLeadInvestigator();
            Log.i("Test", "Current lead: " + currentLead);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            globalVariables.setLeadInvestigator(0);
            int currentLead = globalVariables.getLeadInvestigator();
            Log.i("Test", "Current lead: " + currentLead);
        }
    }
}
