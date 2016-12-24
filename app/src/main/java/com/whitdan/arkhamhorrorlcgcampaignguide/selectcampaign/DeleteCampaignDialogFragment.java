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
 * Dialog box to confirm and then delete a saved campaign after it has been long clicked.
 */

public class DeleteCampaignDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get access to a writable SQLite database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Get the position of the item which was long clicked (passed from the listener)
        Bundle bundle = this.getArguments();
        final long position = bundle.getInt("pos");

        // Create a new Alert Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete campaign?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Set selectionArgs as the _ID of the campaign clicked on
                String[] selectionArgs = {Long.toString(position)};

                // Find all of the relevant rows of the database tables for the campaign clicked on
                String campaignSelection = ArkhamContract.CampaignEntry._ID + " = ?";
                String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ?";
                String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " = ?";

                // Delete the rows
                db.delete(ArkhamContract.CampaignEntry.TABLE_NAME, campaignSelection, selectionArgs);
                db.delete(ArkhamContract.InvestigatorEntry.TABLE_NAME, investigatorSelection, selectionArgs);
                db.delete(ArkhamContract.NightEntry.TABLE_NAME, nightSelection, selectionArgs);

                // Refresh the ListView by re-querying the cursor
                SelectCampaignActivity activity = (SelectCampaignActivity) getActivity();
                CampaignsListAdapter campaignsListAdapter = activity.getCampaignsListAdapter();
                Cursor campaignsCursor = db.rawQuery("SELECT  * FROM " + ArkhamContract.CampaignEntry.TABLE_NAME, null);
                campaignsListAdapter.swapCursor(campaignsCursor);
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
