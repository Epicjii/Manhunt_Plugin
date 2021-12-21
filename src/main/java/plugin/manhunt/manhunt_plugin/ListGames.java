package plugin.manhunt.manhunt_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public class ListGames implements CommandExecutor {
    HashMap<ManhuntGame, Player> currentGames = Target.currentGames;


    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        if (!currentGames.isEmpty()) {
            for (ManhuntGame manhuntGame : currentGames.keySet()) {
                sender.sendMessage(manhuntGame.target.getName() + "'s Hunt");
            }
        }
        sender.sendMessage("There are currently no active games.");
        return true;
    }
}
