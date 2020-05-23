package sr.dac.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sr.dac.events.StartGame;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.List;

public class Countdown implements Runnable {

    int i;
    Arena a;
    List<Player> players;

    public Countdown(int start, Arena a){
        this.i=start;
        this.a=a;
        this.players= a.getPlayers();
    }

    @Override
    public void run() {
        if(i== Main.getPlugin().getConfig().getInt("countdownBeforeStart")){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i==Main.getPlugin().getConfig().getInt("countdownBeforeStart")/2){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i==10){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i==5){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i<=3&&i>0){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i==0){
            players.forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.hasStarted")));
            });
            StartGame.inGame(a);
            Bukkit.getScheduler().cancelTask(a.getCountdown());
        }
        i--;
    }
}