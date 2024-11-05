package dataaccess;

import model.UserData;

public interface UserDAO {
    public void createUser(UserData user) throws DataAccessException;
    public UserData getUser(String UserID) throws DataAccessException;
    public void clear() throws DataAccessException;
}
