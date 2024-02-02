package net.xaervyn.EchoTombs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EchoTombItem {
    // Courtesy of lynxplay (https://www.spigotmc.org/threads/a-guide-to-1-14-persistentdataholder-api.371200/)
    public static Recipe getCraftingRecipe() {
        NamespacedKey keyRecipe = new NamespacedKey(Main.getEchoTombsMain(), "EchoTombRecipe");
        ShapedRecipe echoTombRecipe = new ShapedRecipe(keyRecipe, getEchoTombItem());
        echoTombRecipe.shape(",,,",",*,",",/,");
        echoTombRecipe.setIngredient(',',Material.CRYING_OBSIDIAN);
        echoTombRecipe.setIngredient('*',Material.ENDER_EYE);
        echoTombRecipe.setIngredient('/',Material.ECHO_SHARD);
        return echoTombRecipe;
    }

    public static ItemStack getEchoTombItem() {
        ItemStack echoTombItem = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = echoTombItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "EchoTomb Scroll");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        NamespacedKey keyMarking = new NamespacedKey(Main.getEchoTombsMain(), "EchoTomb");
        itemMeta.getPersistentDataContainer().set(keyMarking, PersistentDataType.BOOLEAN, true);
        NamespacedKey keyPreservation = new NamespacedKey(Main.getEchoTombsMain(), "Persistence");
        itemMeta.getPersistentDataContainer().set(keyPreservation, PersistentDataType.INTEGER, 1);
        NamespacedKey keyDuration = new NamespacedKey(Main.getEchoTombsMain(), "Duration");
        itemMeta.getPersistentDataContainer().set(keyDuration, PersistentDataType.INTEGER, 7);
        echoTombItem.setItemMeta(itemMeta);
        echoTombItem.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 0);
        return echoTombItem;
    }

    public static boolean isTombItem(ItemStack itemstack) {
        if (itemstack.getType().equals(Material.PAPER) &&
                itemstack.getItemMeta() != null && itemstack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "EchoTomb Scroll")) {
            NamespacedKey keyMarking = new NamespacedKey(Main.getEchoTombsMain(), "EchoTomb");
            return itemstack.getItemMeta().getPersistentDataContainer().has(keyMarking);
        }
        return false;
    }

    public static int getPersistenceValue(ItemStack itemStack) {
        NamespacedKey keyPreservation = new NamespacedKey(Main.getEchoTombsMain(), "Persistence");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(keyPreservation, PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(keyPreservation, PersistentDataType.INTEGER);
            }
        }
        return 0;
    }

    public static void setDurationValue(ItemStack itemStack, int value) {
        NamespacedKey keyDuration = new NamespacedKey(Main.getEchoTombsMain(), "Duration");
        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                itemMeta.getPersistentDataContainer().set(keyDuration, PersistentDataType.INTEGER, value);
            }
        }
    }

    public static int getDurationValue(ItemStack itemStack) {
        NamespacedKey keyDuration = new NamespacedKey(Main.getEchoTombsMain(), "Duration");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(keyDuration, PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(keyDuration, PersistentDataType.INTEGER);
            }
        }
        return 0;
    }
}
