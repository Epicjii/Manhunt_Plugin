package plugin.manhunt.manhunt_plugin.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class TargetGui implements Listener {
    Inventory targetgui;
    List<ItemStack> playerheads;

    public void openNewGui(Player player) {
        targetgui = Bukkit.createInventory(null, InventoryType.CHEST);

        for (int i = 0; i <= targetgui.getSize(); i++) {
            targetgui.setItem(i, playerheads.get(i));
        }
    }

    @EventHandler
    public void headRegistry(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) playerhead.getItemMeta();
        headMeta.setPlayerProfile(player.getPlayerProfile());
        playerhead.setItemMeta(headMeta);
        playerheads.add(playerhead);
    }
}
