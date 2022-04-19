package plugin.manhunt.manhunt_plugin.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class TargetGui implements Listener {
    static Inventory targetgui;
    static HashMap<Player, ItemStack> playerHeadMap = new HashMap<>();
    static HashMap<ItemStack, Player> headPlayermap = new HashMap<>();

    public void openNewGui(Player player) {
        targetgui = Bukkit.createInventory(null, 54, Component.text("Select a Target"));
        int i = 0;
        for (ItemStack head : playerHeadMap.values()) {
            targetgui.setItem(i, head);

            i++;
        }
        player.openInventory(targetgui);
    }

    @EventHandler
    public void headRegistry(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);
        playerhead.editMeta(SkullMeta.class, meta -> meta.setOwningPlayer(player));
        playerHeadMap.put(player, playerhead);
        headPlayermap.put(playerhead, player);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent event) {
        if (!event.getInventory().equals(targetgui)) {
            return;
        }
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack targethead = null;

        for (ItemStack head : playerHeadMap.values()) {
            if (event.getCurrentItem() != null) {
                if (!event.getCurrentItem().equals(head)) {
                    return;
                }
                targethead = head;
            }
        }
        Player target = headPlayermap.get(targethead);
        player.closeInventory();
    }
}