package plugin.manhunt.manhunt_plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class Endgame implements CommandExecutor {
    static HashMap<ManhuntGame, Player> currentGames = Target.currentGames;

    public static void endgame(ManhuntGame manhuntGame) {
        for (Player player : manhuntGame.players) {
            player.sendRawMessage("The Manhunt has ended!");
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack compass : manhuntGame.activecompasses) {
                player.getInventory().remove(compass);
            }
        }
        currentGames.remove(manhuntGame);
        manhuntGame.unRegisterEvent();
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        for (ManhuntGame manhuntGame : currentGames.keySet()) {
            if (manhuntGame.players.contains(sender)) {
                for (Player player : manhuntGame.players) {
                    for (ItemStack compass : manhuntGame.activecompasses) {
                        player.getInventory().remove(compass);
                    }
                    player.sendRawMessage("The Manhunt has ended!");
                }
                currentGames.remove(manhuntGame);
                manhuntGame.unRegisterEvent();
                return true;
            }
        }
        return false;
    }
}