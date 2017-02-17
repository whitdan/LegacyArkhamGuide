package com.whitdan.arkhamhorrorlcgcampaignguide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.NightEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.DunwichEntry;

import static com.whitdan.arkhamhorrorlcgcampaignguide.data.ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID;

/**
 * Custom SQLiteOpenHelper that creates the tables defined in the contract.
 * Currently there are three tables:
 * campaigns - contains all global variables
 * investigators - contains a row per investigator, with all relevant variables
 * night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "campaigns.db";
    private static final int DATABASE_VERSION = 8;

    public ArkhamDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
            Create Strings that contain the SQL statements to create the necessary tables
         */

        // Campaigns table
        String SQL_CREATE_CAMPAIGNS_TABLE = "CREATE TABLE " + CampaignEntry.TABLE_NAME + " ("
                + CampaignEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CampaignEntry.COLUMN_CAMPAIGN_NAME + " STRING NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_CAMPAIGN + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_SCENARIO + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_NIGHT_COMPLETED + " INTEGER, "
                + CampaignEntry.COLUMN_DUNWICH_COMPLETED + " INTEGER, "
                + CampaignEntry.COLUMN_ROLAND_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_DAISY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_SKIDS_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_AGNES_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_WENDY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_ZOEY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_REX_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JENNY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JIM_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_PETE_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_ROUGAROU_STATUS + " INTEGER, "
                + CampaignEntry.COLUMN_STRANGE_SOLUTION + " INTEGER, "
                + CampaignEntry.COLUMN_CARNEVALE_STATUS + " INTEGER, "
                + CampaignEntry.COLUMN_CARNEVALE_REWARDS + " INTEGER);";

        // Investigators table
        String SQL_CREATE_INVESTIGATORS_TABLE = "CREATE TABLE " + InvestigatorEntry.TABLE_NAME + " ("
                + InvestigatorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InvestigatorEntry.PARENT_ID + " INTEGER NOT NULL, "
                + INVESTIGATOR_ID + " INTEGER NOT NULL, "
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
                + NightEntry.COLUMN_MASKED_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_UMORDHOTH_STATUS + " INTEGER);";

        // The Dunwich Legacy table
        String SQL_CREATE_DUNWICH_TABLE = "CREATE TABLE " + DunwichEntry.TABLE_NAME + " ("
                + DunwichEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DunwichEntry.PARENT_ID + " INTEGER NOT NULL, "
                + DunwichEntry.COLUMN_FIRST_SCENARIO + " INTEGER, "
                + DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS + " INTEGER, "
                + DunwichEntry.COLUMN_HENRY_ARMITAGE + " INTEGER, "
                + DunwichEntry.COLUMN_WARREN_RICE + " INTEGER, "
                + DunwichEntry.COLUMN_STUDENTS + " INTEGER, "
                + DunwichEntry.COLUMN_FRANCIS_MORGAN + " INTEGER, "
                + DunwichEntry.COLUMN_OBANNION_GANG + " INTEGER, "
                + DunwichEntry.COLUMN_INVESTIGATORS_CHEATED + " INTEGER, "
                + DunwichEntry.COLUMN_NECRONOMICON + " INTEGER);";

        // Execute the SQL statements
        db.execSQL(SQL_CREATE_CAMPAIGNS_TABLE);
        db.execSQL(SQL_CREATE_INVESTIGATORS_TABLE);
        db.execSQL(SQL_CREATE_NIGHT_TABLE);
        db.execSQL(SQL_CREATE_DUNWICH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                String SQL_UPGRADE_ONE_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ZOEY_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_REX_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_JENNY_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_JIM_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_FIVE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_PETE_INUSE + " INTEGER";

                db.execSQL(SQL_UPGRADE_ONE_ONE);
                db.execSQL(SQL_UPGRADE_ONE_TWO);
                db.execSQL(SQL_UPGRADE_ONE_THREE);
                db.execSQL(SQL_UPGRADE_ONE_FOUR);
                db.execSQL(SQL_UPGRADE_ONE_FIVE);
            case 2:
                String SQL_CREATE_DUNWICH_TABLE = "CREATE TABLE " + DunwichEntry.TABLE_NAME + " ("
                        + DunwichEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DunwichEntry.PARENT_ID + " INTEGER NOT NULL, "
                        + DunwichEntry.COLUMN_FIRST_SCENARIO + " INTEGER, "
                        + DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS + " INTEGER, "
                        + DunwichEntry.COLUMN_HENRY_ARMITAGE + " INTEGER, "
                        + DunwichEntry.COLUMN_WARREN_RICE + " INTEGER, "
                        + DunwichEntry.COLUMN_STUDENTS + " INTEGER, "
                        + DunwichEntry.COLUMN_FRANCIS_MORGAN + " INTEGER, "
                        + DunwichEntry.COLUMN_OBANNION_GANG + " INTEGER, "
                        + DunwichEntry.COLUMN_INVESTIGATORS_CHEATED + " INTEGER);";
                db.execSQL(SQL_CREATE_DUNWICH_TABLE);
            case 3:
            case 4:
                String SQL_UPGRADE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ROUGAROU_STATUS + " INTEGER";
                db.execSQL(SQL_UPGRADE_THREE);
            case 5:
                String SQL_UPGRADE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_STRANGE_SOLUTION + " INTEGER";
                db.execSQL(SQL_UPGRADE_FOUR);
            case 6:
                String SQL_UPGRADE_FIVE_ONE = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " + DunwichEntry
                        .COLUMN_NECRONOMICON + " INTEGER";
                String SQL_UPGRADE_FIVE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CARNEVALE_STATUS + " INTEGER";
                String SQL_UPGRADE_FIVE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CARNEVALE_REWARDS + " INTEGER";
                db.execSQL(SQL_UPGRADE_FIVE_ONE);
                db.execSQL(SQL_UPGRADE_FIVE_TWO);
                db.execSQL(SQL_UPGRADE_FIVE_THREE);
            case 7:
                String SQL_UPGRADE_SIX_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_NIGHT_COMPLETED + " INTEGER";
                String SQL_UPGRADE_SIX_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_DUNWICH_COMPLETED + " INTEGER";
                String SQL_UPGRADE_SIX_THREE = "ALTER TABLE " + NightEntry.TABLE_NAME + " ADD COLUMN " +
                        NightEntry.COLUMN_UMORDHOTH_STATUS + " INTEGER";
                db.execSQL(SQL_UPGRADE_SIX_ONE);
                db.execSQL(SQL_UPGRADE_SIX_TWO);
                db.execSQL(SQL_UPGRADE_SIX_THREE);
        }
    }
}
