package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryGameDAO implements GameDAO{
    private Map<Integer,GameData> gameDataMap = new HashMap<>();

    private int makeNewGameID(){
        return gameDataMap.size() +1;
    }

    @Override
    public int createGame(String gameName) {
        int gameID = makeNewGameID();
        GameData newGameData = new GameData(gameID,null,null,gameName,new ChessGame());
        gameDataMap.put(gameID,newGameData);
        return gameID;
    }

    @Override
    public GameData getGame(int gameID) {
        return gameDataMap.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() {
        return gameDataMap.values();
    }

    @Override
    public void updateGame(GameData gameData) {
        int gameID = gameData.gameID();
        gameDataMap.replace(gameID,gameDataMap.get(gameID),gameData);
    }

    @Override
    public void clear() {
        gameDataMap.clear();
    }
}
