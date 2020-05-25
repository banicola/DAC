package sr.dac.configs;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import sr.dac.events.StartGame;
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
    private static Map<Player, String> playerInArena = new HashMap<Player, String>();

    public static boolean createArena(String name) {
        if(arenas.containsKey(name)) throw new KeyAlreadyExistsException();
        arenas.put(name, new Arena(name, null, null, new Pair<>(null, null), -1, 0, 0, false, null));
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

    public static String getPlayerArena(Player player){
        return playerInArena.get(player);
    }

    public static void playerJoinArena(Player player, String arena) throws Exception {
        if(!arenas.containsKey(arena)) throw new NoSuchElementException();
        if(playerInArena.containsKey(player)){
            if(playerInArena.get(player).equalsIgnoreCase(arena)) throw new KeyAlreadyExistsException("same");
            else throw new KeyAlreadyExistsException("other");
        }
        if(arenas.get(arena).getStatus()=="playing") throw new Exception("playing");
        if(arenas.get(arena).getPlayers().size()==arenas.get(arena).getMax_player()) throw new Exception("full");
        playerInArena.put(player, arena);
        Arena a = getArena(arena);
        a.join(player);
        player.teleport(a.getLobbyLocation());
        player.setGameMode(GameMode.ADVENTURE);
        if(a.getPlayers().size()>=a.getMin_player())StartGame.startGame(a);
    }

    public static void playerLeaveArena(Player player){
        if(playerInArena.containsKey(player)){
            Arena a = getArena(playerInArena.get(player));
            playerInArena.remove(player);
            a.leave(player);
            if(player.isOp()) player.setGameMode(GameMode.CREATIVE);
            else player.setGameMode(GameMode.SURVIVAL);
            if(a.getPlayers().size()<a.getMin_player()&& (a.getCountdown()!=0 || a.getStatus()!="playing")){
                Bukkit.getServer().getScheduler().cancelTask(a.getCountdown());
            }
            if(a.getStatus()=="playing" && a.getPlayers().size()==0){
                if(a.getCountdown()!=0){
                    Bukkit.getServer().getScheduler().cancelTask(a.getCountdown());
                }
                a.resetArena();
            }
        } else {
            throw new NoSuchElementException();
        }
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
                    arenasConfig.getInt("arenas."+arena+".poolBottom"), arenasConfig.getInt("arenas."+arena+".min_player"),arenasConfig.getInt("arenas."+arena+".max_player"),
                    arenasConfig.getBoolean("arenas."+arena+".open"),arenasConfig.getString("arenas."+arena+".status")));
        }
    }

    public static void save(String name, Arena a) throws IOException {
        File arenas = new File(Main.getPlugin().getDataFolder(),"arenas.yml");
        YamlConfiguration arenasConfig = YamlConfiguration.loadConfiguration(arenas);
        arenasConfig.set("arenas."+name+".divingLocation",a.getDivingLocation());
        arenasConfig.set("arenas."+name+".lobbyLocation",a.getLobbyLocation());
        arenasConfig.set("arenas."+name+".poolLocationA",a.getPoolLocation().getKey());
        arenasConfig.set("arenas."+name+".poolLocationB",a.getPoolLocation().getValue());
        arenasConfig.set("arenas."+name+".poolBottom", a.getPoolBottom());
        arenasConfig.set("arenas."+name+".min_player",a.getMin_player());
        arenasConfig.set("arenas."+name+".max_player",a.getMax_player());
        arenasConfig.set("arenas."+name+".open",a.isOpen());
        arenasConfig.set("arenas."+name+".status",a.getStatus());
        arenasConfig.save(arenas);
    }
}
