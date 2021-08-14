package plugin.manhunt.manhunt_plugin;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ManhuntPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        PluginFunctionality plugin = new PluginFunctionality();

        getServer().getPluginManager().registerEvents(plugin, this);


        PluginCommand x = this.getCommand("manhunt");
        if (x != null) {
            x.setExecutor(plugin);
        } else {
            System.out.println("NullCommandException");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
