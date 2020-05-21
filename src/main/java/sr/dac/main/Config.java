package sr.dac.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import sr.dac.utils.Version;

import java.io.File;

public class Config {
    public static FileConfiguration config = Main.getPlugin().getConfig();
    public static PluginDescriptionFile desc = Main.getPlugin().getDescription();

    public static void save() {
        Main.getPlugin().saveConfig();
    }

    public static void init() {
        if(!new File(Main.getPlugin().getDataFolder(), "config.yml").exists()){
            Main.getPlugin().saveDefaultConfig();
            System.out.println("[DAC] Config file created");
        }
        Version current_file = new Version(config.getString("version"));
        Version new_version = new Version(desc.getVersion());
        if(new_version.compareTo(current_file)>0){
            System.out.println("[DAC] A new update is available");
            config.set("version", desc.getVersion());
            save();
        }
        Lang.checkLang();
    }

    public static void reloadConfig() {
        Main.getPlugin().reloadConfig();
        config = Main.getPlugin().getConfig();
    }

    public static File selectedLang(){
        return new File(Main.getPlugin().getDataFolder(),"/langs/"+Main.getPlugin().getConfig().getString("lang")+".yml");
    }
}
