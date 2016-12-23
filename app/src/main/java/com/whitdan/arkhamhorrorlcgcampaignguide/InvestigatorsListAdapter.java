package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by danie on 16/12/2016.
 */

public class InvestigatorsListAdapter extends ArrayAdapter<Investigator> {

    private GlobalVariables globalVariables;

    public InvestigatorsListAdapter(Context context, ArrayList<Investigator> investigators, GlobalVariables global) {
        super(context, 0, investigators);
        globalVariables = global;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_investigator, parent, false);
        }

        Investigator currentInvestigator = getItem(position);
        String[] investigatorNames = getContext().getResources().getStringArray(R.array.investigators);

        // Create an ArrayAdapter using the investigators string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.investigators, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Set investigator name and attributes as long as the investigator is in use
        if ((currentInvestigator.getStatus() != 0) && (currentInvestigator.getStatus() != 2) ) {

            View investigatorView = listItemView.findViewById(R.id.investigator_view);
            investigatorView.setVisibility(VISIBLE);

            // Get name and apply to corresponding TextView
            int investigatorName = currentInvestigator.getName();
            TextView investigatorNameView = (TextView) listItemView.findViewById(R.id.investigator_name);
            investigatorNameView.setText(investigatorNames[investigatorName]);

            // Get physical trauma and apply to corresponding TextView
            int investigatorDamage = currentInvestigator.getDamage();
            TextView investigatorDamageView = (TextView) listItemView.findViewById(R.id.investigator_damage);
            investigatorDamageView.setText(Integer.toString(investigatorDamage));

            // Get mental trauma and apply to corresponding TextView
            int investigatorHorror = currentInvestigator.getHorror();
            TextView investigatorHorrorView = (TextView) listItemView.findViewById(R.id.investigator_horror);
            investigatorHorrorView.setText(Integer.toString(investigatorHorror));

            // Get available XP and apply to corresponding TextView
            int investigatorXP = currentInvestigator.getAvailableXP();
            TextView investigatorXPView = (TextView) listItemView.findViewById(R.id.investigator_xp_available);
            investigatorXPView.setText(Integer.toString(investigatorXP));

            // Set up spent XP spinner and apply OnItemSelectedListener if on scenario setup
            View xpView = listItemView.findViewById(R.id.xp_spent_layout);
            View defeatedView = listItemView.findViewById(R.id.defeated_layout);
            if (globalVariables.getScenarioStage() == 1) {
                xpView.setVisibility(VISIBLE);
                defeatedView.setVisibility(GONE);
                Spinner investigatorXPSpinner = (Spinner) listItemView.findViewById(R.id.investigator_xp_spent);
                List<Integer> iOneXPArray = new ArrayList<Integer>();
                for (int i = 0; investigatorXP >= i; i++) {
                    iOneXPArray.add(i);
                }
                ArrayAdapter<Integer> iOneXPadapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, iOneXPArray);
                iOneXPadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                investigatorXPSpinner.setAdapter(iOneXPadapter);
                investigatorXPSpinner.setOnItemSelectedListener(new ListenerXPSpinner(position));
            }
            // Set up defeated spinner and apply OnItemSelectedListener if on scenario finish
            else if (globalVariables.getScenarioStage() == 2) {
                xpView.setVisibility(GONE);
                defeatedView.setVisibility(VISIBLE);
                Spinner defeatedSpinner = (Spinner) listItemView.findViewById(R.id.investigator_defeated);
                ArrayAdapter<CharSequence> defeatedAdapter = ArrayAdapter.createFromResource(getContext(), R.array.defeated, android.R.layout.simple_spinner_item);
                defeatedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                defeatedSpinner.setAdapter(defeatedAdapter);
                defeatedSpinner.setOnItemSelectedListener(new ListenerDefeatedSpinner(position));
            }
        }

        // If investigator is not in use, hide the LinearLayout containing the information
        else if (currentInvestigator.getStatus() == 0) {
            LinearLayout investigatorView = (LinearLayout) listItemView.findViewById(R.id.investigator_view);
            investigatorView.setVisibility(GONE);
        }

       /* TODO: // If investigator is dead, setup a new investigator selection spinner
        else if (currentInvestigator.getStatus() == 2) {
            View investigatorView = listItemView.findViewById(R.id.investigator_view);
            investigatorView.setVisibility(GONE);
            Spinner spinner = (Spinner) listItemView.findViewById(R.id.investigator_one);
            spinner.setVisibility(VISIBLE);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new CampaignInvestigatorsSpinnerListener(globalVariables));
        }*/

        return listItemView;
    }

    // OnItemSelectedListener for the Defeated Spinner
    private class ListenerDefeatedSpinner extends Activity implements AdapterView.OnItemSelectedListener {

        // Need to pass the GlobalVariables to the listener to allow it to be changed below
        private int superPosition;

        public ListenerDefeatedSpinner(int position) {
            superPosition = position;
        }

        // Sets the correct investigator status to the tempStatus (pending clicking continue)
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            setInvestigatorStatus(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }

        private void setInvestigatorStatus(int pos) {
            if (pos > 0) {
                globalVariables.investigators.get(superPosition).setTempStatus(pos + 2);
            }
        }
    }

    // OnItemSelectedListener for the XPSpinner
    private class ListenerXPSpinner extends Activity implements AdapterView.OnItemSelectedListener {
        private int superPosition;

        private ListenerXPSpinner(int position) {
            superPosition = position;
        }

        // Sets the correct investigator using the below methods or deletes the investigator if none selected
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            setXPSpent(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }

        private void setXPSpent(int pos) {
            globalVariables.investigators.get(superPosition).setTempXP(pos);
        }
    }


}
