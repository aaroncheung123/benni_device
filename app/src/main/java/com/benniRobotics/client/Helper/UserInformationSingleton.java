package com.benniRobotics.client.Helper;

import android.os.BatteryManager;

import static android.content.Context.BATTERY_SERVICE;

public class UserInformationSingleton {

    private String email;
    private Integer driveProgressNumber = 100;
    private Integer chatProgressNumber = 100;
    private Integer mathProgressNumber = 100;
    private Integer chargeProgressNumber = 100;
    private Integer happinessIndexNumber = 100;
    private Integer loveIndexNumber = 100;
    private Integer robotCharge = 100;
    private Integer robotHeadCharge = 100;
    private String currentID = "";
    private String lastResponse = "";
    private String currentEmotionalState = "Happy";
    private String SERVER_URL = "https://guarded-savannah-87082.herokuapp.com/";
    private Boolean setManualDriveActivityCheck = false;


    //----------------------------------------------
    //
    // SINGLETON SET UP
    //
    //----------------------------------------------
    private static UserInformationSingleton instance = null;

    private UserInformationSingleton() {
        // Exists only to defeat instantiation.
    }

    public static UserInformationSingleton getInstance() {
        if(instance == null) {
            instance = new UserInformationSingleton();
        }
        return instance;
    }



    //----------------------------------------------
    //
    // EMAIL
    //
    //----------------------------------------------
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }


    //----------------------------------------------
    //
    // PROGRESS NUMBERS
    //
    //----------------------------------------------
    public void setDriveProgressNumber(int number){
        if(number >= 100){
            driveProgressNumber = 100;
        }
        else if(number <= 0){
            driveProgressNumber = 0;
        }
        else{
            driveProgressNumber = number;
        }
    }
    public Integer getDriveProgressNumber(){
        return driveProgressNumber;
    }


    public void setChatProgressNumber(int number){
        if(number >= 100){
            chatProgressNumber = 100;
            loveIndexNumber = 100;
        }
        else if(number <= 0){
            chatProgressNumber = 0;
            loveIndexNumber = 0;
        }
        else{
            chatProgressNumber = number;
            loveIndexNumber = number;
        }
    }
    public Integer getChatProgressNumber(){
        return chatProgressNumber;
    }

    public Integer getLoveIndexNumber() {
        return loveIndexNumber;
    }

    public void setLoveIndexNumber(Integer loveIndexNumber) {
        if(loveIndexNumber >= 100){
            this.loveIndexNumber = 100;
        }
        else if(loveIndexNumber <= 0){
            this.loveIndexNumber = 0;
        }
        else{
            this.loveIndexNumber = loveIndexNumber;
        }
    }

    public void setHappinessIndexNumber(int number){
        if(number >= 100){
            this.happinessIndexNumber = 100;
        }
        else if(loveIndexNumber <= 0){
            this.happinessIndexNumber = 0;
        }
        else{
            this.happinessIndexNumber = number;
        }
    }
    public int getHappinessIndexNumber(){
        return happinessIndexNumber;
    }

    public void setMathProgressNumber(int number){
        if(number >= 100){
            mathProgressNumber = 100;
        }
        else if(number <= 0){
            mathProgressNumber = 0;
        }
        else{
            mathProgressNumber = number;
        }
    }
    public Integer getMathProgressNumber(){
        return mathProgressNumber;
    }


    public void setChargeProgressNumber(int number){
        if(number >= 100){
            chargeProgressNumber = 100;
        }
        else if(number <= 0){
            chargeProgressNumber = 0;
        }
        else{
            chargeProgressNumber = number;
        }
    }
    public Integer getChargeProgressNumber(){
        return chargeProgressNumber;
    }


    //----------------------------------------------
    //
    // SERVER URL
    //
    //----------------------------------------------
    public String getSERVER_URL(){
        return SERVER_URL;
    }



    //----------------------------------------------
    //
    // SINGLETON SET UP
    //
    //----------------------------------------------

    public Integer getRobotCharge() {
        return robotCharge;
    }

    public void setRobotCharge(Integer robotCharge) {
        this.robotCharge = robotCharge;
    }

    public Integer getRobotHeadCharge() {
        return robotHeadCharge;
    }

    public void setRobotHeadCharge(Integer robotHeadCharge) {
        this.robotHeadCharge = robotHeadCharge;
    }

    public String getCurrentEmotionalState() {
        return currentEmotionalState;
    }

    public void setCurrentEmotionalState(String currentEmotionalState) {
        this.currentEmotionalState = currentEmotionalState;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }
}
