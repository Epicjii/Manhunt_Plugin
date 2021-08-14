package plugin.manhunt.manhunt_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManhuntPluginStop implements CommandExecutor {
    List<ManhuntGame> currentGames = ManhuntPluginStart.currentGames;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        for (ManhuntGame manhuntGame : currentGames) {
            if (manhuntGame.nearbyPlayers.contains(sender)) {
                currentGames.remove(manhuntGame);
                manhuntGame.unRegisterEvent();
                return true;
            }
        }
        return false;
    }
}
