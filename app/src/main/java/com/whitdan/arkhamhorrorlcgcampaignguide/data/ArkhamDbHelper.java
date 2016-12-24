package com.whitdan.arkhamhorrorlcgcampaignguide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.NightEntry;

/**
 * Custom SQLiteOpenHelper that creates the tables defined in the contract.
 * Currently there are three tables:
 *          campaigns - contains all global variables
 *          investigators - contains a row per investigator, with all relevant variables
 *          night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "campaigns.db";
    private static final int DATABASE_VERSION = 1;

    public ArkhamDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
            Create Strings that contain the SQL statements to create the necessary tables
         */

        // Campaigns table
        String SQL_CREATE_CAMPAIGNS_TABLE =  "CREATE TABLE " + CampaignEntry.TABLE_NAME + " ("
                + CampaignEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CampaignEntry.COLUMN_CAMPAIGN_NAME + " STRING NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_CAMPAIGN + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_SCENARIO + " INTEGER NOT NULL);";

        // Investigators table
        String SQL_CREATE_INVESTIGATORS_TABLE = "CREATE TABLE " + InvestigatorEntry.TABLE_NAME + " ("
                + InvestigatorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InvestigatorEntry.PARENT_ID + " INTEGER NOT NULL, "
                + InvestigatorEntry.INVESTIGATOR_ID + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_NAME + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_XP + " INTEGER NOT NULL);";

        // Night of the Zealot table
        String SQL_CREATE_NIGHT_TABLE = "CREATE TABLE " + NightEntry.TABLE_NAME + " ("
                + NightEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NightEntry.PARENT_ID + " INTEGER NOT NULL, "
                + NightEntry.COLUMN_HOUSE_STANDING + " INTEGER, "
                + NightEntry.COLUMN_GHOUL_PRIEST + " INTEGER, "
                + NightEntry.COLUMN_LITA_STATUS + " INTEGER, "
                + NightEntry.COLUMN_MIDNIGHT_STATUS + " INTEGER, "
                + NightEntry.COLUMN_CULTISTS_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_DREW_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_HERMAN_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_PETER_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_VICTORIA_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_RUTH_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_MASKED_INTERROGATED + " INTEGER);";

        // Execute the SQL statements
        db.execSQL(SQL_CREATE_CAMPAIGNS_TABLE);
        db.execSQL(SQL_CREATE_INVESTIGATORS_TABLE);
        db.execSQL(SQL_CREATE_NIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
