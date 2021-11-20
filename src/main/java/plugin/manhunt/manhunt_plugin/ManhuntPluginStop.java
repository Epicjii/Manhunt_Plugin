package plugin.manhunt.manhunt_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManhuntPluginStop implements CommandExecutor {
    List<ManhuntGame> currentGames = Target.currentGames;
    List<Player> hunters = Hunters.hunters;

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
            ItemStack compass = manhuntGame.compass;
            for (Player player : manhuntGame.hunters) {
                if (player.getInventory().contains(compass) && compass.getItemMeta().hasLore()) {
                    player.getInventory().remove(compass);
                }
                player.sendRawMessage("The Manhunt has ended!");
            }
            manhuntGame.target.sendRawMessage("You are safe.... for now.");
            currentGames.remove(manhuntGame);
            hunters.clear();
            manhuntGame.unRegisterEvent();
            return true;
        }
        return false;
    }
}