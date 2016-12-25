package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * Allows the selection of up to four investigators for the campaign.
 */

public class CampaignInvestigatorsFragment extends Fragment {

    int investigatorsChosen = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_campaign_investigators, container, false);

        CheckBox roland = (CheckBox) v.findViewById(R.id.roland_banks);
        roland.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

        CheckBox skids = (CheckBox) v.findViewById(R.id.skids_otoole);
        skids.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

        CheckBox agnes = (CheckBox) v.findViewById(R.id.agnes_baker);
        agnes.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

        CheckBox daisy = (CheckBox) v.findViewById(R.id.daisy_walker);
        daisy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

        CheckBox wendy = (CheckBox) v.findViewById(R.id.wendy_adams);
        wendy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());


        return v;
    }

    // Custom OnCheckedChangeListener
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{

        private final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch(buttonView.getId()){
                case R.id.roland_banks:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(1);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == 1){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.skids_otoole:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(3);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == 3){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.agnes_baker:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(4);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == 4){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.daisy_walker:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(2);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == 2){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.wendy_adams:
                    if(isChecked && investigatorsChosen < 4){
                        globalVariables.investigatorNames.add(5);
                        investigatorsChosen++;
                    }
                    else if(isChecked) {
                        buttonView.setChecked(false);
                    }else{
                        investigatorsChosen--;
                        for(int i = 0; i < globalVariables.investigatorNames.size(); i++)
                        {
                            if(globalVariables.investigatorNames.get(i) == 5){
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
            }
        }
    }
}

