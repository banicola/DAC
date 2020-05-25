package sr.dac.main;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private String name;

    private String status;

    private boolean open;

    private Location divingLocation;
    private Location lobbyLocation;
    private Pair<Location, Location> poolLocation;
    private int poolBottom=-1;

    private int min_player;
    private int max_player;

    private List<Player> players = new ArrayList<Player>();
    private List<Player> spectators = new ArrayList<Player>();

    private int diver=-1;

    private int countdown = 0;

    private HashMap<UUID, Integer> playerLives = new HashMap<>();

    public Arena(String n, Location divingLocation, Location lobbyLocation, Pair<Location, Location> poolLocation, int poolBottom, int min_player, int max_player, boolean open, String status) {
        this.name = n;
        this.divingLocation = divingLocation;
        this.lobbyLocation = lobbyLocation;
        this.poolLocation = poolLocation;
        this.poolBottom = poolBottom;
        this.min_player = min_player;
        this.max_player = max_player;
        this.open = open;
        this.status = status;
    }

    public Integer getPlayerLives(Player p){
        return playerLives.get(p.getUniqueId());
    }

    public void resetArena(){
        int topBlockX = (getPoolLocation().getKey().getBlockX() < getPoolLocation().getValue().getBlockX() ? getPoolLocation().getValue().getBlockX()-1 : getPoolLocation().getKey().getBlockX()+1);
        int bottomBlockX = (getPoolLocation().getKey().getBlockX() > getPoolLocation().getValue().getBlockX() ? getPoolLocation().getValue().getBlockX()-1 : getPoolLocation().getKey().getBlockX()+1);

        int topBlockZ = (getPoolLocation().getKey().getBlockZ() < getPoolLocation().getValue().getBlockZ() ? getPoolLocation().getValue().getBlockZ()+1 : getPoolLocation().getKey().getBlockZ()-1);
        int bottomBlockZ = (getPoolLocation().getKey().getBlockZ() > getPoolLocation().getValue().getBlockZ() ? getPoolLocation().getValue().getBlockZ()+1 : getPoolLocation().getKey().getBlockZ()-1);

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                int y=getPoolLocation().getKey().getBlockY()-1;
                Block block = getPoolLocation().getKey().getWorld().getBlockAt(x, y, z);
                while(!block.equals(Material.WATER) && block.getLocation().getBlockY()>poolBottom){
                    block.setType(Material.WATER);
                    block = getPoolLocation().getKey().getWorld().getBlockAt(x, y--, z);
                }

            }
        }
    }

    public void setPlayerLives(Player p, int lives){
        if(playerLives.containsKey(p.getUniqueId())){
            playerLives.put(p.getUniqueId(),lives);
            if(lives<0){
                playerLives.remove(p.getUniqueId());
                players.subList(0,players.size()-1);
                spectators.add(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerDied")));
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerLoseLife").replace("%lives%", ""+lives)));
            }
        } else {
            playerLives.put(p.getUniqueId(), 0);
        }
    }

    public String getName() {
        return name;
    }

    public int getDiver(){
        return diver;
    }

    public void nextDiver(){
        if(diver<0){
            diver=0;
        } else {
            if(diver+1>=getPlayers().size()) diver = 0;
            else diver = diver+1;
        }
    }

    public void setName(String name){
        String oldName = this.name;
        this.name = name;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setStatus(String s){
        this.status = s;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String getStatus(){
        return status;
    }

    public void setCountdown(int c){this.countdown=c;}

    public int getCountdown(){return countdown;}

    public void setOpen(){
        if(open){
            this.open = false;
        } else {
            this.open = true;
        }
        if(status==null) this.status = "waiting";
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void join(Player p) {
        players.add(p);
    }

    public void leave(Player p) {
        players.remove(p);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isOpen(){
        return open;
    }

    public Location getDivingLocation() {
        return divingLocation;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public Pair<Location, Location> getPoolLocation() {
        return poolLocation;
    }

    public int getMin_player() {
        return min_player;
    }

    public int getMax_player() {
        return max_player;
    }

    public void setDivingLocation(Location divingLocation) {
        this.divingLocation = divingLocation;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setPoolLocation(Pair<Location,Location> poolLocation) {
        this.poolLocation = poolLocation;
        if(poolLocation.getKey()!=null&&poolLocation.getValue()!=null){
            int bottomBlockX = (getPoolLocation().getKey().getBlockX() > getPoolLocation().getValue().getBlockX() ? getPoolLocation().getValue().getBlockX()-1 : getPoolLocation().getKey().getBlockX()+1);
            int bottomBlockZ = (getPoolLocation().getKey().getBlockZ() > getPoolLocation().getValue().getBlockZ() ? getPoolLocation().getValue().getBlockZ()+1 : getPoolLocation().getKey().getBlockZ()-1);
            int y=getPoolLocation().getKey().getBlockY()-1;
            Block block = getPoolLocation().getKey().getWorld().getBlockAt(bottomBlockX, y, bottomBlockZ);
            while(block.equals(Material.WATER)){
                block = getPoolLocation().getKey().getWorld().getBlockAt(bottomBlockX, y--, bottomBlockZ);
            }
            poolBottom = block.getY();
        }
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public int getPoolBottom(){
        return poolBottom;
    }

    public void setMin_player(int min_player){
        this.min_player = min_player;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setMax_player(int max_player){
        this.max_player = max_player;
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
