package plugin.manhunt.manhunt_plugin;

import net.kyori.adventure.text.Component;
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
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PluginFunctionality implements CommandExecutor, Listener {
    Boolean manhuntOn = false;
    List<Player> nearbyPlayers = new ArrayList<>();
    Player target;
    Location targetlocation;
    ItemStack compass = new ItemStack(Material.COMPASS);
    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

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
//                    player1.setCompassTarget(getTargetLocation(target));
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
        targetlocation = getTargetLocation(target);
        Component name = Component.text(target.getName() + " Tracker");

        compassMeta.displayName(name);
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(targetlocation);
        compass.setItemMeta(compassMeta);
        return compass;
    }

    public Location getTargetLocation(Player target) {
        return target.getLocation();
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (manhuntOn && nearbyPlayers.contains(player)) {
            player.getInventory().addItem(createCompass());
//            player.setCompassTarget(getTargetLocation(target));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (manhuntOn &&
                nearbyPlayers.contains(player) &&
                player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS) &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (target.getLocation().getWorld().getEnvironment() !=
                    player.getLocation().getWorld().getEnvironment()) {
                player.sendRawMessage("Target is not in your dimension!");
                return;
            }
            player.getInventory().setItemInMainHand(createCompass());
            player.sendRawMessage("Tracking " + target.getName());
            target.playNote(target.getLocation(), Instrument.BELL, Note.sharp(1, Note.Tone.C));
        }
    }
}

