package plugin.manhunt.manhunt_plugin.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import plugin.manhunt.manhunt_plugin.ManhuntPlugin;
import plugin.manhunt.manhunt_plugin.game.ManhuntGame;

import java.util.HashMap;

public class TargetGui implements Listener {
    static Inventory targetgui;
    static HashMap<Player, ItemStack> playerToHeadMap = new HashMap<>();
    static HashMap<ItemStack, Player> headToPlayermap = new HashMap<>();

    public Player target;

    public void openNewGui(Player player) {
        targetgui = Bukkit.createInventory(null, 54, Component.text("Select a Target"));
        createHitList();
        int i = 0;
        for (ItemStack head : playerToHeadMap.values()) {
            targetgui.setItem(i, head);

            i++;
        }
        player.openInventory(targetgui);
    }

    private void createHitList() {
        playerToHeadMap.clear();
        headToPlayermap.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);
            playerhead.editMeta(SkullMeta.class, meta -> meta.setOwningPlayer(player));
            playerhead.editMeta(
                    itemMeta -> itemMeta.displayName(Component.text(player.getName()))
            );
            playerToHeadMap.put(player, playerhead);
            headToPlayermap.put(playerhead, player);
        }
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent event) {
        if (!event.getInventory().equals(targetgui)) {
            return;
        }
        event.setCancelled(true);


        Player player = (Player) event.getWhoClicked();
        ItemStack targethead = null;

        for (ItemStack head : headToPlayermap.keySet()) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta().equals(head.getItemMeta())) {
                    targethead = head;
                    break;
                }
            }
        }

        if (targethead == null) {
            Bukkit.getScheduler().runTask(ManhuntPlugin.getInstance(), () -> player.closeInventory());
            return;
        }

        target = headToPlayermap.get(targethead);
        Bukkit.getScheduler().scheduleSyncDelayedTask(ManhuntPlugin.getInstance(), player::closeInventory);

        new ManhuntGame(player, target);
    }
}
