package net.xaervyn.EchoTombs;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.time.LocalDateTime;
import java.util.UUID;

public class EchoTomb {
    private final UUID pId;
    private final UUID tId;
    private final Location loc;
    private final Inventory tInv;
    private LocalDateTime timeCreate;
    private LocalDateTime timeDestroy;
    private int enchantXp;
    private UUID eId;

    protected EchoTomb(UUID playerId, UUID tombId, Location tombLocation, Inventory tombInventory) {
        pId = playerId;
        loc = tombLocation;
        tInv = tombInventory;
        tId = tombId;
    }

    public Location getLocation() {
        return loc.clone();
    }

    public UUID getPlayerId() {
        return pId;
    }

    public UUID getTombId() {
        return tId;
    }

    public Inventory getInventory() {
        return tInv;
    }

    protected void setEntityId(UUID entityId) {eId = entityId; }

    public UUID getEntityId() {
        return eId;
    }

    public void setExpLevels(int enchantmentExperience) {
        enchantXp = enchantmentExperience;
    }

    public int getEnchantmentExperience() {
        return enchantXp;
    }

    public void setTombExpirationTime(LocalDateTime expirationTimeTicks) { timeDestroy = expirationTimeTicks; }

    public LocalDateTime getTombExpirationTime() { return timeDestroy; }

    public void setTombCreationTime(LocalDateTime creationTimeTicks) { timeCreate = creationTimeTicks; }

    public LocalDateTime getTombCreationTime() { return timeCreate; }


}
