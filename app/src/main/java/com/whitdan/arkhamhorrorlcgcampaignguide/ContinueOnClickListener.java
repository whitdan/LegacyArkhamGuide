package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.finishscenario.FinishScenarioActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.scenariosetup.ScenarioSetupActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign.SelectCampaignActivity;


/**
 * The click listener for the continue button which appears in all scenario setup and scenario finish fragments
 * If on scenario setup, it sets the available XP and advances to scenario finish
 * If on scenario finish, it applies the selected resolution to the scenario and saves all relevant data to the database
 */

public class ContinueOnClickListener implements View.OnClickListener {

    private static GlobalVariables globalVariables;
    private static int option = -1;
    private Context context;
    private Activity activity;

    public ContinueOnClickListener(GlobalVariables mGlobalVariables, Context mContext) {
        globalVariables = mGlobalVariables;
        context = mContext;
    }

    public ContinueOnClickListener(GlobalVariables mGlobalVariables, Context mContext, FragmentActivity
            fragmentActivity) {
        globalVariables = mGlobalVariables;
        context = mContext;
        activity = fragmentActivity;
    }

    public void onClick(View v) {

        // If on unreleased scenario
        if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 5) {
            Intent intent = new Intent(context, SelectCampaignActivity.class);
            context.startActivity(intent);
        }
        // If on campaign start, either continue or set up dunwich dialog
        else if (globalVariables.getCurrentScenario() == 1000) {
            switch (globalVariables.getCurrentCampaign()) {
                // Night of the Zealot
                case 1:
                    // Set current scenario to first scenario
                    globalVariables.setCurrentScenario(1);
                    globalVariables.setScenarioStage(1);

                    // Create campaign specific table
                    ArkhamDbHelper dbHelper = new ArkhamDbHelper(context);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    ContentValues nightValues = new ContentValues();
                    nightValues.put(ArkhamContract.NightEntry.PARENT_ID, globalVariables.getCampaignID());
                    db.insert(ArkhamContract.NightEntry.TABLE_NAME, null, nightValues);

                    // Save the new campaign
                    saveCampaign(context);

                    // Go to scenario setup
                    Intent intent = new Intent(context, ScenarioSetupActivity.class);
                    context.startActivity(intent);
                    break;
                // The Dunwich Legacy
                case 2:
                    // Create dialogfragment for scenario selection
                    DialogFragment dunwichFragment = new ContinueDunwichDialog();
                    dunwichFragment.show(activity.getFragmentManager(), "dunwich");
                    break;
            }
        }
        // If on scenario setup, set available XP and advance to scenario finish
        else if (globalVariables.getScenarioStage() == 1) {
            // Subtract tempXP (as set by the spent XP view) from investigator XP and reset tempXP
            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                int XPSpent = globalVariables.investigators.get(i).getTempXP();
                globalVariables.investigators.get(i).changeXP(-XPSpent);
                globalVariables.investigators.get(i).setTempXP(0);
            }

