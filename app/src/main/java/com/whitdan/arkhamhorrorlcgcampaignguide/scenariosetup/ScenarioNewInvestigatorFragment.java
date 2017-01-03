package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.GONE;
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

        // Setup first death statement
        if (globalVariables.investigators.size() > 0) {
            if (globalVariables.investigators.get(0).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(0).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_one_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        // Setup second death statement
        if (globalVariables.investigators.size() > 1) {
            if (globalVariables.investigators.get(1).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(1).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_two_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }
        // Setup third death statement
        if (globalVariables.investigators.size() > 2) {
            if (globalVariables.investigators.get(2).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(2).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_three_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        // Setup fourth death statement
        if (globalVariables.investigators.size() > 3) {
            if (globalVariables.investigators.get(3).getStatus() == 2) {
                String dead = investigatorNames[globalVariables.investigators.get(3).getName()] + " has died.";
                TextView investigator = (TextView) v.findViewById(R.id.investigator_four_died);
                investigator.setVisibility(VISIBLE);
                investigator.setText(dead);
            }
        }

        // Get sharedpreferences on expansions owned
        String sharedPrefs = getActivity().getResources().getString(R.string.expacs_owned);
        String dunwichOwnedString = getActivity().getResources().getString(R.string.dunwich_campaign_name);
        SharedPreferences settings = getActivity().getSharedPreferences(sharedPrefs, 0);
        boolean dunwichOwned = settings.getBoolean(dunwichOwnedString, false);

        int investigators = 5;
        if (dunwichOwned) {
            investigators += 5;
        }
        int core = 0;
        int dunwich = 0;

        // Setup CheckBoxes with OnCheckedChangeListeners, as long as the investigator is not in use
        if (globalVariables.investigatorsInUse[Investigator.ROLAND_BANKS] == 0) {
            CheckBox roland = (CheckBox) v.findViewById(R.id.roland_banks);
            roland.setVisibility(VISIBLE);
            roland.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            investigators--;
            core++;
        }
        if (globalVariables.investigatorsInUse[Investigator.SKIDS_OTOOLE] == 0) {
            CheckBox skids = (CheckBox) v.findViewById(R.id.skids_otoole);
            skids.setVisibility(VISIBLE);
            skids.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            investigators--;
            core++;
        }
        if (globalVariables.investigatorsInUse[Investigator.AGNES_BAKER] == 0) {
            CheckBox agnes = (CheckBox) v.findViewById(R.id.agnes_baker);
            agnes.setVisibility(VISIBLE);
            agnes.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            investigators--;
            core++;
        }
        if (globalVariables.investigatorsInUse[Investigator.DAISY_WALKER] == 0) {
            CheckBox daisy = (CheckBox) v.findViewById(R.id.daisy_walker);
            daisy.setVisibility(VISIBLE);
            daisy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            investigators--;
            core++;
        }
        if (globalVariables.investigatorsInUse[Investigator.WENDY_ADAMS] == 0) {
            CheckBox wendy = (CheckBox) v.findViewById(R.id.wendy_adams);
            wendy.setVisibility(VISIBLE);
            wendy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            investigators--;
            core++;
        }
        // Hide the LinearLayout if all core investigators are dead
        if (core == 5) {
            LinearLayout coreInvestigators = (LinearLayout) v.findViewById(R.id.core_investigators);
            coreInvestigators.setVisibility(GONE);
        }

        // Setup checkboxes for Dunwich investigators
        if (dunwichOwned) {
            if (globalVariables.investigatorsInUse[Investigator.ZOEY_SAMARAS] == 0) {
                CheckBox zoey = (CheckBox) v.findViewById(R.id.zoey_samaras);
                zoey.setVisibility(VISIBLE);
                zoey.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                investigators--;
                dunwich++;
            }
            if (globalVariables.investigatorsInUse[Investigator.REX_MURPHY] == 0) {
                CheckBox rex = (CheckBox) v.findViewById(R.id.rex_murphy);
                rex.setVisibility(VISIBLE);
                rex.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                investigators--;
                dunwich++;
            }
            if (globalVariables.investigatorsInUse[Investigator.JENNY_BARNES] == 0) {
                CheckBox jenny = (CheckBox) v.findViewById(R.id.jenny_barnes);
                jenny.setVisibility(VISIBLE);
                jenny.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                investigators--;
                dunwich++;
            }
            if (globalVariables.investigatorsInUse[Investigator.JIM_CULVER] == 0) {
                CheckBox jim = (CheckBox) v.findViewById(R.id.jim_culver);
                jim.setVisibility(VISIBLE);
                jim.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                investigators--;
                dunwich++;
            }
            if (globalVariables.investigatorsInUse[Investigator.ASHCAN_PETE] == 0) {
                CheckBox pete = (CheckBox) v.findViewById(R.id.ashcan_pete);
                pete.setVisibility(VISIBLE);
                pete.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                investigators--;
                dunwich++;
            }
        }
        // Hide LinearLayout if all Dunwich investigators are dead or if Dunwich is not owned
        if (dunwich == 5 || !dunwichOwned) {
            LinearLayout dunwichInvestigators = (LinearLayout) v.findViewById(R.id.dunwich_investigators);
            dunwichInvestigators.setVisibility(GONE);
        }

        // Show that the investigators have lost if there are no investigators remaining
        if (investigators == 0) {
            TextView lost = (TextView) v.findViewById(R.id.choose_investigators);
            lost.setText(R.string.lost);
        }

        // Set onClickListener on the continue button
        TextView continueButton = (TextView) v.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScenarioSetupActivity) getActivity()).restartScenario(getContext());
            }
        });

        return v;
    }

    // Custom OnCheckedChangeListener
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();
        private int investigators = 0;

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                if (globalVariables.investigators.get(i).getStatus() == 1) {
                    investigators++;
                }
            }

            switch (buttonView.getId()) {
                case R.id.roland_banks:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.ROLAND_BANKS);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ROLAND_BANKS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.skids_otoole:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.SKIDS_OTOOLE);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.SKIDS_OTOOLE) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.agnes_baker:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.AGNES_BAKER);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.AGNES_BAKER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.daisy_walker:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.DAISY_WALKER);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.DAISY_WALKER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.wendy_adams:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.WENDY_ADAMS);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.WENDY_ADAMS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.zoey_samaras:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.ZOEY_SAMARAS);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ZOEY_SAMARAS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.rex_murphy:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.REX_MURPHY);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.REX_MURPHY) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jenny_barnes:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.JENNY_BARNES);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.JENNY_BARNES) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jim_culver:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.JIM_CULVER);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.JIM_CULVER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.ashcan_pete:
                    if (isChecked && investigators < 4) {
                        globalVariables.investigatorNames.add(Investigator.ASHCAN_PETE);
                        investigators++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigators--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ASHCAN_PETE) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
            }
        }
    }
}
