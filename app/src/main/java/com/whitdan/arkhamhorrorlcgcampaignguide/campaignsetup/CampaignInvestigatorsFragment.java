package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/**
 * Allows the selection of up to four investigators for the campaign.
 */

public class CampaignInvestigatorsFragment extends Fragment {

    int investigators;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_campaign_investigators, container, false);
        investigators = 0;

        final EditText campaign = (EditText) v.findViewById(R.id.campaign_name);
        campaign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((CampaignSetupActivity)getActivity()).campaignName = campaign.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (buttonView.isPressed()) {
                switch (buttonView.getId()) {
                    case R.id.roland_banks:
                        if (isChecked && investigators < 4) {
                            globalVariables.investigatorNames.add(1);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                                if (globalVariables.investigatorNames.get(i) == 1) {
                                    globalVariables.investigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.skids_otoole:
                        if (isChecked && investigators < 4) {
                            globalVariables.investigatorNames.add(3);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                                if (globalVariables.investigatorNames.get(i) == 3) {
                                    globalVariables.investigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.agnes_baker:
                        if (isChecked && investigators < 4) {
                            globalVariables.investigatorNames.add(4);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                                if (globalVariables.investigatorNames.get(i) == 4) {
                                    globalVariables.investigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.daisy_walker:
                        if (isChecked && investigators < 4) {
                            globalVariables.investigatorNames.add(2);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                                if (globalVariables.investigatorNames.get(i) == 2) {
                                    globalVariables.investigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.wendy_adams:
                        if (isChecked && investigators < 4) {
                            globalVariables.investigatorNames.add(5);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                                if (globalVariables.investigatorNames.get(i) == 5) {
                                    globalVariables.investigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
}

