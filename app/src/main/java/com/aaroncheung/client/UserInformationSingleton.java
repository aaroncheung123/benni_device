package com.aaroncheung.client;

public class UserInformationSingleton {

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


}
