package plugin.manhunt.manhunt_plugin.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugin.manhunt.manhunt_plugin.ManhuntPlugin;
import plugin.manhunt.manhunt_plugin.game.ManhuntGame;

public class JoinGui implements Listener {
    static Inventory joingui;

    public Player target;

    public void openNewGui(Player player) {
        joingui = Bukkit.createInventory(null, 54, Component.text("Select a Game to Join"));
        int i = 0;
        for (ManhuntGame game : ManhuntPlugin.gameData.currentGames) {
            joingui.setItem(i, TargetGui.playerToHeadMap.get(game.target));

            i++;
        }

        player.openInventory(joingui);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent event) {
        if (!event.getInventory().equals(joingui)) {
            return;
        }
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack targethead = null;

        for (ItemStack head : TargetGui.headToPlayermap.keySet()) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta().equals(head.getItemMeta())) {
                    targethead = head;
                    break;
                }
            }
        }
        if (targethead == null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(ManhuntPlugin.getInstance(), player::closeInventory);
        }

        target = TargetGui.headToPlayermap.get(targethead);
        for (ManhuntGame game : ManhuntPlugin.gameData.currentGames) {
            if (game.target == target && !game.players.contains(player)) {
                game.onHunterJoin(player);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(ManhuntPlugin.getInstance(), player::closeInventory);
    }
}
