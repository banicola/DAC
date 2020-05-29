package sr.dac.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreboardDAC {

    public static void setScoreboardWaitingDAC(Player p, int timeLeft){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Arena arena = ArenaManager.getArena(ArenaManager.getPlayerArena(p));

        if(arena==null)return;

        Objective objective = scoreboard.registerNewObjective("dacWaiting", "dummy", ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.title").replace("%arena%", arena.getName())));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.count").replace("%count%",""+arena.getPlayers().size())));
        score.setScore(10);

        Score score1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.blockTitle")));
        score1.setScore(9);

        Score score2;

        if(arena.getPlayerMaterial(p)!=null){
            score2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.block").replace("%block%",arena.getPlayerMaterial(p).name())));
        } else {
            score2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.noBlock")));
        }
        score2.setScore(8);
        Score score3 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.statusTitle")));
        score3.setScore(7);
        Score score4 = null;
        if(arena.getStatus().equalsIgnoreCase("waiting")){
            if(arena.getPlayers().size()<arena.getMin_player()){
                int needed = arena.getMin_player()-arena.getPlayers().size();
                score4 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("sign.needXPlayers").replace("%count%", ""+needed)));
            } else {
                score4 = objective.getScore(ChatColor.translateAlternateColorCodes('&',Main.f.getString("sign.readyToStart")));
            }
        }
        assert score4 != null;
        score4.setScore(6);

        if(arena.getCountdown()!=0){
            Score time = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("startScoreboard.timeLeft").replace("%time%", ""+timeLeft)));
            time.setScore(5);
        }

        p.setScoreboard(scoreboard);
    }

    public static void setScoreboardPlayingDAC(Player p, int timeLeft){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Arena arena = ArenaManager.getArena(ArenaManager.getPlayerArena(p));

        if(arena==null)return;

        Objective objective = scoreboard.registerNewObjective("dacPlaying", "dummy", ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.title").replace("%arena%", arena.getName())));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score next = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.nextTitle")));
        Score nextPlayer = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.nextDiver").replace("%player%", arena.getPlayers().get(arena.nextDiver(arena.getDiver()+1)).getName())));
        next.setScore(10);
        nextPlayer.setScore(9);

        Score score1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.livesTitle")));
        score1.setScore(8);

        List<Player> playersList = arena.getPlayers();
        List<Player> players = new ArrayList<>(playersList);
        players.sort((p1, p2) -> arena.getPlayerLives(p2) - arena.getPlayerLives(p1));

        if(players.size()<=5){
            players.subList(0, players.size()-1);
        }else{
            players.subList(0,5);
        }
        int pos = 7;
        for(Player player : players){
            objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.players").replace("%player%", player.getName()).replace("%lives%", ""+arena.getPlayerLives(player)))).setScore(pos);
            pos--;
        }
        try{
            if(arena.getPlayers().get(arena.getDiver())==p){
                Score time = objective.getScore(ChatColor.translateAlternateColorCodes('&', Main.f.getString("playingScoreboard.timeLeft").replace("%time%", ""+timeLeft)));
                time.setScore(2);
            }
        } catch (IndexOutOfBoundsException ignored){}


        p.setScoreboard(scoreboard);
    }

    public static void removeScoreboardDAC(Player p){
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
