package net.xaervyn.EchoTombs.Listeners;


import net.xaervyn.EchoTombs.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.getPlayer().discoverRecipe(new NamespacedKey(Main.getEchoTombsMain(), "EchoTombRecipe"));
    }
}
