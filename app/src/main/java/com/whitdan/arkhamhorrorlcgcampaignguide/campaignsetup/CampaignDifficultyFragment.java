package com.whitdan.arkhamhorrorlcgcampaignguide.campaignsetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Allows the selection of a difficulty and displays the relevant chaos bag makeup.
 */

public class CampaignDifficultyFragment extends Fragment {

    private GlobalVariables globalVariables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_campaign_difficulty, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplication();

        // Setup difficulty selection spinner
        Spinner difficultySpinner = (Spinner) v.findViewById(R.id.difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.difficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        difficultySpinner.setOnItemSelectedListener(new DifficultySpinnerListener(v));

        // Set click listener on the continue button
        TextView button = (TextView) v.findViewById(R.id.continue_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CampaignSetupActivity) getActivity()).startScenario(v);
            }
        });

        return v;
    }

    private class DifficultySpinnerListener extends Activity implements AdapterView.OnItemSelectedListener {

        View v;

        DifficultySpinnerListener(View view) {
            v = view;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Get all relevant views and layouts
            LinearLayout plusTwoLayout = (LinearLayout) v.findViewById(R.id.plus_two_layout);
            LinearLayout plusOneLayout = (LinearLayout) v.findViewById(R.id.plus_one_layout);
            LinearLayout zeroLayout = (LinearLayout) v.findViewById(R.id.zero_layout);
            LinearLayout minusOneLayout = (LinearLayout) v.findViewById(R.id.minus_one_layout);
            LinearLayout minusTwoLayout = (LinearLayout) v.findViewById(R.id.minus_two_layout);
            LinearLayout minusThreeLayout = (LinearLayout) v.findViewById(R.id.minus_three_layout);
            LinearLayout minusFourLayout = (LinearLayout) v.findViewById(R.id.minus_four_layout);
            LinearLayout minusFiveLayout = (LinearLayout) v.findViewById(R.id.minus_five_layout);
            LinearLayout minusSixLayout = (LinearLayout) v.findViewById(R.id.minus_six_layout);
            LinearLayout minusSevenLayout = (LinearLayout) v.findViewById(R.id.minus_seven_layout);
            LinearLayout minusEightLayout = (LinearLayout) v.findViewById(R.id.minus_eight_layout);
            LinearLayout skullLayout = (LinearLayout) v.findViewById(R.id.skull_layout);
            LinearLayout cultistLayout = (LinearLayout) v.findViewById(R.id.cultist_layout);
            LinearLayout tabletLayout = (LinearLayout) v.findViewById(R.id.tablet_layout);
            LinearLayout elderThingLayout = (LinearLayout) v.findViewById(R.id.elder_thing_layout);
            LinearLayout tentaclesLayout = (LinearLayout) v.findViewById(R.id.tentacles_layout);
            LinearLayout elderSignLayout = (LinearLayout) v.findViewById(R.id.elder_sign_layout);
            TextView plusTwo = (TextView) v.findViewById(R.id.plus_two);
            TextView plusOne = (TextView) v.findViewById(R.id.plus_one);
            TextView zero = (TextView) v.findViewById(R.id.zero);
            TextView minusOne = (TextView) v.findViewById(R.id.minus_one);
            TextView minusTwo = (TextView) v.findViewById(R.id.minus_two);
            TextView minusThree = (TextView) v.findViewById(R.id.minus_three);
            TextView minusFour = (TextView) v.findViewById(R.id.minus_four);
            TextView minusFive = (TextView) v.findViewById(R.id.minus_five);
            TextView minusSix = (TextView) v.findViewById(R.id.minus_six);
            TextView minusSeven = (TextView) v.findViewById(R.id.minus_seven);
            TextView minusEight = (TextView) v.findViewById(R.id.minus_eight);
            TextView skull = (TextView) v.findViewById(R.id.skull);
            TextView cultist = (TextView) v.findViewById(R.id.cultist);
            TextView tablet = (TextView) v.findViewById(R.id.tablet);
            TextView elderThing = (TextView) v.findViewById(R.id.elder_thing);
            TextView tentacles = (TextView) v.findViewById(R.id.tentacles);
            TextView elderSign = (TextView) v.findViewById(R.id.elder_sign);

            int currentCampaign = globalVariables.getCurrentCampaign();
            switch (currentCampaign) {

                // Night of the Zealot
                case 1:
                    // Hide all unused token views
                    plusTwoLayout.setVisibility(GONE);
                    minusSevenLayout.setVisibility(GONE);
                    elderThingLayout.setVisibility(GONE);
                    // Set consistent values
                    skull.setText(R.string.two);
                    cultist.setText(R.string.one);
                    tablet.setText(R.string.one);
                    tentacles.setText(R.string.one);
                    elderSign.setText(R.string.one);
                    switch (position) {
                        // Easy
                        case 0:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOne.setText(R.string.two);
                            zero.setText(R.string.three);
                            minusOne.setText(R.string.three);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(GONE);
                            minusFourLayout.setVisibility(GONE);
                            minusFiveLayout.setVisibility(GONE);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Standard
                        case 1:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOne.setText(R.string.one);
                            zero.setText(R.string.two);
                            minusOne.setText(R.string.three);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.one);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.one);
                            minusFiveLayout.setVisibility(GONE);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Hard
                        case 2:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOneLayout.setVisibility(GONE);
                            zero.setText(R.string.three);
                            minusOne.setText(R.string.two);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.two);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.one);
                            minusFiveLayout.setVisibility(VISIBLE);
                            minusFive.setText(R.string.one);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Expert
                        case 3:
                            plusOneLayout.setVisibility(GONE);
                            zero.setText(R.string.one);
                            minusOne.setText(R.string.two);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.two);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.two);
                            minusFiveLayout.setVisibility(VISIBLE);
                            minusFive.setText(R.string.one);
                            minusSixLayout.setVisibility(VISIBLE);
                            minusSix.setText(R.string.one);
                            minusEightLayout.setVisibility(VISIBLE);
                            minusEight.setText(R.string.one);
                            break;
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    // Hide all unused token views
                    plusTwoLayout.setVisibility(GONE);
                    minusSevenLayout.setVisibility(GONE);
                    elderThingLayout.setVisibility(GONE);
                    tabletLayout.setVisibility(GONE);
                    // Set consistent values
                    skull.setText(R.string.two);
                    cultist.setText(R.string.one);
                    tentacles.setText(R.string.one);
                    elderSign.setText(R.string.one);
                    switch (position) {
                        // Easy
                        case 0:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOne.setText(R.string.two);
                            zero.setText(R.string.three);
                            minusOne.setText(R.string.three);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(GONE);
                            minusFourLayout.setVisibility(GONE);
                            minusFiveLayout.setVisibility(GONE);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Standard
                        case 1:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOne.setText(R.string.one);
                            zero.setText(R.string.two);
                            minusOne.setText(R.string.three);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.one);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.one);
                            minusFiveLayout.setVisibility(GONE);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Hard
                        case 2:
                            plusOneLayout.setVisibility(VISIBLE);
                            plusOneLayout.setVisibility(GONE);
                            zero.setText(R.string.three);
                            minusOne.setText(R.string.two);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.two);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.one);
                            minusFiveLayout.setVisibility(VISIBLE);
                            minusFive.setText(R.string.one);
                            minusSixLayout.setVisibility(GONE);
                            minusEightLayout.setVisibility(GONE);
                            break;
                        // Expert
                        case 3:
                            plusOneLayout.setVisibility(GONE);
                            zero.setText(R.string.one);
                            minusOne.setText(R.string.two);
                            minusTwo.setText(R.string.two);
                            minusThreeLayout.setVisibility(VISIBLE);
                            minusThree.setText(R.string.two);
                            minusFourLayout.setVisibility(VISIBLE);
                            minusFour.setText(R.string.two);
                            minusFiveLayout.setVisibility(VISIBLE);
                            minusFive.setText(R.string.one);
                            minusSixLayout.setVisibility(VISIBLE);
                            minusSix.setText(R.string.one);
                            minusEightLayout.setVisibility(VISIBLE);
                            minusEight.setText(R.string.one);
                            break;
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}
