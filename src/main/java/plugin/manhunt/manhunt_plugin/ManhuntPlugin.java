package plugin.manhunt.manhunt_plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.manhunt.manhunt_plugin.commands.*;
import plugin.manhunt.manhunt_plugin.gui.TargetGui;

public final class ManhuntPlugin extends JavaPlugin {

    public static ManhuntPlugin instance;

    public static ManhuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getServer().getPluginManager().registerEvents(new TargetGui(), this);

        getCommand("hunters").setExecutor(new Hunters());
        getCommand("target").setExecutor(new Target());
        getCommand("endgame").setExecutor(new Endgame());
        getCommand("listgames").setExecutor(new ListGames());

        getCommand("newgame").setExecutor(new CreateGame());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();
    }

}
