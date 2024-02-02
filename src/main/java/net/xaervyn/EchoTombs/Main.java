package net.xaervyn.EchoTombs;

import net.xaervyn.EchoTombs.Listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static net.xaervyn.EchoTombs.Main instance;
    private Gravedigger gravedigger;

    @Override
    public void onEnable() {
        instance = this;

        // Register Crafting Recipe for EchoTomb Item
        getServer().addRecipe(EchoTombItem.getCraftingRecipe());

        // Start Gravedigger
        gravedigger = new Gravedigger();

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new InteractTombListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new CraftTombListener(), this);
        getServer().getPluginManager().registerEvents(new EchoTombInventoryListener(), this);

        // Start and Register Tomb Expiration Task
        getServer().getScheduler().runTaskTimer(this, gravedigger.getTombCleanupRunnable(), 1200, 200);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        instance = null;
    }

    public static net.xaervyn.EchoTombs.Main getEchoTombsMain() {
        return instance;
    }

    public static Gravedigger getGravedigger() {
        return instance.gravedigger;
    }
}
