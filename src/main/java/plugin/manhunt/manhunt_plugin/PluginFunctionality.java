package plugin.manhunt.manhunt_plugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PluginFunctionality implements CommandExecutor, Listener {
    Boolean manhuntOn = false;
    List<Player> nearbyPlayers = new ArrayList<>();
    Player target;
    Location targetlocation;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        if (sender instanceof Player commandSender) {
            target = playerNameParser(args);
            for (Entity playerEntity : commandSender.getNearbyEntities(15, 15, 15)) {
                if (playerEntity instanceof Player player1 &&
                        !playerEntity.getName().equals(target.getName())) {
                    nearbyPlayers.add(player1);
                    player1.getInventory().addItem(createCompass());
                    player1.setCompassTarget(gettargetlocation(target));
                }
            }
            nearbyPlayers.add(commandSender);
            if (!commandSender.getName().equals(target.getName())) {
                commandSender.getInventory().addItem(createCompass());
            }
            manhuntOn = true;
            return true;
        }
        return false;
    }

    public Player playerNameParser(String[] args) {
        return Bukkit.getPlayer(args[0]);
    }

    public ItemStack createCompass() {
        targetlocation = gettargetlocation(target);
        return new ItemStack(Material.COMPASS);
    }

    public Location gettargetlocation(Player target) {
        return target.getLocation();
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (manhuntOn && nearbyPlayers.contains(player)) {
            player.getInventory().addItem(createCompass());
            player.setCompassTarget(gettargetlocation(target));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (manhuntOn &&
                nearbyPlayers.contains(player) &&
                player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS) &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            player.setCompassTarget(gettargetlocation(target));
            target.playNote(target.getLocation(), Instrument.BELL, Note.sharp(1, Note.Tone.C));
        }
    }
}