            // Go to scenario finish
            globalVariables.setScenarioStage(2);
            Intent intent = new Intent(context, FinishScenarioActivity.class);
            context.startActivity(intent);
        }
        // If on campaign finish, set up dialog
        else if (globalVariables.getScenarioStage() == 3) {
            FinishCampaignDialogFragment newFragment = new FinishCampaignDialogFragment();
            newFragment.show(activity.getFragmentManager(), "finish");
        }
        // Save and continue the campaign
        else {
            FinishScenarioDialogFragment newFragment = new FinishScenarioDialogFragment();
            newFragment.show(activity.getFragmentManager(), "continue");
        }
    }


    /*
        Shows a dialog box when finishing the scenario to confirm continuing
     */
    public static class FinishScenarioDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Create a new Alert Dialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Set message
            StringBuilder dialogMessage = new StringBuilder();
            dialogMessage.append("Scenario completed.\n\n");
            if (globalVariables.getResolution() == 0) {
                dialogMessage.append("No resolution.\n");
            } else {
                dialogMessage.append("Resolution " + globalVariables.getResolution() + "\n");
            }
            dialogMessage.append("Victory display: " + globalVariables.getVictoryDisplay() + "\n\n");
            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                Investigator currentInvestigator = globalVariables.investigators.get(i);
                String[] investigatorNames = getActivity().getResources().getStringArray(R.array
                        .investigators);
                String name = investigatorNames[currentInvestigator.getName()];
                String[] investigatorStatuses = getActivity().getResources().getStringArray(R.array
                        .investigator_eliminated);
                String status = investigatorStatuses[currentInvestigator.getTempStatus()];
                dialogMessage.append(name + ": " + status + "\n");
            }
            dialogMessage.append("\nSave and continue?");
            builder.setMessage(dialogMessage);

            // Apply everything to finish the scenario and move on
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Apply the scenario resolution
                    switch (globalVariables.getCurrentCampaign()) {
                        case 1:
                            nightResolutions(globalVariables, getActivity());
                            break;
                        case 2:
                            dunwichResolutions(globalVariables, getActivity());
                            break;
                    }
                    if (globalVariables.getCurrentScenario() > 100) {
                        sideResolutions(globalVariables, getActivity());
                    }

                    // Apply defeats from temp status and weaknesses
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        Investigator currentInvestigator = globalVariables.investigators.get(i);
                        int status = currentInvestigator.getTempStatus();
                        // Add to physical trauma
                        if (status == 2) {
                            currentInvestigator.changeDamage(1);
                        }
                        // Add to mental trauma
                        else if (status == 3) {
                            currentInvestigator.changeHorror(1);
                        }

                        // Reset temp status
                        currentInvestigator.setTempStatus(0);

                        // Apply any relevant weaknesses
                        if (currentInvestigator.getWeakness() == 1) {
                            switch (currentInvestigator.getName()) {
                                case Investigator.ROLAND_BANKS:
                                case Investigator.ZOEY_SAMARAS:
                                case Investigator.JENNY_BARNES:
                                    currentInvestigator.changeHorror(1);
                                    break;
                                case Investigator.SKIDS_OTOOLE:
                                    currentInvestigator.changeXP(-2);
                                    for (; currentInvestigator.getAvailableXP() < 0; ) {
                                        currentInvestigator.changeXP(1);
                                    }
                                    break;
                                case Investigator.AGNES_BAKER:
                                case Investigator.DAISY_WALKER:
                                case Investigator.WENDY_ADAMS:
                                case Investigator.REX_MURPHY:
                                case Investigator.JIM_CULVER:
                                case Investigator.ASHCAN_PETE:
                                    break;
                            }
                            currentInvestigator.setWeakness(0);
                        }

                        // Check health and sanity
                        if ((currentInvestigator.getDamage() >= currentInvestigator.getHealth()) ||
                                (currentInvestigator.getHorror() >= currentInvestigator.getSanity())) {
                            currentInvestigator.setStatus(2);
                        }
                    }

                    // Increment current scenario
                    int nextScenario = 0;
                    if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getFirstScenario() == 2) {
                        if (globalVariables.getCurrentScenario() == 1) {
                            nextScenario = 3;
                        } else if (globalVariables.getCurrentScenario() == 2) {
                            nextScenario = 1;
                        } else {
                            nextScenario = globalVariables.getCurrentScenario() + 1;
                        }
                    } else {
                        nextScenario = globalVariables.getCurrentScenario() + 1;
                    }
                    if (globalVariables.getCurrentScenario() > 100) {
                        nextScenario = globalVariables.getPreviousScenario();
                    }
                    globalVariables.setCurrentScenario(nextScenario);

                    // Reset victory display
                    globalVariables.setVictoryDisplay(0);

                    // Go to campaign finish if appropriate
                    if (globalVariables.getCurrentCampaign() == 1 && globalVariables.getCurrentScenario() == 4) {
                        globalVariables.setScenarioStage(3);
                        globalVariables.setNightCompleted(1);
                    } else {
                        globalVariables.setScenarioStage(1);
                    }

                    // Save the campaign
                    saveCampaign(getActivity());

                    //   Go to scenario setup for the next scenario
                    if (globalVariables.getCurrentCampaign() == 2 && globalVariables.getCurrentScenario() == 5) {
                        Toast toast = Toast.makeText(getActivity(), "This scenario is not available yet.", Toast
                                .LENGTH_SHORT);
                        toast.show();
                    }
                    Intent intent = new Intent(getActivity(), ScenarioSetupActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            return builder.create();
        }
    }


    /*
        Shows a dialog box when finishing the campaign to confirm deletion
     */
    public static class FinishCampaignDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get access to writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            final long position = globalVariables.getCampaignID();

            // Create a new Alert Dialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_option)
                    .setSingleChoiceItems(R.array.campaign_options, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            option = which;
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (option) {
                                // Finish and delete campaign
                                case 0:
                                    // Set selectionArgs as the _ID of the campaign clicked on
                                    String[] selectionArgs = {Long.toString(position)};

                                    // Find all of the relevant rows of the database tables for the campaign clicked on
                                    String campaignSelection = ArkhamContract.CampaignEntry._ID + " = ?";
                                    String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ?";
                                    String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " = ?";

                                    // Delete the rows
                                    db.delete(ArkhamContract.CampaignEntry.TABLE_NAME, campaignSelection,
                                            selectionArgs);
                                    db.delete(ArkhamContract.InvestigatorEntry.TABLE_NAME, investigatorSelection,
                                            selectionArgs);
                                    db.delete(ArkhamContract.NightEntry.TABLE_NAME, nightSelection, selectionArgs);

                                    // Go back to the CampaignSelectActivity
                                    Intent intent = new Intent(getActivity(), SelectCampaignActivity.class);
                                    getActivity().startActivity(intent);
                                    break;
                                // Night of the Zealot
                                case 1:
                                    if (globalVariables.getNightCompleted() == 1) {
                                        Toast toast = Toast.makeText(getActivity(), "You have already completed this " +
                                                "campaign.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        globalVariables.setCurrentCampaign(1);
                                        globalVariables.setCurrentScenario(1000);
                                        globalVariables.setScenarioStage(1);
                                        Intent restart = new Intent(getActivity(), ScenarioSetupActivity.class);
                                        getActivity().startActivity(restart);
                                    }
                                    break;
                                // The Dunwich Legacy
                                case 2:
                                    if (globalVariables.getDunwichCompleted() == 1) {
                                        Toast toast = Toast.makeText(getActivity(), "You have already completed this " +
                                                "campaign.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        // Set Dunwich Legacy to owned in settings
                                        String sharedPrefs = getString(R.string.expacs_owned);
                                        String dunwichOwnedString = getString(R.string.dunwich_campaign_name);
                                        SharedPreferences settings = getActivity().getSharedPreferences(sharedPrefs, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putBoolean(dunwichOwnedString, true);
                                        editor.apply();
                                        globalVariables.setCurrentCampaign(2);
                                        globalVariables.setCurrentScenario(1000);
                                        globalVariables.setScenarioStage(1);
                                        Intent restart = new Intent(getActivity(), ScenarioSetupActivity.class);
                                        getActivity().startActivity(restart);
                                    }
                                    break;
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            return builder.create();
        }

    }


    /*
     Contains all the Night of the Zealot resolutions
    */
    private static void nightResolutions(GlobalVariables globalVariables, Activity parent) {
        int leadInvestigator = globalVariables.getLeadInvestigator();

        /*
         The Gathering (scenario one) resolutions
        */
        if (globalVariables.getCurrentScenario() == 1) {
            switch (globalVariables.getResolution()) {
                // No resolution
                case 0:
                    globalVariables.setHouseStanding(1);
                    globalVariables.setGhoulPriestAlive(1);
                    globalVariables.setLitaStatus(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 2);
                    }
                    break;
                // Resolution one
                case 1:
                    globalVariables.setHouseStanding(0);
                    globalVariables.setGhoulPriestAlive(0);
                    globalVariables.setLitaStatus(1);
                    globalVariables.investigators.get(leadInvestigator).changeHorror(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 2);
                    }
                    break;
                // Resolution two
                case 2:
                    globalVariables.setHouseStanding(1);
                    globalVariables.setGhoulPriestAlive(0);
                    globalVariables.setLitaStatus(0);
                    globalVariables.investigators.get(leadInvestigator).changeXP(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 2);
                    }
                    break;
                // Resolution three
                case 3:
                    globalVariables.setHouseStanding(1);
                    globalVariables.setGhoulPriestAlive(1);
                    globalVariables.setLitaStatus(2);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        if (globalVariables.investigators.get(i).getTempStatus() != 1) {
                            globalVariables.investigators.get(i).setStatus(2);
                        }
                    }
                    break;
            }
        }

        /*
         The Midnight Masks (scenario 2) resolutions
        */
        else if (globalVariables.getCurrentScenario() == 2) {

            // Check if Ghoul Priest is still alive
            CheckBox ghoulPriest = (CheckBox) parent.findViewById(R.id.ghoul_priest_killed);
            if (ghoulPriest.isChecked()) {
                globalVariables.setGhoulPriestAlive(0);
            }

            // Check if each cultist has been interrogated
            globalVariables.setCultistsInterrogated(0);
            CheckBox drew = (CheckBox) parent.findViewById(R.id.drew_interrogated);
            if (drew.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setDrewInterrogated(1);
            } else {
                globalVariables.setDrewInterrogated(0);
            }
            CheckBox herman = (CheckBox) parent.findViewById(R.id.herman_interrogated);
            if (herman.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setHermanInterrogated(1);
            } else {
                globalVariables.setHermanInterrogated(0);
            }
            CheckBox peter = (CheckBox) parent.findViewById(R.id.peter_interrogated);
            if (peter.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setPeterInterrogated(1);
            } else {
                globalVariables.setPeterInterrogated(0);
            }
            CheckBox victoria = (CheckBox) parent.findViewById(R.id.victoria_interrogated);
            if (victoria.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setVictoriaInterrogated(1);
            } else {
                globalVariables.setVictoriaInterrogated(0);
            }
            CheckBox ruth = (CheckBox) parent.findViewById(R.id.ruth_interrogated);
            if (ruth.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setRuthInterrogated(1);
            } else {
                globalVariables.setRuthInterrogated(0);
            }
            CheckBox masked = (CheckBox) parent.findViewById(R.id.masked_hunter_interrogated);
            if (masked.isChecked()) {
                globalVariables.incrementCultistsInterrogated();
                globalVariables.setMaskedInterrogated(1);
            } else {
                globalVariables.setMaskedInterrogated(0);
            }

            switch (globalVariables.getResolution()) {
                case 0:
                    globalVariables.setMidnightStatus(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                case 1:
                    globalVariables.setMidnightStatus(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                case 2:
                    globalVariables.setMidnightStatus(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
            }
        }

        /*
            The Devourer Below resolutions
         */
        else if (globalVariables.getCurrentScenario() == 3) {
            switch (globalVariables.getResolution()) {
                // No resolution
                case 0:
                    globalVariables.setUmordhothStatus(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).setStatus(2);
                    }
                    break;
                case 1:
                    globalVariables.setUmordhothStatus(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 5);
                        globalVariables.investigators.get(i).changeHorror(2);
                    }
                    break;
                case 2:
                    globalVariables.setUmordhothStatus(2);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 10);
                        globalVariables.investigators.get(i).changeHorror(2);
                        globalVariables.investigators.get(i).changeDamage(2);
                    }
                    break;
                case 3:
                    globalVariables.setUmordhothStatus(3);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                        globalVariables.investigators.get(i).changeHorror(2);
                        globalVariables.investigators.get(i).changeDamage(2);
                    }
                    break;
            }
        }
    }


    /*
    Contains all the Dunwich Legacy resolutions
   */
    private static void dunwichResolutions(GlobalVariables globalVariables, Activity parent) {
        int leadInvestigator = globalVariables.getLeadInvestigator();

        /*
         Extracurricular Activity - Scenario 1-A
        */
        if (globalVariables.getCurrentScenario() == 1) {
            switch (globalVariables.getResolution()) {
                // No resolution
                case 0:
                    globalVariables.setWarrenRice(0);
                    globalVariables.setStudents(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 1);
                    }
                    break;
                // Resolution one
                case 1:
                    globalVariables.setWarrenRice(1);
                    globalVariables.setStudents(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution two
                case 2:
                    globalVariables.setWarrenRice(0);
                    globalVariables.setStudents(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution three
                case 3:
                    globalVariables.setWarrenRice(0);
                    globalVariables.setStudents(2);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution four
                case 4:
                    globalVariables.setInvestigatorsUnconscious(1);
                    globalVariables.setWarrenRice(0);
                    globalVariables.setStudents(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 1);
                    }
                    break;
            }
        }

        /*
         The House Always Wins - Scenario 1-B
        */
        else if (globalVariables.getCurrentScenario() == 2) {

            CheckBox cheated = (CheckBox) parent.findViewById(R.id.cheated_checkbox);
            if (cheated.isChecked()) {
                globalVariables.setInvestigatorsCheated(1);
            }

            switch (globalVariables.getResolution()) {
                // No resolution or resolution one
                case 1:
                    globalVariables.setObannionGang(0);
                    globalVariables.setFrancisMorgan(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 1);
                    }
                    break;
                // Resolution two
                case 2:
                    globalVariables.setObannionGang(0);
                    globalVariables.setFrancisMorgan(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution three
                case 3:
                    globalVariables.setObannionGang(1);
                    globalVariables.setFrancisMorgan(0);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution four
                case 4:
                    globalVariables.setObannionGang(0);
                    globalVariables.setFrancisMorgan(0);
                    globalVariables.setInvestigatorsUnconscious(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay() + 1);
                        globalVariables.investigators.get(i).changeDamage(1);
                    }
                    break;
            }
        }

        /*
            Resolution 2: The Miskatonic Museum
         */
        else if (globalVariables.getCurrentScenario() == 4) {
            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
            }
            globalVariables.setNecronomicon(globalVariables.getResolution());
        }
    }

    /*
   Contains all the side story resolutions
  */
    private static void sideResolutions(GlobalVariables globalVariables, Activity parent) {
        int leadInvestigator = globalVariables.getLeadInvestigator();

        /*
         Curse of the Rougarou
        */
        if (globalVariables.getCurrentScenario() == 101) {
            switch (globalVariables.getResolution()) {
                // Resolution one
                case 1:
                    globalVariables.setRougarouStatus(1);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution two
                case 2:
                    globalVariables.setRougarouStatus(2);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
                // Resolution three
                case 3:
                    globalVariables.setRougarouStatus(3);
                    for (int i = 0; i < globalVariables.investigators.size(); i++) {
                        globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
                    }
                    break;
            }
        }
        /*
            Carnevale of Horrors
         */
        else if (globalVariables.getCurrentScenario() == 102) {
            for (int i = 0; i < globalVariables.investigators.size(); i++) {
                globalVariables.investigators.get(i).changeXP(globalVariables.getVictoryDisplay());
            }
            globalVariables.setCarnevaleStatus(globalVariables.getResolution() + 1);
        }
    }

    /*
        DialogFragment for The Dunwich Legacy
     */
    public static class ContinueDunwichDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_option)
                    .setItems(R.array.dunwich_setup_options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    // Set current scenario and first scenario
                                    globalVariables.setCurrentScenario(1);
                                    globalVariables.setFirstScenario(1);
                                    break;
                                case 1:
                                    globalVariables.setCurrentScenario(2);
                                    globalVariables.setFirstScenario(2);
                                    break;
                            }
                            globalVariables.setScenarioStage(1);

                            // Create campaign specific table
                            ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            ContentValues dunwichValues = new ContentValues();
                            dunwichValues.put(ArkhamContract.DunwichEntry.PARENT_ID, globalVariables.getCampaignID());
                            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO,
                                    globalVariables.getFirstScenario());
                            db.insert(ArkhamContract.DunwichEntry.TABLE_NAME, null, dunwichValues);

                            saveCampaign(getActivity());

                            // Go to scenario setup
                            Intent intent = new Intent(getActivity(), ScenarioSetupActivity.class);
                            startActivity(intent);
                        }
                    });
            return builder.create();
        }
    }


    /*
     Saves the campaign, including all relevant variables
    */
    private static void saveCampaign(Context context) {

        // Get a writable database
        ArkhamDbHelper mDbHelper = new ArkhamDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Update campaign variables (currently only the scenario number and which investigators are in use)
        ContentValues campaignValues = new ContentValues();
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables.getCurrentCampaign());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables.getCurrentScenario());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_NIGHT_COMPLETED, globalVariables
                .getNightCompleted());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_DUNWICH_COMPLETED, globalVariables
                .getDunwichCompleted());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_ROLAND_INUSE, globalVariables
                .investigatorsInUse[Investigator.ROLAND_BANKS]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_DAISY_INUSE, globalVariables
                .investigatorsInUse[Investigator.DAISY_WALKER]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_SKIDS_INUSE, globalVariables
                .investigatorsInUse[Investigator.SKIDS_OTOOLE]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_AGNES_INUSE, globalVariables
                .investigatorsInUse[Investigator.AGNES_BAKER]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_WENDY_INUSE, globalVariables
                .investigatorsInUse[Investigator.WENDY_ADAMS]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_ZOEY_INUSE, globalVariables
                .investigatorsInUse[Investigator.ZOEY_SAMARAS]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_REX_INUSE, globalVariables
                .investigatorsInUse[Investigator.REX_MURPHY]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_JENNY_INUSE, globalVariables
                .investigatorsInUse[Investigator.JENNY_BARNES]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_JIM_INUSE, globalVariables
                .investigatorsInUse[Investigator.JIM_CULVER]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_PETE_INUSE, globalVariables
                .investigatorsInUse[Investigator.ASHCAN_PETE]);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_ROUGAROU_STATUS, globalVariables.getRougarouStatus());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_STRANGE_SOLUTION, globalVariables.getStrangeSolution());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CARNEVALE_STATUS, globalVariables.getCarnevaleStatus());
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CARNEVALE_REWARDS, globalVariables.getCarnevaleReward());
        String campaignSelection = ArkhamContract.CampaignEntry._ID + " LIKE ?";
        String[] campaignSelectionArgs = {Long.toString(globalVariables.getCampaignID())};
        db.update(
                ArkhamContract.CampaignEntry.TABLE_NAME,
                campaignValues,
                campaignSelection,
                campaignSelectionArgs);

        // Update all night variables
        if (globalVariables.getCurrentCampaign() == 1) {
            ContentValues nightValues = new ContentValues();
            nightValues.put(ArkhamContract.NightEntry.COLUMN_HOUSE_STANDING, globalVariables.getHouseStanding());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_GHOUL_PRIEST, globalVariables.getGhoulPriestAlive());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_LITA_STATUS, globalVariables.getLitaStatus());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_CULTISTS_INTERROGATED, globalVariables
                    .getCultistsInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_MIDNIGHT_STATUS, globalVariables.getMidnightStatus());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_DREW_INTERROGATED, globalVariables.getDrewInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_HERMAN_INTERROGATED, globalVariables
                    .getHermanInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_PETER_INTERROGATED, globalVariables.getPeterInterrogated
                    ());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_RUTH_INTERROGATED, globalVariables.getRuthInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_VICTORIA_INTERROGATED, globalVariables
                    .getVictoriaInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_MASKED_INTERROGATED, globalVariables
                    .getMaskedInterrogated());
            nightValues.put(ArkhamContract.NightEntry.COLUMN_UMORDHOTH_STATUS, globalVariables.getUmordhothStatus());

            String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " LIKE ?";
            String[] nightSelectionArgs = {Long.toString(globalVariables.getCampaignID())};
            db.update(
                    ArkhamContract.NightEntry.TABLE_NAME,
                    nightValues,
                    nightSelection,
                    nightSelectionArgs);
        }

        // Update all Dunwich variables
        if (globalVariables.getCurrentCampaign() == 2) {
            ContentValues dunwichValues = new ContentValues();
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO, globalVariables.getFirstScenario());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS, globalVariables
                    .getInvestigatorsUnconscious());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_HENRY_ARMITAGE, globalVariables.getHenryArmitage());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_WARREN_RICE, globalVariables.getWarrenRice());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_STUDENTS, globalVariables.getStudents());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_OBANNION_GANG, globalVariables.getObannionGang());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FRANCIS_MORGAN, globalVariables.getFrancisMorgan());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_CHEATED, globalVariables
                    .getInvestigatorsCheated());
            dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_NECRONOMICON, globalVariables.getNecronomicon());

            String dunwichSelection = ArkhamContract.DunwichEntry.PARENT_ID + " LIKE ?";
            String[] dunwichSelectionArgs = {Long.toString(globalVariables.getCampaignID())};
            db.update(
                    ArkhamContract.DunwichEntry.TABLE_NAME,
                    dunwichValues,
                    dunwichSelection,
                    dunwichSelectionArgs);
        }

        // Update investigator entries
        for (int i = 0; i < globalVariables.investigators.size(); i++) {
            ContentValues investigatorValues = new ContentValues();
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_NAME, globalVariables
                    .investigators.get(i).getName());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS, globalVariables
                    .investigators.get(i).getStatus());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE, globalVariables
                    .investigators.get(i).getDamage());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR, globalVariables
                    .investigators.get(i).getHorror());
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_XP, globalVariables
                    .investigators.get(i).getAvailableXP());

            String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " LIKE ?" + " AND " +
                    ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID + " LIKE ?";
            String[] investigatorSelectionArgs = {Long.toString(globalVariables.getCampaignID()), Integer.toString(i)};
            db.update(
                    ArkhamContract.InvestigatorEntry.TABLE_NAME,
                    investigatorValues,
                    investigatorSelection,
                    investigatorSelectionArgs);
        }
    }
}
