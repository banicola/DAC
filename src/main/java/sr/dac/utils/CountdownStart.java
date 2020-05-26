package sr.dac.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import sr.dac.events.StartGame;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.List;

public class CountdownStart implements Runnable {

    int i;
    Arena a;
    List<Player> players;

    public CountdownStart(int start, Arena a){
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
        } else if(i<=5&&i>0){
            players.forEach(player -> {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 1);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.countdown").replace("%time%", ""+i)));
            });
        } else if(i==0){
            a.resetLives();
            players.forEach(player -> {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 1);
                if(a.getPlayerMaterial(player)==null) StartGame.selectRandomBlock(player, a);
                a.setPlayerLives(player, 0);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.hasStarted")));
            });
            StartGame.inGame(a);
        }
        i--;
    }
}
