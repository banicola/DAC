package sr.dac.configs;

import javafx.util.Pair;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArenaManager {
    private static Map<String, Arena> arenas = new HashMap<String, Arena>();

    public static boolean createArena(String name) {
        if(arenas.containsKey(name)) throw new KeyAlreadyExistsException();
        arenas.put(name, new Arena(name, null, null, new Pair<>(null, null), 0, 0, false, null));
        try {
            save(name, arenas.get(name));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeArena(String name) {
        if(!arenas.containsKey(name)) throw new NoSuchElementException();
        arenas.remove(name);
        File arenas = new File(Main.getPlugin().getDataFolder(),"arenas.yml");
        YamlConfiguration arenasConfig = YamlConfiguration.loadConfiguration(arenas);
        arenasConfig.set("arenas."+name,null);
        try {
            arenasConfig.save(arenas);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public static void renameArena(String name, Arena a){
        arenas.put(a.getName(), a);
        removeArena(name);
    }

    public static Arena getArena(String name) {
        return arenas.get(name);
    }

    public static Set<String> getArenas() {
        return arenas.keySet();
    }

    public static boolean exists(String name) {
        return arenas.get(name) != null;
    }

    public static void load() {
        arenas.clear();
        YamlConfiguration arenasConfig = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(),"arenas.yml"));
        for(String arena : arenasConfig.getConfigurationSection("arenas").getKeys(false)){
            arenas.put(arena, new Arena(arena, arenasConfig.getLocation("arenas."+arena+".divingLocation"), arenasConfig.getLocation("arenas."+arena+".lobbyLocation"),
                    new Pair<Location, Location>(arenasConfig.getLocation("arenas."+arena+".poolLocationA"), arenasConfig.getLocation("arenas."+arena+".poolLocationB")),
                    arenasConfig.getInt("arenas."+arena+".min_player"),arenasConfig.getInt("arenas."+arena+".max_player"),arenasConfig.getBoolean("arenas."+arena+".open"),arenasConfig.getString("arenas."+arena+".status")));
        }
    }

    public static void save(String name, Arena a) throws IOException {
        File arenas = new File(Main.getPlugin().getDataFolder(),"arenas.yml");
        YamlConfiguration arenasConfig = YamlConfiguration.loadConfiguration(arenas);
        arenasConfig.set("arenas."+name+".divingLocation",a.getDivingLocation());
        arenasConfig.set("arenas."+name+".lobbyLocation",a.getLobbyLocation());
        arenasConfig.set("arenas."+name+".poolLocationA",a.getPoolLocation().getKey());
        arenasConfig.set("arenas."+name+".poolLocationB",a.getPoolLocation().getValue());
        arenasConfig.set("arenas."+name+".min_player",a.getMin_player());
        arenasConfig.set("arenas."+name+".max_player",a.getMax_player());
        arenasConfig.set("arenas."+name+".open",a.isOpen());
        arenasConfig.set("arenas."+name+".status",a.getStatus());
        arenasConfig.save(arenas);
    }
}
