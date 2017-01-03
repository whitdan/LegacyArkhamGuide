package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Investigator;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.GONE;

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

        // Text change listener to set the next text to the campaignName variable in CampaignSetupActivity
        campaign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((CampaignSetupActivity) getActivity()).campaignName = campaign.getText().toString().trim();
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

        // Hide Dunwich investigators if xpac not owned
        String sharedPrefs = getActivity().getResources().getString(R.string.expacs_owned);
        String dunwichOwnedString = getActivity().getResources().getString(R.string.dunwich_campaign_name);

        SharedPreferences settings = getActivity().getSharedPreferences(sharedPrefs, 0);
        boolean dunwichOwned = settings.getBoolean(dunwichOwnedString, false);

        if (dunwichOwned) {
            CheckBox zoey = (CheckBox) v.findViewById(R.id.zoey_samaras);
            zoey.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

            CheckBox rex = (CheckBox) v.findViewById(R.id.rex_murphy);
            rex.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

            CheckBox jenny = (CheckBox) v.findViewById(R.id.jenny_barnes);
            jenny.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

            CheckBox jim = (CheckBox) v.findViewById(R.id.jim_culver);
            jim.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());

            CheckBox pete = (CheckBox) v.findViewById(R.id.ashcan_pete);
            pete.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            LinearLayout dunwich = (LinearLayout) v.findViewById(R.id.dunwich_investigators);
            dunwich.setVisibility(GONE);
        }

        setupUI(v.findViewById(R.id.parent_layout), getActivity());

        return v;
    }

    // Hides the soft keyboard when someone clicks outside the EditText
    public void setupUI(final View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) activity.getSystemService(
                                    Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
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
}

