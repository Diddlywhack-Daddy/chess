package server;

import dataaccess.DataAccess;
import spark.*;
import MyServices.*;

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
        DeleteService service = new DeleteService(dataAccessor);
        service.delete();
        return "";
    }

    private Object registerHandler(Request request, Response response) {
        return null;
    }

    private Object loginHandler(Request request, Response response) {
        return null;
    }

    private Object logoutHandler(Request request, Response response) {
        return null;
    }

    private Object listGamesHandler(Request request, Response response) {
        return null;
    }

    private Object createGameHandler(Request request, Response response) {
        return null;
    }

    private Object joinGameHandler(Request request, Response response) {
        return null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
