package sr.dac.main;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import sr.dac.commands.DACCommand;
import sr.dac.commands.DACTabCompletion;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    private static Economy econ = null;

    public static File lang;
    public static YamlConfiguration f;
    private static WorldEditPlugin we;

    @Override
    public void onEnable() {
        plugin = this;
        Config.init();
        f = YamlConfiguration.loadConfiguration(lang);

        we = setupWorldEdit();
        econ = setupEconomy();
        try{
            if(we.isEnabled()) getLogger().info(ChatColor.translateAlternateColorCodes('&',f.getString("debug.worldeditEnable")));
        } catch (NoClassDefFoundError e){}
        try{
            if(econ != null) getLogger().info(ChatColor.translateAlternateColorCodes('&',f.getString("debug.vaultEnable")));
        } catch (NoClassDefFoundError e){}

        getCommand("dac").setExecutor(new DACCommand());
        getCommand("dac").setTabCompleter(new DACTabCompletion());

        getLogger().info(ChatColor.translateAlternateColorCodes('&',f.getString("debug.onEnable")));
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.translateAlternateColorCodes('&',f.getString("debug.onDisable")));
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private Economy setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        return rsp.getProvider();
    }

    private static WorldEditPlugin setupWorldEdit() {
        return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static WorldEditPlugin getWorldEdit() {
        return we;
    }
}
