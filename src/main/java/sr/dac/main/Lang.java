package sr.dac.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.lang.reflect.Array;

public class Lang {
    private static File en_US = new File(Main.getPlugin().getDataFolder(),"/langs/en_US.yml");
    private static File fr_FR = new File(Main.getPlugin().getDataFolder(),"/langs/fr_FR.yml");
    public static void checkLang(){
        createLangFiles();
        for(File f : new File(Main.getPlugin().getDataFolder(),"/langs").listFiles()){
            try {
                updateLangFiles(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(Config.selectedLang().exists()){
            Main.lang = Config.selectedLang();
        } else{
            Main.lang = en_US;
            System.out.println("[DAC] Lang file doesn't exists, en_US selected by default.");
        }
    }

    private static void createLangFiles(){
        if(!en_US.exists()){
            Main.getPlugin().saveResource("langs/en_US.yml", false);
            Bukkit.getLogger().info(en_US.getName()+" "+Main.f.getString("debug.createFile"));
        }
        if(!fr_FR.exists()){
            Main.getPlugin().saveResource( "langs/fr_FR.yml", false);
            System.out.println(fr_FR.getName()+" "+Main.f.getString("debug.createFile"));
        }
    }

    private static void updateLangFiles(File file) throws IOException {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(file);
        YamlConfiguration ressource = YamlConfiguration.loadConfiguration(getRessourceFile(file));
        boolean changeMade = false;
        for(String m : ressource.getKeys(true)){
            if(!f.contains(m)){
                f.set(m, ressource.getString(m));
                changeMade = true;
            }
        }
        if(changeMade){
            System.out.println("[DAC] "+file.getName()+" updated");
        }
        f.save(file);
    }

    private static File getRessourceFile(File file){
        File ressourceFile = new File(file.getName());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = Main.getPlugin().getResource("langs/"+file.getName());
            outputStream = new FileOutputStream(ressourceFile);
            int read = 0;
            byte[]bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } catch (IOException|NullPointerException e) {
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
}
