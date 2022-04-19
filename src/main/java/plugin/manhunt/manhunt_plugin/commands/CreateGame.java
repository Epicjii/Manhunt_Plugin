package plugin.manhunt.manhunt_plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.manhunt.manhunt_plugin.gui.TargetGui;

public class CreateGame implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        TargetGui targetGui = new TargetGui();

        targetGui.openNewGui((Player) sender);


        return true;
    }
}
