package dataaccess;

import java.util.ArrayList;

public class DataAccess {
    private ArrayList <String> authData;
    private ArrayList <String> userData;
    private ArrayList <String> gameData;

    public DataAccess() {
        authData = new ArrayList<>();
    }

    public void clearAuthData(){
        authData = new ArrayList<>();
    }

    public void clearUserData(){
        userData = new ArrayList<>();
    }
    public void clearGameData(){
        gameData = new ArrayList<>();
    }
}
