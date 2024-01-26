package net.xaervyn.EchoTombs.Listeners;

import net.xaervyn.EchoTombs.EchoTombItem;
import net.xaervyn.EchoTombs.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class CraftTombListener implements Listener {
    @EventHandler
    public void onCraftEchoTomb(CraftItemEvent event) {
        ItemStack resultItem = event.getCurrentItem();
        if (EchoTombItem.isTombItem(resultItem)) {
            // Cancel if shift clicking
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }

            ItemMeta itemMeta = resultItem.getItemMeta();
            NamespacedKey key = new NamespacedKey(Main.getEchoTombsMain(), "EchoTombSeperator");
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, new Random().nextInt());
            resultItem.setItemMeta(itemMeta);
            event.setCurrentItem(resultItem);
        }
    }
}
