package sr.dac.main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import sr.dac.commands.DACCommand;
import sr.dac.commands.DACTabCompletion;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    public static File lang;
    public static YamlConfiguration f;

    @Override
    public void onEnable() {
        plugin = this;
        Config.init();
        f = YamlConfiguration.loadConfiguration(lang);

        getCommand("dac").setExecutor(new DACCommand());
        getCommand("dac").setTabCompleter(new DACTabCompletion());

        getLogger().info(f.getString("debug.onEnable"));
    }

    @Override
    public void onDisable() {
        getLogger().info(f.getString("debug.onDisable"));
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
