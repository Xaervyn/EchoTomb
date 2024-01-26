package net.xaervyn.EchoTombs.Listeners;

import net.xaervyn.EchoTombs.EchoTomb;
import net.xaervyn.EchoTombs.Main;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InteractTombListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTombInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            ItemStack helmet = armorStand.getEquipment().getHelmet();
            if (helmet != null && helmet.getType().equals(Material.ENDER_CHEST) &&
                    armorStand.getCustomName().equals("Echo Tomb")) {
                EchoTomb echoTomb = Main.getGravedigger().getTombByEntityId(armorStand.getUniqueId());
                if (echoTomb != null) {
                    event.getPlayer().openInventory(echoTomb.getInventory());
                }
                event.setCancelled(true);
            }
        }
    }
}
