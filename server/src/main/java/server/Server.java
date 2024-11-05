package server;

import dataaccess.*;
import spark.*;
import MyServices.*;
import com.google.gson.Gson;

public class Server {
    private DataAccess dataAccessor;
    public Server(){
        dataAccessor = new DataAccess();
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db",this::clearHandler);
        Spark.post("/user",this::registerHandler);
        Spark.post("/session",this::loginHandler);
        Spark.delete("/session",this::logoutHandler);
        Spark.get("/game",this::listGamesHandler);
        Spark.post("/game",this::createGameHandler);
        Spark.put("/game",this::joinGameHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public Object clearHandler(Request req, Response res){

        return "";
    }

    private Object registerHandler(Request request, Response response) {
        UserService service = new UserService();
        service.register();
        return null;
    }

    private Object loginHandler(Request request, Response response) {
        UserService service = new UserService();
        service.login();
        return null;
    }

    private Object logoutHandler(Request request, Response response) {
        UserService service = new UserService();
        service.logout();
        return null;
    }

    private Object listGamesHandler(Request request, Response response) {
        GameService service = new GameService();
        service.listGames();
        return null;
    }

    private Object createGameHandler(Request request, Response response) {
        GameService service = new GameService();
        var game = new Gson().fromJson(request.body(),String.class);
        service.createGame(game);
        return null;
    }

    private Object joinGameHandler(Request request, Response response) {
        GameService service = new GameService();
        service.joinGame();
        return null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
