package server;

import RequestClasses.BadRequestException;
import RequestClasses.RegisterRequest;
import ResponseClasses.ClearResponse;
import ResponseClasses.RegisterResponse;
import dataaccess.*;
import spark.*;
import MyServices.*;
import com.google.gson.Gson;

public class Server {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;
    private UserService userService;
    private GameService gameService;
    private ClearService clearService;

    public Server() {
        authDAO = new MemoryAuthDao();
        gameDAO = new MemoryGameDAO();
        userDAO = new MemoryUserDAO();
        //TODO:Remember to replace these with SQL versions for phase 4!

        userService = new UserService();
        gameService = new GameService();
        clearService = new ClearService();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearHandler);
        Spark.post("/user", this::registerHandler);
        Spark.post("/session", this::loginHandler);
        Spark.delete("/session", this::logoutHandler);
        Spark.get("/game", this::listGamesHandler);
        Spark.post("/game", this::createGameHandler);
        Spark.put("/game", this::joinGameHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public Object clearHandler(Request req, Response res) {
        ClearResponse clearResponse = clearService.clear();
        res.status(200);
        return new Gson().toJson(clearResponse);
    }

    private Object registerHandler(Request request, Response response) {
        try {
            RegisterRequest newRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
            RegisterResponse registerResponse = userService.register(newRequest);
            response.status(200);
            return new Gson().toJson(registerResponse);
        } catch (DataAccessException e) {
            response.status(500);
            return new Gson().toJson(new ErrorMessage(e.getMessage()));
        } catch (BadRequestException e) {
            response.status(400);
            return new Gson().toJson(new ErrorMessage(e.getMessage()));

        } catch (AlreadyTakenException e) {
            response.status(403);
            return new Gson().toJson(new ErrorMessage(e.getMessage()));

        }

    }

    private Object loginHandler(Request request, Response response) {
        userService.login();
        return null;
    }

    private Object logoutHandler(Request request, Response response) {
        userService.logout();
        return null;
    }

    private Object listGamesHandler(Request request, Response response) {
        gameService.listGames();
        return null;
    }

    private Object createGameHandler(Request request, Response response) {
        var game = new Gson().fromJson(request.body(), String.class);
        gameService.createGame(game);
        return null;
    }

    private Object joinGameHandler(Request request, Response response) {
        gameService.joinGame();
        return null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
