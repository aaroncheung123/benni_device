package com.aaroncheung.client.Networking;

public class UserInformationSingleton {

    private String email;
    private Integer driveProgressNumber = 100;
    private Integer chatProgressNumber = 100;
    private Integer mathProgressNumber = 100;
    private Integer chargeProgressNumber = 100;

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

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setDriveProgressNumber(int number){
        driveProgressNumber = number;
    }
    public Integer getDriveProgressNumber(){
        return driveProgressNumber;
    }


    public void setChatProgressNumber(int number){
        chatProgressNumber = number;
    }
    public Integer getChatProgressNumber(){
        return chatProgressNumber;
    }


    public void setMathProgressNumber(int number){
        mathProgressNumber = number;
    }
    public Integer getMathProgressNumber(){
        return mathProgressNumber;
    }


    public void setChargeProgressNumber(int number){
        chargeProgressNumber = number;
    }
    public Integer getChargeProgressNumber(){
        return chargeProgressNumber;
    }



}
