package sr.dac.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;

import java.io.IOException;
import java.util.*;

public class Arena {

    private String name;

    private String status;

    private boolean open;

    private Location divingLocation;
    private Location lobbyLocation;
    private AbstractMap.SimpleEntry<Location, Location> poolLocation;
    private int poolBottom;

    private int min_player;
    private int max_player;

    private List<Location> signs;

    private List<Player> players = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();

    private int diver=0;

    private int countdown = 0;

    private HashMap<UUID, Material> playerMaterial = new HashMap<>();
    private HashMap<Player, Integer> playersLives = new HashMap<>();

    public Arena(String n, Location divingLocation, Location lobbyLocation, AbstractMap.SimpleEntry<Location, Location> poolLocation, int poolBottom, int min_player, int max_player, String status, List<Location> signs) {
        this.name = n;
        this.divingLocation = divingLocation;
        this.lobbyLocation = lobbyLocation;
        this.poolLocation = poolLocation;
        this.poolBottom = poolBottom;
        this.min_player = min_player;
        this.max_player = max_player;
        this.open = divingLocation != null && lobbyLocation != null && poolLocation.getKey() != null && poolLocation.getValue() != null && min_player > 0 && min_player <= max_player;
        this.status = status;
        this.signs = signs;
        for(Location sign : signs){
            updateSign(sign);
        }
    }

    public void join(Player p) {
        players.add(p);
        playersLives.put(p, 0);
    }

    public void leave(Player p) {
        playerMaterial.remove(p.getUniqueId());
        players.remove(p);
    }

    public void addSign(Location signLocation){
        signs.add(signLocation);
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        updateSign(signLocation);
    }

    public void updateSign(Location signLocation){
        Sign sign = (Sign) signLocation.getBlock().getState();
        if(!sign.isPlaced()) return;
        for (int i = 0; i < 4; i++) {
            sign.setLine(i, "");
        }
        sign.setLine(0, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.title").replace("%arena%", name)));
        if(!isOpen()){
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.statusClosed")));
        } else {
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.playersCount").replace("%count%", ""+getPlayers().size()).replace("%max%", ""+getMax_player())));
            if(getStatus().equalsIgnoreCase("waiting")){
                if(getPlayers().size()<getMin_player()){
                    int needed = getMin_player()-getPlayers().size();
                    sign.setLine(2, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.needXPlayers").replace("%count%", ""+needed)));
                } else {
                    sign.setLine(2, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.readyToStart")));
                }
            }
            if(getStatus().equalsIgnoreCase("waiting")){
                sign.setLine(3, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.statusWaiting")));
            } else {
                sign.setLine(3, ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.statusPlaying")));
            }
        }
        sign.update();
    }

    public void removeSign(Location signLocation){
        if(signs.contains(signLocation)){
            signs.remove(signLocation);
            try {
                ArenaManager.save(name, this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public List<Location> getSigns(){
        return signs;
    }

    public Integer getPlayerLives(Player p){
        return playersLives.get(p);
    }

    public void resetPlayersBlocks(){
        playerMaterial.clear();
    }

    public void setPlayerLives(Player p, int change){
        if(playersLives.containsKey(p)){
            playersLives.put(p,getPlayerLives(p)+change);
            if(getPlayerLives(p)<0){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerDied")));
            } else {
                if(change<0) p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerLoseLife").replace("%lives%", ""+getPlayerLives(p))));
                else if(change>0) p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWinLife").replace("%lives%", ""+getPlayerLives(p))));
            }
        } else {
            playersLives.put(p, 0);
        }
    }

    public void setPlayerMaterial(Player p, Material m){
        playerMaterial.put(p.getUniqueId(), m);
    }

    public Material getPlayerMaterial(Player p){
        return playerMaterial.get(p.getUniqueId());
    }

    public List<Player> getSpectators(){
        return spectators;
    }

    public void setSpectators(Player player){
        spectators.add(player);
    }

    public void leaveSpectator(Player player){
        spectators.remove(player);
    }

    public void resetArena(){
        int topBlockX = (Math.max(getPoolLocation().getKey().getBlockX(), getPoolLocation().getValue().getBlockX()));
        int bottomBlockX = (Math.min(getPoolLocation().getKey().getBlockX(), getPoolLocation().getValue().getBlockX()));

        int topBlockZ = (Math.max(getPoolLocation().getKey().getBlockZ(), getPoolLocation().getValue().getBlockZ()));
        int bottomBlockZ = (Math.min(getPoolLocation().getKey().getBlockZ(), getPoolLocation().getValue().getBlockZ()));

        for(int x = bottomBlockX+1; x <= topBlockX-1; x++)
        {
            for(int z = bottomBlockZ+1; z <= topBlockZ-1; z++)
            {
                int y=getPoolLocation().getKey().getBlockY()-1;
                Block block = getPoolLocation().getKey().getWorld().getBlockAt(x, y, z);
                while(block.getLocation().getBlockY()>poolBottom){
                    block.setType(Material.WATER);
                    block = getPoolLocation().getKey().getWorld().getBlockAt(x, y--, z);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setDiver(int diver){
        this.diver = diver;
    }

    public int getDiver(){
        return diver;
    }

    public int nextDiver(int diver){
        if(diver>=players.size()){
            return nextDiver(0);
        }
        if(playersLives.get(players.get(diver))>=0){
            return diver;
        } else {
            return nextDiver(diver+1);
        }
    }

    public void setName(String name){
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
            List<Player> players = getPlayers();
            List<Player> listPlayers = new ArrayList<>(players);
            for(Player p : listPlayers){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.kickPlayer").replace("%reason%", Main.f.getString("kickReason.arenaClosed"))));
                ArenaManager.playerLeaveArena(p);
            }
        } else {
            this.open = true;
        }
        if(status==null) this.status = "waiting";
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        for(Location sign : getSigns()){
            updateSign(sign);
        }
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

    public AbstractMap.SimpleEntry<Location, Location> getPoolLocation() {
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

    public void setPoolLocation(AbstractMap.SimpleEntry<Location,Location> poolLocation) {
        this.poolLocation = poolLocation;
        if(poolLocation.getKey()!=null&&poolLocation.getValue()!=null){
            int topBlockX = (Math.max(getPoolLocation().getKey().getBlockX(), getPoolLocation().getValue().getBlockX()));
            int bottomBlockX = (Math.min(getPoolLocation().getKey().getBlockX(), getPoolLocation().getValue().getBlockX()));

            int topBlockZ = (Math.max(getPoolLocation().getKey().getBlockZ(), getPoolLocation().getValue().getBlockZ()));
            int bottomBlockZ = (Math.min(getPoolLocation().getKey().getBlockZ(), getPoolLocation().getValue().getBlockZ()));

            boolean hasWater = false;

            Block block = null;

            for(int x = bottomBlockX+1; x <= topBlockX-1; x++)
            {
                for(int z = bottomBlockZ+1; z <= topBlockZ-1; z++)
                {
                    int y=getPoolLocation().getKey().getBlockY()-1;
                    block = getPoolLocation().getKey().getWorld().getBlockAt(x, y, z);
                    if(block.getType().equals(Material.WATER)){
                        hasWater=true;
                        break;
                    }
                }
                if(hasWater) break;
            }
            if(block!=null && hasWater){
                while(block.getType().equals(Material.WATER)){
                    block = getPoolLocation().getKey().getWorld().getBlockAt(block.getLocation().set(block.getX(), block.getY()-1, block.getZ()));
                }
                poolBottom = block.getY();
            }
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
