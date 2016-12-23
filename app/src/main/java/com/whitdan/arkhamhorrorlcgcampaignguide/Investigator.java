package com.whitdan.arkhamhorrorlcgcampaignguide;

/**
 * Class for storing and accessing relevant attributes for each investigator.
 */

public class Investigator {

    public Investigator(int investigator){
        this.mStatus = 1;
        this.mName = investigator;
    }

    public Investigator(){
        this.mStatus = 1;
    }

    private int mName;
    private int mStatus = 0;    // 0 = not in use, 1 = in use, 2 = dead, 3 = resigned, 4 = health, 5 = horror
    private int mTempStatus = 0;
    private int mDamage = 0;
    private int mTempDamage = 0;
    private int mHorror = 0;
    private int mTempHorror = 0;
    private int mAvailableXP = 0;
    private int mTempXP = 0;

    public void setName(int name){mName = name;}

    public void setTempXP(int XP){
        mTempXP = XP;}
    public void changeXP(int XP){
        mAvailableXP += XP;}

    public void setTempDamage(int damage){
        mTempDamage = damage;}
    public void changeDamage(int damage){
        mDamage += damage;}

    public void setTempHorror(int horror){
        mTempHorror = horror;}
    public void changeHorror(int horror){
        mHorror += horror;}

    public void setTempStatus(int status){mTempStatus = status;}
    public void setStatus(int status){mStatus = status;}

    public int getName(){return mName;}
    public int getStatus(){return mStatus;}
    public int getTempStatus(){return mTempStatus;}
    public int getDamage(){return mDamage;}
    public int getTempDamage(){return mTempDamage;}
    public int getHorror(){return mHorror;}
    public int getTempHorror(){return mTempHorror;}
    public int getAvailableXP(){return mAvailableXP;}
    public int getTempXP(){return mTempXP;}
}
