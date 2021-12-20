package plugin.manhunt.manhunt_plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class Target implements CommandExecutor {
    //    public static List<ManhuntGame> currentGames = new ArrayList<>();
    public static HashMap<ManhuntGame, Player> currentGames = new HashMap<>();

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        List<Player> hunters = Hunters.map.get(sender);
        Player target = Bukkit.getPlayer(args[0]);
        currentGames.put(new ManhuntGame(hunters, target), target);
        return true;
    }
}
