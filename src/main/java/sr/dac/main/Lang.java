package sr.dac.main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lang {
    private static final String[] langsAvailable = {"en_US", "fr_FR"};
    private static final File en_US = new File(Main.getPlugin().getDataFolder(), "/langs/en_US.yml");
    private static final File fr_FR = new File(Main.getPlugin().getDataFolder(), "/langs/fr_FR.yml");

    public static void checkLang() {
        if (Config.selectedLang().exists()) {
            Main.lang = Config.selectedLang();
        } else {
            Main.lang = en_US;
            Main.getPlugin().getLogger().info("Lang file doesn't exists, en_US selected by default.");
        }
        Main.f = YamlConfiguration.loadConfiguration(Main.lang);
        createLangFiles();
        for (File f : new File(Main.getPlugin().getDataFolder(), "/langs").listFiles()) {
            try {
                updateLangFiles(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.lang = Config.selectedLang();
    }

    private static void createLangFiles() {
        List<File> allLang = new ArrayList<>();
        for (String l : langsAvailable) {
            allLang.add(getRessourceFile(l + ".yml"));
        }
        for (File file : allLang) {
            if (!new File(Main.getPlugin().getDataFolder(), "langs/" + file.getName()).exists()) {
                Main.getPlugin().saveResource("langs/" + file.getName(), false);
                if (!Main.f.contains("debug.createFile")) {
                    Main.getPlugin().getLogger().info(file.getName() + " has been created");
                } else {
                    Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&', Main.f.getString("debug.createFile").replace("%file%", file.getName())));
                }
            }
        }
    }

    private static void updateLangFiles(File file) throws IOException {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(file);
        YamlConfiguration ressource = YamlConfiguration.loadConfiguration(getRessourceFile(file.getName()));
        boolean changeMade = false;
        for (String m : ressource.getKeys(true)) {
            if (!f.contains(m)) {
                f.set(m, ressource.getString(m));
                changeMade = true;
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

    private static File getRessourceFile(String file) {
        File ressourceFile = new File(file);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = Main.getPlugin().getResource("langs/" + file);
            outputStream = new FileOutputStream(ressourceFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
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
        return ressourceFile;
    }

    public static void reloadConfig() {
        checkLang();
    }
}