package plugin.manhunt.manhunt_plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Hunters implements CommandExecutor {
    public static List<Player> hunters = new ArrayList<>();


    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        for (String arg : args) {
            hunters.add(Bukkit.getPlayer(arg));
        }
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(hunters.toString()));

        return hunters.size() != 0; //if hunters list is empty command failed
    }
}
