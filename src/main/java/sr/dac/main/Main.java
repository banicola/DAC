package sr.dac.main;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    public static File lang;

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled");
        plugin = this;
        Config.init();
        getLogger().info("Lang selected "+lang.getName());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disable");
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
