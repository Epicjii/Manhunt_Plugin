package plugin.manhunt.manhunt_plugin.game;

import plugin.manhunt.manhunt_plugin.game.ManhuntGame;

import java.util.ArrayList;

public class GameData {
    public ArrayList<ManhuntGame> currentGames = new ArrayList<>();

    public void addGame(ManhuntGame game) {
        currentGames.add(game);
    }

    public void removeGame(ManhuntGame game) {
        currentGames.remove(game);
    }

}
