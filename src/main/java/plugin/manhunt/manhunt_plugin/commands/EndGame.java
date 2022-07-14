package plugin.manhunt.manhunt_plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import plugin.manhunt.manhunt_plugin.game.ManhuntGame;
import plugin.manhunt.manhunt_plugin.ManhuntPlugin;

public class EndGame implements CommandExecutor {


    public static void endgame(ManhuntGame manhuntGame) {
        for (Player player : manhuntGame.players) {
            player.sendRawMessage("The Manhunt has ended!");
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack compass : manhuntGame.activecompasses) {
                player.getInventory().remove(compass);
            }
        }
        ManhuntPlugin.gameData.removeGame(manhuntGame);
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
        for (ManhuntGame manhuntGame : ManhuntPlugin.gameData.currentGames) {
            if (manhuntGame.players.contains(sender)) {
                for (Player player : manhuntGame.players) {
                    for (ItemStack compass : manhuntGame.activecompasses) {
                        player.getInventory().remove(compass);
                    }
                    player.sendRawMessage("The Manhunt has ended!");
                }
                ManhuntPlugin.gameData.removeGame(manhuntGame);
                return true;
            }
        }
        return false;
    }
}