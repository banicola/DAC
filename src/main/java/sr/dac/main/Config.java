package sr.dac.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import sr.dac.utils.Version;

import java.io.File;

public class Config {
    public static FileConfiguration config = Main.getPlugin().getConfig();
    public static final PluginDescriptionFile desc = Main.getPlugin().getDescription();

    public static void save() {
        Main.getPlugin().saveResource("config.yml", true);
    }

    public static void init() {
        if (!new File(Main.getPlugin().getDataFolder(), "arenas.yml").exists()) {
            Main.getPlugin().saveResource("arenas.yml", false);
            Main.getPlugin().getLogger().info("Arenas file created");
        }
        if (!new File(Main.getPlugin().getDataFolder(), "config.yml").exists()) {
            Main.getPlugin().saveResource("config.yml", false);
            Main.getPlugin().getLogger().info("Config file created");
        }
        Version current_file = new Version(config.getString("version"));
        Version new_version = new Version(desc.getVersion());
        if (new_version.compareTo(current_file) > 0) {
            Main.getPlugin().getLogger().info("Plugin updated to " + desc.getVersion());
            config.set("version", desc.getVersion());
            save();
        }
        Lang.checkLang();
    }

    public static void reloadConfig() {
        Main.getPlugin().reloadConfig();
        config = Main.getPlugin().getConfig();
    }

    public static File selectedLang() {
        return new File(Main.getPlugin().getDataFolder(), "/langs/" + Main.getPlugin().getConfig().getString("lang") + ".yml");
    }
}
