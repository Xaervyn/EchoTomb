package net.xaervyn.EchoTombs.Listeners;

import net.xaervyn.EchoTombs.EchoTomb;
import net.xaervyn.EchoTombs.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EchoTombInventoryListener implements Listener {

    @EventHandler
    public void onBottleClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.EXPERIENCE_BOTTLE)) {
            if (event.getSlot() == 44) {
                EchoTomb tomb = Main.getGravedigger().getTombByInventory(event.getClickedInventory());
                if (tomb != null) {
                    if (event.getWhoClicked() instanceof Player player) {
                        player.giveExp(tomb.getEnchantmentExperience());
                        tomb.setExpLevels(0);
                        event.getClickedInventory().clear(44);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChestEmptied(InventoryClickEvent event) {
        if (event.getInventory().isEmpty()) {
            EchoTomb tomb = Main.getGravedigger().getTombByInventory(event.getInventory());
            if (tomb != null) {
                Runnable destroyTombRunnable = () -> Main.getGravedigger().destroyTomb(tomb);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getEchoTombsMain(), destroyTombRunnable, 5);
            }
        }
    }
}
