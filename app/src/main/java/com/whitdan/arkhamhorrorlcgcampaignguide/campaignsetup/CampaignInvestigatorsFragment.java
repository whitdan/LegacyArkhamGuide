package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * Allows the selection of up to four investigators for the campaign.
 */

public class CampaignInvestigatorsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_campaign_investigators, container, false);

        // Create an ArrayAdapter using the investigators string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.investigators, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Setup first spinner and apply the adapter
        Spinner spinnerOne = (Spinner) v.findViewById(R.id.investigator_one);
        spinnerOne.setAdapter(adapter);
        spinnerOne.setOnItemSelectedListener(new CampaignInvestigatorsSpinnerListener());

        // Setup second spinner and apply the adapter
        Spinner spinnerTwo = (Spinner) v.findViewById(R.id.investigator_two);
        spinnerTwo.setAdapter(adapter);
        spinnerTwo.setOnItemSelectedListener(new CampaignInvestigatorsSpinnerListener());

        // Setup third spinner and apply the adapter
        Spinner spinnerThree = (Spinner) v.findViewById(R.id.investigator_three);
        spinnerThree.setAdapter(adapter);
        spinnerThree.setOnItemSelectedListener(new CampaignInvestigatorsSpinnerListener());

        // Setup fourth spinner and apply the adapter
        Spinner spinnerFour = (Spinner) v.findViewById(R.id.investigator_four);
        spinnerFour.setAdapter(adapter);
        spinnerFour.setOnItemSelectedListener(new CampaignInvestigatorsSpinnerListener());

        return v;
    }

    // Custom OnItemSelectedListener for the investigator spinners
    private class CampaignInvestigatorsSpinnerListener extends Activity implements AdapterView.OnItemSelectedListener {

        // Need to pass the GlobalVariables to the listener to allow it to be changed below
        private GlobalVariables mGlobalVariables = (GlobalVariables) getActivity().getApplication();

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

