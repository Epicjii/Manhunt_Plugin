package plugin.manhunt.manhunt_plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.manhunt.manhunt_plugin.gui.JoinGui;

public class JoinGame implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        JoinGui joinGui = new JoinGui();
        joinGui.openNewGui((Player) sender);


        return true;
    }
}
