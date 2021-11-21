package plugin.manhunt.manhunt_plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
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
    Player target;
    Location targetlocation;
    List<ItemStack> activecompasses = new ArrayList<>();
    List<Player> hunters;

    public ManhuntGame(List<Player> hunters, Player target) {
        this.hunters = hunters;
        this.target = target;
        for (Player hunter : hunters) {
            hunter.getInventory().addItem(createCompass());
            hunter.sendRawMessage("The hunt is on! Your target is: " + target.getName());
        }
        target.sendRawMessage("The hunt is on! Good luck.... You'll need it.");
        registerEvent();
    }

    public void registerEvent() {
        getServer().getPluginManager().registerEvents(this, ManhuntPlugin.getInstance());
    }

    public void unRegisterEvent() {
        HandlerList.unregisterAll(this);
    }


    public ItemStack createCompass() {
        targetlocation = getTargetLocation(target);
        Component name = Component.text(target.getName() + " Tracker").color(TextColor.color(128, 0, 128));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Tracker"));

        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

        compassMeta.lore(lore);
        compassMeta.displayName(name);
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(targetlocation);
        compass.setItemMeta(compassMeta);
        activecompasses.add(compass);
        return compass;
    }


    public Location getTargetLocation(Player target) {
        return target.getLocation();
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (hunters.contains(player)) {
            player.getInventory().addItem(createCompass());
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        System.out.println("Right Clicker");
        System.out.println(hunters.contains(player) + ", " + activecompasses.contains(player.getInventory().getItemInMainHand()));
        if (hunters.contains(player) && activecompasses.contains(player.getInventory().getItemInMainHand()) &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("success"));
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
