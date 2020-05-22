package sr.dac.main;

import javafx.util.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private String name;

    private Location divingLocation;
    private Location lobbyLocation;
    private Pair<Location, Location> poolLocation;

    private int min_player;
    private int max_player;

    private List<UUID> players = new ArrayList<UUID>();

    public Arena(String n, Location divingLocation, Location lobbyLocation, Pair<Location, Location> poolLocation, int min_player, int max_player) {
        this.name = n;
        this.divingLocation = divingLocation;
        this.lobbyLocation = lobbyLocation;
        this.poolLocation = poolLocation;
        this.min_player = min_player;
        this.max_player = max_player;
    }

    public String getName() {
        return name;
    }

    public void join(Player p) {
        players.add(p.getUniqueId());
    }

    public void leave(Player p) {
        players.remove(p.getUniqueId());
    }

    public List<UUID> getPlayers() {
        return players;
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
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public void setMin_player(int min_player){
        this.min_player = min_player;
    }

    public void setMax_player(int max_player){
        this.max_player = max_player;
    }

}
