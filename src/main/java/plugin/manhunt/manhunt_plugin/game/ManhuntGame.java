package plugin.manhunt.manhunt_plugin.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import plugin.manhunt.manhunt_plugin.ManhuntPlugin;
import plugin.manhunt.manhunt_plugin.commands.EndGame;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ManhuntGame implements Listener {
    public Player target;
    public List<ItemStack> activecompasses = new ArrayList<>();
    public List<Player> hunters = new ArrayList<>();
    public List<Player> players = new ArrayList<>();
    Location targetlocation;

    public ManhuntGame(Player hunter, Player target) {
        registerEvent();

        this.hunters.add(hunter);
        this.target = target;
        players.addAll(hunters);
        players.add(target);
        hunter.getInventory().addItem(createCompass());

        hunter.sendRawMessage("The hunt is on! Your target is: " + target.getName());
        target.sendRawMessage("The hunt is on! Good luck.... You'll need it.");
        ManhuntPlugin.gameData.addGame(this);
    }

    public void registerEvent() {
        getServer().getPluginManager().registerEvents(this, ManhuntPlugin.getInstance());
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

    public void onHunterJoin(Player newhunter) {
        this.hunters.add(newhunter);
        players.add(newhunter);
        newhunter.getInventory().addItem(createCompass());

        newhunter.sendRawMessage("The hunt is on! Your target is: " + target.getName());
        target.sendRawMessage("The hunt increases! " + newhunter.getName() + " has joined the hunt!");
    }

    public Location getTargetLocation(Player target) {
        return target.getLocation();
    }

    @EventHandler
    public void onRageQuit(PlayerQuitEvent event) {
        Player rageQuitter = event.getPlayer();
        players.remove(rageQuitter);
        hunters.remove(rageQuitter);
        if (rageQuitter.equals(target)) {
            EndGame.endgame(this);
        }
        for (ItemStack compass : activecompasses) {
            rageQuitter.getInventory().remove(compass);
        }
    }

    @EventHandler
    public void uCantDepositThis(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != event.getPlayer()) {
            for (ItemStack compass : activecompasses) {
                event.getInventory().remove(compass);
            }
        }
    }

    @EventHandler
    public void uCantDropThis(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        if (activecompasses.contains(dropped)) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void compassRemover9000(PlayerDeathEvent event) {
        for (ItemStack compass : activecompasses) {
            event.getDrops().remove(compass);
        }
    }

    @EventHandler
    public void onHunterDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (hunters.contains(player)) {
            player.getInventory().addItem(createCompass());
        }
    }

    @EventHandler
    public void onTargetDeath(PlayerRespawnEvent event) {
        Player target = event.getPlayer();
        if (target == this.target) {
            for (Player player : players) {
                player.sendRawMessage(target.getName() + " has died. The Hunters Win!");
            }
            EndGame.endgame(this);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (hunters.contains(player) && activecompasses.contains(player.getInventory().getItemInMainHand()) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (target.getLocation().getWorld().getEnvironment() != player.getLocation().getWorld().getEnvironment()) {
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
