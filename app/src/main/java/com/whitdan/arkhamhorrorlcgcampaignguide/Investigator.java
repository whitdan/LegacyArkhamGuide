package com.whitdan.arkhamhorrorlcgcampaignguide;

/**
 * Class for storing and accessing relevant attributes for each investigator.
 */

public class Investigator {

    // Integer values for each investigator
    public static final int ROLAND_BANKS = 1;
    public static final int DAISY_WALKER = 2;
    public static final int SKIDS_OTOOLE = 3;
    public static final int AGNES_BAKER = 4;
    public static final int WENDY_ADAMS = 5;
    public static final int ZOEY_SAMARAS = 6;
    public static final int REX_MURPHY = 7;
    public static final int JENNY_BARNES = 8;
    public static final int JIM_CULVER = 9;
    public static final int ASHCAN_PETE = 10;

    // Sets maximum health and sanity values for the various investigators (correspond to the names in the string array)
    private int[] health = {0,9,5,8,6,7,9,6,8,7,6};
    private int[] sanity = {0,5,9,6,8,7,6,9,7,8,5};

    public Investigator(int investigator){
        setupInvestigator(investigator);
    }

    private void setupInvestigator(int investigator){
        this.mName = investigator;
        this.mHealth = health[mName];
        this.mSanity = sanity[mName];
        this.mStatus = 1;
        this.mDamage = 0;
        this.mHorror = 0;
        this.mAvailableXP = 0;
    }

    // Basic attributes for all investigators
    private int mName;
    private int mHealth;
    private int mSanity;
    private int mStatus;    // 0 = not in use, 1 = in use, 2 = dead
    private int mDamage;
    private int mHorror;
    private int mAvailableXP;

    // Temp variables for when a change might be pending clicking the continue button
    private int mTempXP = 0;    // 0 = normal, 1 = resigned, 2 = health, 3 = horror
    private int mTempStatus;
    private int mTempWeakness;  // 0 = not active, 1 = active

    public void setName(int name){mName = name;}
    public void setStatus(int status){mStatus = status;}

    // Changes to numerical values are made by adding the value passed
    public void changeXP(int XP){
        mAvailableXP += XP;}
    public void changeDamage(int damage){
        mDamage += damage;}
    public void changeHorror(int horror){
        mHorror += horror;}

    void setTempStatus(int status){mTempStatus = status;}
    int getTempStatus(){return mTempStatus;}

    void setTempXP(int XP){
        mTempXP = XP;}
    int getTempXP(){return mTempXP;}

    void setWeakness(int weakness){mTempWeakness = weakness;}
    int getWeakness(){return mTempWeakness;}

    public int getName(){return mName;}
    public int getStatus(){return mStatus;}
    int getHealth(){return mHealth;}
    int getSanity(){return mSanity;}
    int getDamage(){return mDamage;}
    int getHorror(){return mHorror;}
    int getAvailableXP(){return mAvailableXP;}
}
