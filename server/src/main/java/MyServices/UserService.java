package MyServices;


import RequestClasses.RegisterRequest;
import ResponseClasses.RegisterResponse;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

//service for Register, Login, and Logout
public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    public RegisterResponse register(RegisterRequest req) {
        try {
            if(userDAO.getUser(req.username())!=null){
                throw new RuntimeException();
                //TODO: make some exception classes
            }
                userDAO.createUser(new UserData(req.username(), req.password(), req.email()));
                AuthData authData = authDAO.createAuth(req.username());
                String authToken = authData.authToken();
                return new RegisterResponse(req.username(),authToken);

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
        
    }

    public void logout() {
    }
}
