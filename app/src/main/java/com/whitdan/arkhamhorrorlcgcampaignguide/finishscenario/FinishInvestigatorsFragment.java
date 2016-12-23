package com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.ContinueOnClickListener;
import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.InvestigatorsListAdapter;

/**
 * Created by danie on 16/12/2016.
 */

public class FinishInvestigatorsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finish_investigators, container, false);
        GlobalVariables globalVariables = ((GlobalVariables) this.getActivity().getApplication());

        // Setup lead investigator TextView
        TextView textView = (TextView) v.findViewById(R.id.lead_investigator_textview);
        String[] investigatorNames = getResources().getStringArray(R.array.investigators);
        textView.setText(investigatorNames[globalVariables.investigators.get(globalVariables.getLeadInvestigator()).getName()]);

        // Setup listview
        ListView listView = (ListView) v.findViewById(R.id.investigator_list);
        InvestigatorsListAdapter investigatorsAdapter = new InvestigatorsListAdapter(this.getActivity(), globalVariables.investigators, globalVariables);
        listView.setAdapter(investigatorsAdapter);

        // Set button click listener
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new ContinueOnClickListener(globalVariables, this.getActivity()));

        return v;
    }
}
