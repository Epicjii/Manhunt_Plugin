package plugin.manhunt.manhunt_plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ManhuntGame implements Listener {
    Boolean manhuntOn = false;
    List<Player> nearbyPlayers = new ArrayList<>();
    Player target;
    Location targetlocation;
    ItemStack compass = new ItemStack(Material.COMPASS);
    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

    public ManhuntGame(CommandSender sender, String[] args) {
        if (sender instanceof Player commandSender) {
            target = playerNameParser(args);
            for (Entity playerEntity : commandSender.getNearbyEntities(15, 15, 15)) {
                if (playerEntity instanceof Player player
                        && !playerEntity.getName().equals(target.getName())
                ) {
                    nearbyPlayers.add(player);
                    player.getInventory().addItem(createCompass());
                    player.sendRawMessage("The hunt is on! Your target is: " + target.getName());
                }
            }
            nearbyPlayers.add(commandSender);
            if (!commandSender.getName().equals(target.getName()) || args[1].equals("debug")) {
                commandSender.getInventory().addItem(createCompass());
            }
            target.sendRawMessage("The hunt is on! Good luck.... You'll need it.");
            registerEvent();
            manhuntOn = true;
        }
    }

    public void registerEvent() {
        getServer().getPluginManager().registerEvents(this, ManhuntPlugin.getInstance());
    }

    public void unRegisterEvent() {
        HandlerList.unregisterAll(this);
    }

    public Player playerNameParser(String[] args) {
        return Bukkit.getPlayer(args[0]);
    }

    public ItemStack createCompass() {
        targetlocation = getTargetLocation(target);
        Component name = Component.text(target.getName() + " Tracker").color(TextColor.color(128, 0, 128));
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
                player.sendRawMessage("The target is not in your dimension!");
                return;
            }
            player.getInventory().setItemInMainHand(createCompass());
            Component tracking = Component.text("Tracking ");
            Component trackee = Component.text(target.getName()).color(TextColor.color(0, 191, 255));
            player.sendMessage(tracking.append(trackee));
            target.playNote(target.getLocation(), Instrument.BELL, Note.sharp(1, Note.Tone.C));
        }
    }
}
