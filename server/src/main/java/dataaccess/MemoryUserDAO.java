package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO{

    private Map<String,UserData> userDataMap = new HashMap<>();
    @Override
    public void createUser(UserData user) {
        userDataMap.put(user.username(),user);
    }

    @Override
    public UserData getUser(String UserID) {
        //Can the username and the UserID be the same? if not implement UUID.randomUUID().toString() in CreateUser
        return userDataMap.get(UserID);
    }

    @Override
    public void clear() {
        userDataMap.clear();
    }
}
