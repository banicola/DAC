package sr.dac.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import sr.dac.main.Main;

import java.io.*;

public class UpdateFiles {
    public static void updateFile(File file) throws IOException {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(file);
        YamlConfiguration resource = YamlConfiguration.loadConfiguration(getResourceFile(file.getName()));
        boolean changeMade = false;
        for (String m : resource.getKeys(true)) {
            if (!f.contains(m)) {
                f.set(m, resource.getString(m));
                changeMade = true;
            } else if (f.contains("version")){
                f.set(m, Main.getPlugin().getDescription().getVersion());
            }
        }
        if (changeMade) {
            if (!Main.f.contains("debug.updateFile")) {
                Main.getPlugin().getLogger().info(file.getName() + " has been updated");
            } else {
                Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&', Main.f.getString("debug.updateFile").replace("%file%", file.getName())));
            }
        }
        f.save(file);
    }

    public static File getResourceFile(String file) {
        File resourceFile = new File(file);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if(!file.equalsIgnoreCase("config.yml") && !file.equalsIgnoreCase("arenas.yml")){
                inputStream = Main.getPlugin().getResource("langs/" + file);
            } else {
                inputStream = Main.getPlugin().getResource(file);
            }
            outputStream = new FileOutputStream(resourceFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            try{
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (NullPointerException e){}
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resourceFile;
    }

    public static void updateConfig(){
        Configuration defaults = Main.getPlugin().getConfig().getDefaults();
        for (String defaultKey : defaults.getKeys(false)) {
            if (!Main.getPlugin().getConfig().getKeys(false).contains(defaultKey)) {
                Main.getPlugin().getConfig().set(defaultKey, defaults.get(defaultKey));
            } else if (defaultKey.equalsIgnoreCase("version")){
                Main.getPlugin().getConfig().set(defaultKey, defaults.get(defaultKey));
            }
        }
        Main.getPlugin().saveConfig();
    }
}
