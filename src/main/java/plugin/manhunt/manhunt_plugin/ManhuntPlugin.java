package plugin.manhunt.manhunt_plugin;

import org.bukkit.plugin.java.JavaPlugin;
import plugin.manhunt.manhunt_plugin.commands.Endgame;
import plugin.manhunt.manhunt_plugin.commands.Hunters;
import plugin.manhunt.manhunt_plugin.commands.ListGames;
import plugin.manhunt.manhunt_plugin.commands.Target;

public final class ManhuntPlugin extends JavaPlugin {

    public static ManhuntPlugin instance;

    public static ManhuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("hunters").setExecutor(new Hunters());
        getCommand("target").setExecutor(new Target());
        getCommand("endgame").setExecutor(new Endgame());
        getCommand("listgames").setExecutor(new ListGames());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
