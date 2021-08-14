package plugin.manhunt.manhunt_plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ManhuntPlugin extends JavaPlugin {

    public static ManhuntPlugin instance;

    public static ManhuntPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("manhuntStart").setExecutor(new ManhuntPluginStart());
        getCommand("manhuntStop").setExecutor(new ManhuntPluginStop());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
