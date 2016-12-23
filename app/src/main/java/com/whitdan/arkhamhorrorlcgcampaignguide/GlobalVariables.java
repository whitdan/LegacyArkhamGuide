package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Application;

import java.util.ArrayList;

/**
 * Stores all global variables.
 */

public class GlobalVariables extends Application {

    // Campaign ID (for SQL purposes)
    private long mCampaignID;
    public void setCampaignID(long id){this.mCampaignID = id;}
    public long getCampaignID(){return mCampaignID;}

    // Current campaign (1 = Night of the Zealot)
    private int mCurrentCampaign;
    public void setCurrentCampaign(int currentCampaign) {
        this.mCurrentCampaign = currentCampaign;
    }
    public int getCurrentCampaign() {
        return mCurrentCampaign;
    }

    // Current scenario (0 = Setup)
    private int mCurrentScenario;
    public void setCurrentScenario(int currentScenario) {
        this.mCurrentScenario = currentScenario;
    }
    public int getCurrentScenario() {
        return mCurrentScenario;
    }

    // Current stage (1 = scenario setup, 2 = scenario finish)
    private int mScenarioStage;
    public void setScenarioStage(int scenarioStage) {
        this.mScenarioStage = scenarioStage;
    }
    public int getScenarioStage() {
        return mScenarioStage;
    }

    // Current resolution (0 = No resolution)
    private int mResolution;
    public void setResolution(int resolution) {
        this.mResolution = resolution;
    }
    public int getResolution() {
        return this.mResolution;
    }

    // Investigators and associated arrays
    public ArrayList<Investigator> investigators = new ArrayList<>();
    public int investigatorNames[] = new int[4];
    private int leadInvestigator;
    public void setLeadInvestigator(int lead) {
        leadInvestigator = lead;
    }
    public int getLeadInvestigator() {
        return leadInvestigator;
    }

    // Victory display amount
    private int mVictoryDisplay;
    public void setVictoryDisplay(int xp) {
        mVictoryDisplay = xp;
    }
    public int getVictoryDisplay() {
        return mVictoryDisplay;
    }

    /*
    Night of the Zealot variables
    */

    private int mHouseStanding; // false = burned to the ground, true = still standing
    public void setHouseStanding(int status) {
        this.mHouseStanding = status;
    }
    public int getHouseStanding() {
        return this.mHouseStanding;
    }

    private int mGhoulPriestAlive;
    public void setGhoulPriestAlive(int status) {
        this.mGhoulPriestAlive = status;
    }
    public int getGhoulPriestAlive() {
        return this.mGhoulPriestAlive;
    }

    private int mLitaStatus;    // 0 = Lita not gained, 1 = Lita gained, 2 = Lita forced to find others (Lita gained)
    public void setLitaStatus(int status) {
        this.mLitaStatus = status;
    }
    public int getLitaStatus() {
        return this.mLitaStatus;
    }

    private int mMidnightStatus;
    public void setMidnightStatus(int status) {
        this.mMidnightStatus = status;
    }
    public int getMidnightStatus() {
        return this.mMidnightStatus;
    }

    // All of the cultists
    private int mCultistsInterrogated = 0;
    public void incrementCultistsInterrogated(){this.mCultistsInterrogated++;}
    public void setCultistsInterrogated(int interrogated){this.mCultistsInterrogated = interrogated;}
    public int getCultistsInterrogated(){return this.mCultistsInterrogated;}
    private int mDrewInterrogated;
    public void setDrewInterrogated(int interrogated){this.mDrewInterrogated = interrogated;};
    public int getDrewInterrogated(){return this.mDrewInterrogated;}
    private int mHermanInterrogated;
    public void setHermanInterrogated(int interrogated){this.mHermanInterrogated = interrogated;};
    public int getHermanInterrogated(){return this.mHermanInterrogated;}
    private int mPeterInterrogated;
    public void setPeterInterrogated(int interrogated){this.mPeterInterrogated = interrogated;};
    public int getPeterInterrogated(){return this.mPeterInterrogated;}
    private int mVictoriaInterrogated;
    public void setVictoriaInterrogated(int interrogated){this.mVictoriaInterrogated = interrogated;};
    public int getVictoriaInterrogated(){return this.mVictoriaInterrogated;}
    private int mRuthInterrogated;
    public void setRuthInterrogated(int interrogated){this.mRuthInterrogated = interrogated;};
    public int getRuthInterrogated(){return this.mRuthInterrogated;}
    private int mMaskedInterrogated;
    public void setMaskedInterrogated(int interrogated){this.mMaskedInterrogated = interrogated;};
    public int getMaskedInterrogated(){return this.mMaskedInterrogated;}
}
