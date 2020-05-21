package sr.dac.main;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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
        getLogger().info(f.getString("debug.onEnable"));
    }

    @Override
    public void onDisable() {
        getLogger().info(f.getString("debug.onDisable"));
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(command.getName().equalsIgnoreCase("dac") && args.length > 0 && args[0].contentEquals("reload")){
            if(sender.hasPermission("dac.reload")){
                Config.reloadConfig();
                Lang.reloadConfig();
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getPluginManager().enablePlugin(this);
                Main.getPlugin().getLogger().info("");
                Main.getPlugin().getLogger().info(Main.f.getString("debug.reloadConfig"));
                if(sender instanceof Player){
                    sender.sendMessage(Main.f.getString("name")+" "+Main.f.getString("debug.reloadConfig"));
                }
            }else{
                sender.sendMessage(Main.f.getString("debug.noPermission"));
            }
            return true;
        }
        return false;
    }
}
