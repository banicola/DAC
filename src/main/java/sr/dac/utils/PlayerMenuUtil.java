package sr.dac.utils;

import org.bukkit.entity.Player;

public class PlayerMenuUtil {

    private Player p;

    public PlayerMenuUtil(Player p){
        this.p = p;
    }

    public Player getP(){
        return p;
    }

    public void setP(Player p){
        this.p = p;
    }
}
