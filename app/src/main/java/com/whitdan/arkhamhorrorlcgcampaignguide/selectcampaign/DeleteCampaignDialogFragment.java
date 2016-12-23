package com.whitdan.arkhamhorrorlcgcampaignguide.selectcampaign;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamDbHelper;

/**
 * Created by danie on 23/12/2016.
 */

public class DeleteCampaignDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Bundle bundle = this.getArguments();
        final long position = bundle.getInt("pos");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete campaign?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String[] selectionArgs = {Long.toString(position)};

                String campaignSelection = ArkhamContract.CampaignEntry._ID + " = ?";
                String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ?";
                String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " = ?";

                db.delete(ArkhamContract.CampaignEntry.TABLE_NAME, campaignSelection, selectionArgs);
                db.delete(ArkhamContract.InvestigatorEntry.TABLE_NAME, investigatorSelection, selectionArgs);
                db.delete(ArkhamContract.NightEntry.TABLE_NAME, nightSelection, selectionArgs);

                SelectCampaignActivity activity = (SelectCampaignActivity) getActivity();
                CampaignsListAdapter campaignsListAdapter = activity.getCampaignsListAdapter();
                Cursor campaignsCursor = activity.getCampaignsCursor();
                campaignsCursor.requery();
                campaignsListAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
