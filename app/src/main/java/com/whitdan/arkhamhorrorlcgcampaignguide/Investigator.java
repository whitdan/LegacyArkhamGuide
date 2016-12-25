package com.whitdan.arkhamhorrorlcgcampaignguide;

/**
 * Class for storing and accessing relevant attributes for each investigator.
 */

public class Investigator {

    // Sets maximum health and sanity values for the various investigators (correspond to the names in the string array)
    private int[] health = {0,9,5,8,6,7};
    private int[] sanity = {0,5,9,6,8,7};

    public Investigator(int investigator){
        setupInvestigator(investigator);
    }

    // Declaration for loading from SQLite database
    public Investigator(){ }

    public void setupInvestigator(int investigator){
        this.mName = investigator;
        this.mHealth = health[investigator];
        this.mSanity = sanity[investigator];
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

    public void setName(int name){mName = name;}
    // Not yet used, but will be when investigator death is added
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

    public int getName(){return mName;}
    public int getHealth(){return mHealth;}
    public int getSanity(){return mSanity;}
    public int getStatus(){return mStatus;}
    int getDamage(){return mDamage;}
    int getHorror(){return mHorror;}
    int getAvailableXP(){return mAvailableXP;}
}
