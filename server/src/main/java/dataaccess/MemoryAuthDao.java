package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDAO{
    private Map<String,AuthData> authMap = new HashMap<>();
    //string is authToken, not userName

    @Override
    public AuthData createAuth(String userID) {
        AuthData auth = new AuthData(UUID.randomUUID().toString(),userID);
        authMap.put(auth.authToken(), auth);
        return auth;
    }

    @Override
    public AuthData getAuth(String authToken){
        return authMap.get(authToken);
    }

    @Override
    public void deleteAuth(AuthData authToken)  {
        authMap.remove(authToken.authToken());
    }

    @Override
    public void clear(){
        authMap.clear();
    }
}
