package com.aaroncheung.client.Helper;

import android.os.BatteryManager;

import static android.content.Context.BATTERY_SERVICE;

public class UserInformationSingleton {

    private String email;
    private Integer driveProgressNumber;
    private Integer chatProgressNumber;
    private Integer mathProgressNumber;
    private Integer chargeProgressNumber;
    private Integer happinessIndexNumber;
    private String SERVER_URL = "https://guarded-savannah-87082.herokuapp.com/";


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
        }
        else if(number <= 0){
            chatProgressNumber = 0;
        }
        else{
            chatProgressNumber = number;
        }
    }
    public Integer getChatProgressNumber(){
        return chatProgressNumber;
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
    public void setHappinessIndexNumber(int number){
        happinessIndexNumber = number;
    }
    public int getHappinessIndexNumber(){
        return happinessIndexNumber;
    }


}
