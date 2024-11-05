package dataaccess;
import model.AuthData;

public interface AuthDAO {
    public AuthData createAuth(String userID) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(AuthData authToken) throws DataAccessException;
    public void clear() throws DataAccessException;

}
