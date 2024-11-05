package MyServices;


import RequestClasses.BadRequestException;
import RequestClasses.RegisterRequest;
import ResponseClasses.RegisterResponse;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

//service for Register, Login, and Logout
public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    public RegisterResponse register(RegisterRequest req) throws DataAccessException,AlreadyTakenException, BadRequestException {
        try {
            if(userDAO.getUser(req.username())!=null){
                throw new AlreadyTakenException("Username already taken");
                //TODO: throw the other exceptions
            }
                userDAO.createUser(new UserData(req.username(), req.password(), req.email()));
                AuthData authData = authDAO.createAuth(req.username());
                String authToken = authData.authToken();
                return new RegisterResponse(req.username(),authToken);

        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void login() {
        
    }

    public void logout() {
    }
}
