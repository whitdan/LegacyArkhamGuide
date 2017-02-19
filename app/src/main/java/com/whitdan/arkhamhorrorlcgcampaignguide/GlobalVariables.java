package com.whitdan.arkhamhorrorlcgcampaignguide;

import android.app.Application;

import java.util.ArrayList;

/**
 * Stores all global variables.
 */

public class GlobalVariables extends Application {

    // Campaign ID (in the SQLite database)
    private long mCampaignID;
    public void setCampaignID(long id){this.mCampaignID = id;}
    public long getCampaignID(){return mCampaignID;}

    // Current campaign (1 = Night of the Zealot, 2 = Dunwich Legacy)
    private int mCurrentCampaign;
    public void setCurrentCampaign(int currentCampaign) {
        this.mCurrentCampaign = currentCampaign;
    }
    public int getCurrentCampaign() {
        return mCurrentCampaign;
    }

    // Current scenario (0 = Setup, >100 = standalone, 1000 = setup between campaigns)
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
    public ArrayList<Integer> investigatorNames = new ArrayList<>();
    public int[] investigatorsInUse = {0,0,0,0,0,0,0,0,0,0,0};          // Matches up to the names in the string array
    private int leadInvestigator;
    public void setLeadInvestigator(int lead) {
        leadInvestigator = lead;
    }
    public int getLeadInvestigator() {
        return leadInvestigator;
    }

    // Victory display amount
    private int mVictoryDisplay = 0;
    public void setVictoryDisplay(int xp) {
        mVictoryDisplay = xp;
    }
    public int getVictoryDisplay() {
        return mVictoryDisplay;
    }

    // Completed campaigns
    private int NightCompleted;
    public void setNightCompleted(int var){this.NightCompleted = var;}
    public int getNightCompleted(){return this.NightCompleted;}
    private int DunwichCompleted;
    public void setDunwichCompleted(int var){this.DunwichCompleted = var;}
    public int getDunwichCompleted(){return this.DunwichCompleted;}

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

    private int UmordhothStatus;
    public void setUmordhothStatus(int var){this.UmordhothStatus = var;}
    public int getUmordhothStatus(){return this.UmordhothStatus;}

    // All of the cultists
    private int mCultistsInterrogated = 0;
    public void incrementCultistsInterrogated(){this.mCultistsInterrogated++;}
    public void setCultistsInterrogated(int interrogated){this.mCultistsInterrogated = interrogated;}
    public int getCultistsInterrogated(){return this.mCultistsInterrogated;}
    private int mDrewInterrogated;
    public void setDrewInterrogated(int interrogated){this.mDrewInterrogated = interrogated;}
    public int getDrewInterrogated(){return this.mDrewInterrogated;}
    private int mHermanInterrogated;
    public void setHermanInterrogated(int interrogated){this.mHermanInterrogated = interrogated;}
    public int getHermanInterrogated(){return this.mHermanInterrogated;}
    private int mPeterInterrogated;
    public void setPeterInterrogated(int interrogated){this.mPeterInterrogated = interrogated;}
    public int getPeterInterrogated(){return this.mPeterInterrogated;}
    private int mVictoriaInterrogated;
    public void setVictoriaInterrogated(int interrogated){this.mVictoriaInterrogated = interrogated;}
    public int getVictoriaInterrogated(){return this.mVictoriaInterrogated;}
    private int mRuthInterrogated;
    public void setRuthInterrogated(int interrogated){this.mRuthInterrogated = interrogated;}
    public int getRuthInterrogated(){return this.mRuthInterrogated;}
    private int mMaskedInterrogated;
    public void setMaskedInterrogated(int interrogated){this.mMaskedInterrogated = interrogated;}
    public int getMaskedInterrogated(){return this.mMaskedInterrogated;}


    /*
        The Dunwich Legacy variables
     */
    private int FirstScenario;         // Used for determining which scenario the player completed first
    public void setFirstScenario(int first){this.FirstScenario = first;}
    public int getFirstScenario(){return this.FirstScenario;}
    private int InvestigatorsUnconscious = 0;
    public void setInvestigatorsUnconscious(int unconscious){this.InvestigatorsUnconscious = unconscious;}
    public int getInvestigatorsUnconscious(){return this.InvestigatorsUnconscious;}
    private int HenryArmitage;
    public void setHenryArmitage(int var){this.HenryArmitage = var;}
    public int getHenryArmitage(){return this.HenryArmitage;}
    private int WarrenRice;         // 0 = kidnapped, 1 = rescued
    public void setWarrenRice(int var){this.WarrenRice = var;}
    public int getWarrenRice(){return this.WarrenRice;}
    private int Students;           // 0 = failed, 1 = saved, 2 = Experiment defeated
    public void setStudents(int var){this.Students = var;}
    public int getStudents(){return this.Students;}
    private int ObannionGang;       // 0 = bone to pick, 1 = has back
    public void setObannionGang(int var){this.ObannionGang = var;}
    public int getObannionGang(){return this.ObannionGang;}
    private int FrancisMorgan;
    public void setFrancisMorgan(int var){this.FrancisMorgan = var;}
    public int getFrancisMorgan(){return this.FrancisMorgan;}
    private int InvestigatorsCheated;
    public void setInvestigatorsCheated(int var){this.InvestigatorsCheated = var;}
    public int getInvestigatorsCheated(){return this.InvestigatorsCheated;}
    private int Necronomicon;
    public void setNecronomicon(int var){this.Necronomicon = var;}
    public int getNecronomicon(){return this.Necronomicon;}

    /*
    Side story variables
     */
    private int PreviousScenario;
    public void setPreviousScenario(int var){this.PreviousScenario = var;}
    public int getPreviousScenario(){return this.PreviousScenario;}
    private int RougarouStatus;     // 1 = alive, 2 = defeated, 3 = escaped
    public void setRougarouStatus(int var){this.RougarouStatus = var;}
    public int getRougarouStatus(){return this.RougarouStatus;}
    private int CarnevaleStatus;    // 1 = many sacrificed, 2 = banished, 3 = retreated
    public void setCarnevaleStatus(int var){this.CarnevaleStatus = var;}
    public int getCarnevaleStatus(){return this.CarnevaleStatus;}
    private int CarnevaleReward;    // 1 = sacrifice made, 2 = abbess satisfied
    public void setCarnevaleReward(int var){this.CarnevaleReward = var;}
    public int getCarnevaleReward(){return this.CarnevaleReward;}

    /*
    Player cards
     */
    private int StrangeSolution;
    public void setStrangeSolution(int var){this.StrangeSolution = var;}
    public int getStrangeSolution(){return this.StrangeSolution;}
}
