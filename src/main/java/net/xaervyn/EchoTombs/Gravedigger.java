package net.xaervyn.EchoTombs;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.*;

public class Gravedigger {
    private final Set<EchoTomb> activeTombs;
    private final TombCleanup tombCln;

    protected Gravedigger() {
        activeTombs = new HashSet<>();
        tombCln = new TombCleanup();
    }

    public EchoTomb createTomb(Player player, Location location, int duration) {
        Inventory pInv = player.getInventory();

        Bukkit.getLogger().info("Creating EchoTomb for " + player.getName() + " at x:" + location.getBlockX() +
                ",y:" + location.getBlockY() + ",z:" + location.getBlockZ() + " in world " + location.getWorld() +
                " containing " + pInv.getContents().length + " items.");

        // Set tomb inventory contents
        Inventory tombInv = Bukkit.createInventory(null, 45, player.getName() + "'s EchoTomb");
        tombInv.setStorageContents(pInv.getContents());

        // Add enchanted bottle to inventory
        ItemStack enchantedBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta bottleMeta = enchantedBottle.getItemMeta();
        bottleMeta.setDisplayName(ChatColor.DARK_PURPLE + "Collect Experience");
        bottleMeta.setLore(Arrays.asList("Click to collect " + player.getTotalExperience() + " enchantment experience."));
        enchantedBottle.setItemMeta(bottleMeta);
        tombInv.setItem(44, enchantedBottle);

        // Create Tomb
        EchoTomb newTomb =  new EchoTomb(player.getUniqueId(), UUID.randomUUID(), location, tombInv);

        // Set Expiration and Creation Time for Tomb
        newTomb.setTombCreationTime(LocalDateTime.now());
        newTomb.setTombExpirationTime(LocalDateTime.now().plusSeconds((long) duration * 60));

        // Spawn Tomb into World
        Entity tombEntity = spawnTomb(newTomb);
        if (tombEntity != null) {
            newTomb.setEntityId(tombEntity.getUniqueId());
            activeTombs.add(newTomb);
            return newTomb;
        } else {
            return null;
            // TODO Throw an exception here or something...
        }
    }

    public void destroyTomb(EchoTomb echoTomb) {
        if (despawnTomb(echoTomb)) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(echoTomb.getPlayerId());

            Bukkit.getLogger().info("Destroying EchoTomb for " + p.getName() + " containing " + echoTomb.getInventory().getContents().length +
                    " items.");

            // Close inventory view for inventory viewers
            new ArrayList<>(echoTomb.getInventory().getViewers()).forEach(HumanEntity::closeInventory);

            // Clear inventory contents
            echoTomb.getInventory().clear();

            // Despawn entity for the EchoTomb
            despawnTomb(echoTomb);

            // TODO Drop EchoTomb items? Probably not...

            activeTombs.remove(echoTomb);
        } else {
            // TODO throw an exception here or something...
        }
    }

    private Entity spawnTomb(EchoTomb echoTomb) {
        ArmorStand tombEntity = (ArmorStand) echoTomb.getLocation().getWorld().spawnEntity(echoTomb.getLocation().subtract(0, 1.50, 0), EntityType.ARMOR_STAND);
        tombEntity.getEquipment().setHelmet(new ItemStack(Material.ENDER_CHEST));
        tombEntity.setCollidable(false);
        tombEntity.setVisible(false);
        tombEntity.setGravity(false);
        tombEntity.setInvulnerable(true);
        tombEntity.setCanPickupItems(false);
        tombEntity.setRemoveWhenFarAway(false);
        tombEntity.setPersistent(true);
        tombEntity.setCustomName("Echo Tomb");
        tombEntity.setCustomNameVisible(true);
        return tombEntity;
    }

    private boolean despawnTomb(EchoTomb echoTomb) {
        World world = echoTomb.getLocation().getWorld();
        Collection<Entity> nearbyEntities = world.getNearbyEntities(echoTomb.getLocation(), 0, 2, 0);
        for (Entity entity : nearbyEntities) {
            if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                if (entity.getName().equals("Echo Tomb")) {
                    entity.remove();
                }
            }
        }

        return true;
    }

    public EchoTomb getTomb(Location location) {
        for (EchoTomb tomb : activeTombs) {
            if (tomb.getLocation().equals(location)) {
                return tomb;
            }
        }
        return null;
    }

    public EchoTomb getTombByEntityId(UUID entityId) {
        for (EchoTomb tomb : activeTombs) {
            if (tomb.getEntityId() != null &&
                    tomb.getEntityId().equals(entityId)) {
                return tomb;
            }
        }
        return null;
    }

    public EchoTomb getTombByInventory(Inventory inventory) {
        for (EchoTomb tomb : activeTombs) {
            if (tomb.getInventory() != null &&
                    tomb.getInventory().equals(inventory)) {
                return tomb;
            }
        }
        return null;
    }

    protected TombCleanup getTombCleanupRunnable() { return tombCln; }

    private class FileManager {
        private boolean saveEchoTomb(UUID tombId) {
            return false;
        }

        private boolean loadEchoTomb(UUID tombId) {
            return false;
        }

        private boolean updateEchoTomb(UUID tombId) {
            return false;
        }

        private boolean deleteEchoTomb(UUID tombId) {
            return false;
        }

        private List<UUID> getEchoTombs() {
            return List.of();
        }

        private List<UUID> getEchoTombsForPlayer(UUID playerId) {
            return List.of();
        }
    }

    protected class TombCleanup implements Runnable {

        @Override
        public void run() {
            Iterator<EchoTomb> tombIterator = Gravedigger.this.activeTombs.iterator();
            while (tombIterator.hasNext()) {
                EchoTomb tomb = tombIterator.next();
                if (tomb.getTombExpirationTime().isAfter(LocalDateTime.now())) {
                    Gravedigger.this.destroyTomb(tomb);
                }
            }
        }
    }


}
