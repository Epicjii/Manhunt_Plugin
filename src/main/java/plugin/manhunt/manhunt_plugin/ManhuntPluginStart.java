package plugin.manhunt.manhunt_plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ManhuntPluginStart implements CommandExecutor {
    public static List<ManhuntGame> currentGames = new ArrayList<>();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        if (!playerNameParser(args).isOnline()) {
            return false;
        }
        currentGames.add(new ManhuntGame(sender, args));
        return true;
    }

    public Player playerNameParser(String[] args) {
        return Bukkit.getPlayer(args[0]);
    }
}

