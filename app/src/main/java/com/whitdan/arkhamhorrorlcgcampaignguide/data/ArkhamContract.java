package com.whitdan.arkhamhorrorlcgcampaignguide.data;

import android.provider.BaseColumns;

/**
 * Contract for the layout of the SQL tables.
 * Currently there are three tables:
 *          campaigns - contains all global variables
 *          investigators - contains a row per investigator, with all relevant variables
 *          night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ArkhamContract() {}

    /* Inner class that defines the table contents */
    public static class CampaignEntry implements BaseColumns {
        public static final String TABLE_NAME = "campaigns";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CAMPAIGN_NAME = "name";
        public static final String COLUMN_CURRENT_CAMPAIGN = "campaign"; // Denotes which campaign
        public static final String COLUMN_CURRENT_SCENARIO = "scenario";
    }

    public static class InvestigatorEntry implements BaseColumns{
        public static final String TABLE_NAME = "investigators";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String INVESTIGATOR_ID = "investigator_id";
        public static final String COLUMN_INVESTIGATOR_NAME = "name";
        public static final String COLUMN_INVESTIGATOR_STATUS = "status";
        public static final String COLUMN_INVESTIGATOR_DAMAGE = "damage";
        public static final String COLUMN_INVESTIGATOR_HORROR = "horror";
        public static final String COLUMN_INVESTIGATOR_XP = "xp";
    }

    public static class NightEntry implements BaseColumns{
        public static final String TABLE_NAME = "night";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_HOUSE_STANDING = "house_standing";
        public static final String COLUMN_GHOUL_PRIEST = "ghoul_priest";
        public static final String COLUMN_LITA_STATUS = "lita_status";
        public static final String COLUMN_MIDNIGHT_STATUS = "midnight_status";
        public static final String COLUMN_CULTISTS_INTERROGATED = "cultists_interrogated";
        public static final String COLUMN_DREW_INTERROGATED = "drew_interrogated";
        public static final String COLUMN_PETER_INTERROGATED = "peter_interrogated";
        public static final String COLUMN_HERMAN_INTERROGATED = "herman_interrogated";
        public static final String COLUMN_VICTORIA_INTERROGATED = "victoria_interrogated";
        public static final String COLUMN_RUTH_INTERROGATED = "ruth_interrogated";
        public static final String COLUMN_MASKED_INTERROGATED = "masked_interrogated";
    }
}
