package sr.dac.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import sr.dac.commands.CmdDAC;

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

        CommandExecutor dacExecutor = new CmdDAC();
        getCommand("dac").setExecutor(dacExecutor);

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
