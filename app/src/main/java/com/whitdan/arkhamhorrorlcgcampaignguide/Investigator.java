package com.whitdan.arkhamhorrorlcgcampaignguide;

/**
 * Class for storing and accessing relevant attributes for each investigator.
 */

public class Investigator {

    public Investigator(int investigator){
        this.mName = investigator;
    }

    // Declaration for loading from SQLite database
    public Investigator(){ }

    // Basic attributes for all investigators
    private int mName;
    private int mStatus = 1;    // 0 = not in use, 1 = in use, 2 = dead, 3 = resigned, 4 = health, 5 = horror
    private int mDamage = 0;
    private int mHorror = 0;
    private int mAvailableXP = 0;

    // Temp variables for when a change might be pending clicking the continue button
    private int mTempXP = 0;
    private int mTempStatus = 0;

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
    int getStatus(){return mStatus;}
    int getDamage(){return mDamage;}
    int getHorror(){return mHorror;}
    int getAvailableXP(){return mAvailableXP;}
}
