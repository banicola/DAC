package sr.dac.main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import sr.dac.utils.UpdateFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lang {
    private static final List<String> langsAvailable = Arrays.asList("en_US.yml","fr_FR.yml");
    private static final File en_US = new File(Main.getPlugin().getDataFolder(), "/langs/en_US.yml");

    public static void checkLang() {
        createLangFiles();
        if (Config.selectedLang().exists()) {
            Main.lang = Config.selectedLang();
            if(!langsAvailable.contains(Config.selectedLang().getName())){
                Main.getPlugin().getLogger().warning(ChatColor.translateAlternateColorCodes('&',"&4The language you selected isn't officially supported. Unknown or missing sentences can create null elements."));
            }
        } else {
            if(en_US.exists()){
                Main.lang = en_US;
                Main.getPlugin().getLogger().info("Lang file doesn't exists, en_US selected by default.");
            }
        }
        Main.f = YamlConfiguration.loadConfiguration(Main.lang);
        for (File f : new File(Main.getPlugin().getDataFolder(), "/langs").listFiles()) {
            try {
                UpdateFiles.updateFile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.lang = Config.selectedLang();
    }

    private static void createLangFiles() {
        List<File> allLang = new ArrayList<>();
        for (String l : langsAvailable) {
            allLang.add(UpdateFiles.getResourceFile(l));
        }
        for (File file : allLang) {
            if (!new File(Main.getPlugin().getDataFolder(), "langs/" + file.getName()).exists()) {
                Main.getPlugin().saveResource("langs/" + file.getName(), false);
                try{
                    if (!Main.f.contains("global.createFile")) {
                        Main.getPlugin().getLogger().info(file.getName() + " has been created");
                    } else {
                        Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&', Main.f.getString("global.createFile").replace("%file%", file.getName())));
                    }
                } catch (NullPointerException e){
                    Main.getPlugin().getLogger().info(file.getName() + " has been created");
                }
            }
        }
    }
}