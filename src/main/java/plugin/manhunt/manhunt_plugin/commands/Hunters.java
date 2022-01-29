package plugin.manhunt.manhunt_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hunters implements CommandExecutor {
    public static HashMap<CommandSender, List<Player>> map = new HashMap<>();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        List<Player> hunters = new ArrayList<>();
        for (String arg : args) {
            if (Bukkit.getPlayer(arg) != null) {
                hunters.add(Bukkit.getPlayer(arg));
            }
        }
        map.put(sender, hunters);

        StringBuilder hunterslist = new StringBuilder();
        for (Player hunter : hunters) {
            hunterslist.append(" ").append(hunter.getName());
        }
        sender.sendMessage("Adding" + hunterslist + " to hunters");

        return hunters.size() != 0; //if hunters list is empty, command failed
    }
}