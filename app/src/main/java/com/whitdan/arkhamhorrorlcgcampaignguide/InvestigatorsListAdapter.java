package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Displays list of in-use investigators, with their relevant info.
 * Used by both scenario setup (with XP spent selection) and finish scenario (with investigator eliminated spinner).
 */

public class InvestigatorsListAdapter extends ArrayAdapter<Investigator> {

    private GlobalVariables globalVariables;
    private Context context;

    public InvestigatorsListAdapter(Context con, ArrayList<Investigator> investigators, GlobalVariables global) {
        super(con, 0, investigators);
        context = con;
        globalVariables = global;
    }

    @Override
    @NonNull
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_investigator, parent, false);
        }

        // Get array of all investigator names and the current Investigator object
        String[] investigatorNames = getContext().getResources().getStringArray(R.array.investigators);
        final Investigator currentInvestigator = getItem(pos);

        // Ensure the ListView item is visible
        View investigatorView = listItemView.findViewById(R.id.investigator_view);
        investigatorView.setVisibility(VISIBLE);

        // Get name and apply to corresponding TextView
        int investigatorName = currentInvestigator.getName();
        TextView investigatorNameView = (TextView) listItemView.findViewById(R.id.investigator_name);
        investigatorNameView.setText(investigatorNames[investigatorName]);
        String playerName = currentInvestigator.getPlayer();
        TextView playerNameView = (TextView) listItemView.findViewById(R.id.player_name);
        playerNameView.setText(playerName);
        final String decklist = currentInvestigator.getDecklist();
        TextView decklistPaddingView = (TextView) listItemView.findViewById(R.id.decklist_padding);
        TextView decklistView = (TextView) listItemView.findViewById(R.id.decklist);
        if(decklist == null){
            decklistPaddingView.setVisibility(GONE);
            decklistView.setVisibility(GONE);
        } else{
            decklistView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String deck;
                    if (!decklist.startsWith("http://") && !decklist.startsWith("https://")){
                        deck = "http://" + decklist;
                    } else {
                        deck = decklist;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deck));
                    context.startActivity(browserIntent);
                }
            });
        }

        // Get physical trauma and apply to corresponding TextView
        int investigatorDamage = currentInvestigator.getDamage();
        TextView investigatorDamageView = (TextView) listItemView.findViewById(R.id.investigator_damage);
        investigatorDamageView.setText(String.valueOf(investigatorDamage));

        // Get mental trauma and apply to corresponding TextView
        int investigatorHorror = currentInvestigator.getHorror();
        TextView investigatorHorrorView = (TextView) listItemView.findViewById(R.id.investigator_horror);
        investigatorHorrorView.setText(String.valueOf(investigatorHorror));

        // Get available XP and apply to corresponding TextView
        int investigatorXP = currentInvestigator.getAvailableXP();
        TextView investigatorXPView = (TextView) listItemView.findViewById(R.id.investigator_xp_available);
        investigatorXPView.setText(String.valueOf(investigatorXP));

        // If on scenario setup set up spent XP view and buttons
        View xpView = listItemView.findViewById(R.id.xp_spent_layout);
        View defeatedView = listItemView.findViewById(R.id.defeated_layout);
        if (globalVariables.getScenarioStage() == 1
                && !(globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 5)) {
            xpView.setVisibility(VISIBLE);
            defeatedView.setVisibility(GONE);

            final TextView xpSpent = (TextView) listItemView.findViewById(R.id.investigator_xp_spent);
            Button xpDecrement = (Button) listItemView.findViewById(R.id.xp_decrement);
            Button xpIncrement = (Button) listItemView.findViewById(R.id.xp_increment);

            xpSpent.setText(String.valueOf(currentInvestigator.getTempXP()));
            xpDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = currentInvestigator.getTempXP();
                    if (current > 0) {
                        currentInvestigator.setTempXP(current - 1);
                        xpSpent.setText(String.valueOf(currentInvestigator.getTempXP()));
                    }
                }
            });
            xpIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = currentInvestigator.getTempXP();
                    if (current < currentInvestigator.getAvailableXP()) {
                        currentInvestigator.setTempXP(current + 1);
                        xpSpent.setText(String.valueOf(currentInvestigator.getTempXP()));
                    }
                }
            });
        }

        // If on scenario finish set up defeated spinner and apply OnItemSelectedListener, and set up weakness
        // checkbox for relevant investigators
        else if (globalVariables.getScenarioStage() == 2) {
            // Set up defeated spinner
            xpView.setVisibility(GONE);
            defeatedView.setVisibility(VISIBLE);
            Spinner defeatedSpinner = (Spinner) listItemView.findViewById(R.id.investigator_defeated);
            ArrayAdapter<CharSequence> defeatedAdapter = ArrayAdapter.createFromResource(getContext(), R.array
                    .investigator_eliminated, android.R.layout.simple_spinner_item);
            defeatedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            defeatedSpinner.setAdapter(defeatedAdapter);
            defeatedSpinner.setSelection(currentInvestigator.getTempStatus());
            defeatedSpinner.setOnItemSelectedListener(new ListenerDefeatedSpinner(pos));

            // Set up weakness checkbox
            CheckBox weakness = (CheckBox) listItemView.findViewById(R.id.weakness);
            weakness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        currentInvestigator.setWeakness(1);
                    } else {
                        currentInvestigator.setWeakness(0);
                    }
                }
            });
            if (currentInvestigator.getWeakness() == 1) {
                weakness.setChecked(true);
            }
            switch (currentInvestigator.getName()) {
                case Investigator.ROLAND_BANKS:
                    weakness.setText(R.string.cover_up);
                    weakness.setVisibility(VISIBLE);
                    break;
                case Investigator.SKIDS_OTOOLE:
                    weakness.setText(R.string.hospital_debts);
                    weakness.setVisibility(VISIBLE);
                    break;
                case Investigator.ZOEY_SAMARAS:
                    weakness.setText(R.string.smite_the_wicked);
                    weakness.setVisibility(VISIBLE);
                    break;
                case Investigator.JENNY_BARNES:
                    weakness.setText(R.string.searching_for_izzie);
                    weakness.setVisibility(VISIBLE);
                    break;
                case Investigator.AGNES_BAKER:
                case Investigator.DAISY_WALKER:
                case Investigator.WENDY_ADAMS:
                case Investigator.REX_MURPHY:
                case Investigator.JIM_CULVER:
                case Investigator.ASHCAN_PETE:
                    weakness.setVisibility(GONE);
                    break;
            }
        }

        return listItemView;
    }

    // OnItemSelectedListener for the Defeated Spinner
    private class ListenerDefeatedSpinner extends Activity implements AdapterView.OnItemSelectedListener {

        private int position;

        public ListenerDefeatedSpinner(int pos) {
            position = pos;
        }

        // Sets the tempStatus to the correct value(pending clicking continue)
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            globalVariables.investigators.get(position).setTempStatus(pos);
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
