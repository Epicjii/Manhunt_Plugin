package plugin.manhunt.manhunt_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ListGames implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        Set<CommandSender> senderkey = Hunters.map.keySet();

        if (!Hunters.map.isEmpty()) {
            for (CommandSender gameCreator : senderkey) {
                sender.sendMessage(gameCreator.getName() + "'s Game");
            }
            return true;
        }

        sender.sendMessage("There are currently no active games.");
        return true;
    }
}
