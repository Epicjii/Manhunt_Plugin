package plugin.manhunt.manhunt_plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.manhunt.manhunt_plugin.commands.CreateGame;
import plugin.manhunt.manhunt_plugin.commands.EndGame;
import plugin.manhunt.manhunt_plugin.commands.JoinGame;
import plugin.manhunt.manhunt_plugin.game.GameData;
import plugin.manhunt.manhunt_plugin.gui.JoinGui;
import plugin.manhunt.manhunt_plugin.gui.TargetGui;

public final class ManhuntPlugin extends JavaPlugin {

    public static ManhuntPlugin instance;
    public static GameData gameData = new GameData();

    public static ManhuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getServer().getPluginManager().registerEvents(new TargetGui(), this);
        getServer().getPluginManager().registerEvents(new JoinGui(), this);

        getCommand("endgame").setExecutor(new EndGame());
        getCommand("newgame").setExecutor(new CreateGame());
        getCommand("joingame").setExecutor(new JoinGame());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();
    }

}
