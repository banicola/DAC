package sr.dac.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sr.dac.events.StartGame;
import sr.dac.main.Arena;
import sr.dac.main.Main;

public class CountdownDive implements Runnable {
    Arena a;
    Player diver;
    int timeToJump;
    public CountdownDive(Player p, Arena arena){
        a = arena;
        diver = p;
        timeToJump = Main.getPlugin().getConfig().getInt("timeForJump");
    }

    @Override
    public void run() {
        if(timeToJump== Main.getPlugin().getConfig().getInt("timeForJump")){
            diver.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.jumpCountdownStart").replace("%time%", ""+timeToJump)));
        } else if(timeToJump==5 || (timeToJump<=3&&timeToJump>0)){
            diver.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.jumpCountdown").replace("%time%", ""+timeToJump)));
        } else if(timeToJump==0){
            diver.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.jumpCountdownEnd")));
            try {
                a.setPlayerLives(diver, -1);
                diver.teleport(a.getLobbyLocation());
            } catch (NullPointerException e){}
            StartGame.nextDiver(a);
        }
        timeToJump--;
    }
}
