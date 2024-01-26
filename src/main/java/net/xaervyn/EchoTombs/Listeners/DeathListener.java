package net.xaervyn.EchoTombs.Listeners;

import net.xaervyn.EchoTombs.EchoTombItem;
import net.xaervyn.EchoTombs.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();

        boolean hasEchoTombItem = false;
        ItemStack[] inventoryContents = player.getInventory().getContents();
        for (int i=0; i<inventoryContents.length; i++) {
            if (inventoryContents[i] != null && EchoTombItem.isTombItem(inventoryContents[i])) {
                player.getInventory().clear(i); // TODO Test to make sure it removes the correct items
                hasEchoTombItem = true;
                break;
            }
        }

        if (hasEchoTombItem) {
            Main.getGravedigger().createTomb(player, location);
            event.getDrops().clear();
            event.setDroppedExp(0);
            event.setDeathMessage(event.getDeathMessage() + " An EchoTomb remains...");
            event.setNewLevel(0);
        }
    }
}
