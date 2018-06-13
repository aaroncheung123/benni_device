package com.aaroncheung.client.Networking;

public class UserInformationSingleton {

    private String email;
    private Integer driveProgressNumber;
    private Integer chatProgressNumber;
    private Integer mathProgressNumber;
    private Integer chargeProgressNumber;
    private String SERVER_URL = "https://guarded-savannah-87082.herokuapp.com/";

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
        if(number <= 100){
            driveProgressNumber = number;
        }
    }
    public Integer getDriveProgressNumber(){
        return driveProgressNumber;
    }


    public void setChatProgressNumber(int number){
        if(number <= 100) {
            chatProgressNumber = number;
        }
    }
    public Integer getChatProgressNumber(){
        return chatProgressNumber;
    }


    public void setMathProgressNumber(int number){
        if(number <= 100) {
            mathProgressNumber = number;
        }
    }
    public Integer getMathProgressNumber(){
        return mathProgressNumber;
    }


    public void setChargeProgressNumber(int number){
        if(number <= 100) {
            chargeProgressNumber = number;
        }
    }
    public Integer getChargeProgressNumber(){
        return chargeProgressNumber;
    }

    public String getSERVER_URL(){
        return SERVER_URL;
    }


}
