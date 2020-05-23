package sr.dac.main;

import javafx.util.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    private String name;

    private String status;

    private boolean open;

    private Location divingLocation;
    private Location lobbyLocation;
    private Pair<Location, Location> poolLocation;

    private int min_player;
    private int max_player;

    private List<Player> players = new ArrayList<Player>();

    private int countdown = 0;

    public Arena(String n, Location divingLocation, Location lobbyLocation, Pair<Location, Location> poolLocation, int min_player, int max_player, boolean open, String status) {
        this.name = n;
        this.divingLocation = divingLocation;
        this.lobbyLocation = lobbyLocation;
        this.poolLocation = poolLocation;
        this.min_player = min_player;
        this.max_player = max_player;
        this.open = open;
        this.status = status;
    }

    public String getName() {
        return name;
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
        try {
            ArenaManager.save(name, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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
