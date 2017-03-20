package com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    private int investigatorsCount;
    private int current;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scenario_new_investigator, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();
        String[] investigatorNames = getResources().getStringArray(R.array.investigators);

        investigatorsCount = 0;
        current = 0;

        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            if (globalVariables.investigators.get(i).getStatus() == 1) {
                investigatorsCount++;
                current++;
            }
        }

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
        // Hide the LinearLayout if all core investigatorsCount are dead
        if (core == 5) {
            LinearLayout coreInvestigators = (LinearLayout) v.findViewById(R.id.core_investigators);
            coreInvestigators.setVisibility(GONE);
        }

        // Setup checkboxes for Dunwich investigatorsCount
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
        // Hide LinearLayout if all Dunwich investigatorsCount are dead or if Dunwich is not owned
        if (dunwich == 5 || !dunwichOwned) {
            LinearLayout dunwichInvestigators = (LinearLayout) v.findViewById(R.id.dunwich_investigators);
            dunwichInvestigators.setVisibility(GONE);
        }

        // Show that the investigatorsCount have lost if there are no investigatorsCount remaining
        if (investigators == 0) {
            TextView lost = (TextView) v.findViewById(R.id.choose_investigators);
            lost.setText(R.string.lost);
        }

        // Set onClickListener on the continue button
        TextView continueButton = (TextView) v.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScenarioSetupActivity) getActivity()).restartScenario(getActivity());
            }
        });

        return v;
    }

    // Custom OnCheckedChangeListener
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private final GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplication();

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            View parent = (View) buttonView.getParent().getParent().getParent().getParent();

            switch (buttonView.getId()) {
                case R.id.roland_banks:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.ROLAND_BANKS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ROLAND_BANKS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.skids_otoole:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.SKIDS_OTOOLE);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.SKIDS_OTOOLE) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.agnes_baker:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.AGNES_BAKER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.AGNES_BAKER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.daisy_walker:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.DAISY_WALKER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.DAISY_WALKER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.wendy_adams:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.WENDY_ADAMS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.WENDY_ADAMS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.zoey_samaras:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.ZOEY_SAMARAS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ZOEY_SAMARAS) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.rex_murphy:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.REX_MURPHY);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.REX_MURPHY) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jenny_barnes:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.JENNY_BARNES);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.JENNY_BARNES) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jim_culver:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.JIM_CULVER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.JIM_CULVER) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.ashcan_pete:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.investigatorNames.add(Investigator.ASHCAN_PETE);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.investigatorNames.size(); i++) {
                            if (globalVariables.investigatorNames.get(i) == Investigator.ASHCAN_PETE) {
                                globalVariables.investigatorNames.remove(i);
                            }
                        }
                    }
                    break;
            }

            LinearLayout investigatorOne = (LinearLayout) parent.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwo = (LinearLayout) parent.findViewById(R.id.investigator_two);
            LinearLayout investigatorThree = (LinearLayout) parent.findViewById(R.id.investigator_three);
            LinearLayout investigatorFour = (LinearLayout) parent.findViewById(R.id.investigator_four);
            TextView investigatorOneName = (TextView) parent.findViewById(R.id.investigator_one_name);
            TextView investigatorTwoName = (TextView) parent.findViewById(R.id.investigator_two_name);
            TextView investigatorThreeName = (TextView) parent.findViewById(R.id.investigator_three_name);
            TextView investigatorFourName = (TextView) parent.findViewById(R.id.investigator_four_name);
            String[] investigatorNames = getContext().getResources().getStringArray(R.array.investigators);

            Log.i("investigators: ", Integer.toString(investigatorsCount));
            Log.i("current: ", Integer.toString(current));

            // Show relevant views
            if(investigatorsCount - current > 0){
                investigatorOne.setVisibility(VISIBLE);
                String nameOne = investigatorNames[globalVariables.investigatorNames.get(0)];
                investigatorOneName.setText(nameOne);
            } else {investigatorOne.setVisibility(GONE);}

            if(investigatorsCount - current > 1){
                investigatorTwo.setVisibility(VISIBLE);
                String nameTwo = investigatorNames[globalVariables.investigatorNames.get(1)];
                investigatorTwoName.setText(nameTwo);
            } else {investigatorTwo.setVisibility(GONE);}

            if(investigatorsCount - current > 2){
                investigatorThree.setVisibility(VISIBLE);
                String nameThree = investigatorNames[globalVariables.investigatorNames.get(2)];
                investigatorThreeName.setText(nameThree);
            } else {investigatorThree.setVisibility(GONE);}

            if(investigatorsCount - current > 3){
                investigatorFour.setVisibility(VISIBLE);
                String nameFour = investigatorNames[globalVariables.investigatorNames.get(3)];
                investigatorFourName.setText(nameFour);
            } else {investigatorFour.setVisibility(GONE);}

            // Set listeners on the edit text fields
            final EditText playerOneName = (EditText) parent.findViewById(R.id.investigator_one_player);
            playerOneName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.playerNames[0] = playerOneName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerTwoName = (EditText) parent.findViewById(R.id.investigator_two_player);
            playerTwoName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.playerNames[1] = playerTwoName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerThreeName = (EditText) parent.findViewById(R.id.investigator_three_player);
            playerThreeName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.playerNames[2] = playerThreeName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerFourName = (EditText) parent.findViewById(R.id.investigator_four_player);
            playerFourName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.playerNames[3] = playerFourName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            final EditText playerOneDeckName = (EditText) parent.findViewById(R.id.investigator_one_deck_name);
            playerOneDeckName.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.deckNames[0] = playerOneDeckName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerTwoDeckName = (EditText) parent.findViewById(R.id.investigator_two_deck_name);
            playerTwoDeckName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.deckNames[1] = playerTwoDeckName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerThreeDeckName = (EditText) parent.findViewById(R.id.investigator_three_deck_name);
            playerThreeDeckName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.deckNames[2] = playerThreeDeckName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerFourDeckName = (EditText) parent.findViewById(R.id.investigator_four_deck_name);
            playerFourDeckName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.deckNames[3] = playerFourDeckName.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            final EditText playerOneDeck = (EditText) parent.findViewById(R.id.investigator_one_deck_link);
            playerOneDeck.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.decklists[0] = playerOneDeck.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerTwoDeck = (EditText) parent.findViewById(R.id.investigator_two_deck_link);
            playerTwoDeck.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.decklists[1] = playerTwoDeck.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerThreeDeck = (EditText) parent.findViewById(R.id.investigator_three_deck_link);
            playerThreeDeck.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.decklists[2] = playerThreeDeck.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            final EditText playerFourDeck = (EditText) parent.findViewById(R.id.investigator_four_deck_link);
            playerFourDeck.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    globalVariables.decklists[3] = playerFourDeck.getText().toString().trim();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
