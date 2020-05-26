package sr.dac.main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import sr.dac.configs.ArenaManager;
import sr.dac.utils.UpdateFiles;
import sr.dac.utils.Version;

import java.io.File;

public class Config {
    public static File configF = new File(Main.getPlugin().getDataFolder(), "config.yml");
    public static YamlConfiguration blocksConfig = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "blocks.yml"));
    public static final PluginDescriptionFile desc = Main.getPlugin().getDescription();

    public static void init() {
        if (!new File(Main.getPlugin().getDataFolder(), "arenas.yml").exists()) {
            Main.getPlugin().saveResource("arenas.yml", false);
            Main.getPlugin().getLogger().info("Arenas file created");
        }
        if (!new File(Main.getPlugin().getDataFolder(), "blocks.yml").exists()) {
            Main.getPlugin().saveResource("blocks.yml", false);
            Main.getPlugin().getLogger().info("Blocks file created");
        }
        if (!new File(Main.getPlugin().getDataFolder(), "config.yml").exists()) {
            Main.getPlugin().saveResource("config.yml", false);
            Main.getPlugin().getLogger().info("Config file created");
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configF);
        Version current_file = new Version(config.getString("version"));
        Version new_version = new Version(desc.getVersion());
        if (new_version.compareTo(current_file) > 0) {
            Main.getPlugin().getLogger().info("Plugin updated to " + desc.getVersion());
            UpdateFiles.updateConfig();
        }
        Lang.checkLang();
    }

    public static void reloadConfig() {
        Main.getPlugin().reloadConfig();
        for(String a : ArenaManager.getArenas()){
            Arena arena = ArenaManager.getArena(a);
            for(Player p: arena.getPlayers()){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.kickPlayer").replace("%reason%", "Plugin reload")));
            }
            for(Player spect : arena.getSpectators()){
                spect.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.kickPlayer").replace("%reason%", "Plugin reload")));
            }
        }
        ArenaManager.load();
        Lang.reloadConfig();
        configF = new File(Main.getPlugin().getDataFolder(), "config.yml");
        blocksConfig = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "blocks.yml"));
    }

    public static File selectedLang() {
        return new File(Main.getPlugin().getDataFolder(), "/langs/" + Main.getPlugin().getConfig().getString("lang") + ".yml");
    }
}
