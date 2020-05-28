package sr.dac.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.ArrayList;
import java.util.List;

public class EndGame {

    public static void gameIsDone(Arena a){
        a.setCountdown(0);
        try{
            Player winner = null;
            for(Player player : a.getPlayers()){
                if(a.getPlayerLives(player)>=0){
                    winner=player;
                    break;
                }
            }
            if(winner!=null){
                winner.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
                for(Player s : a.getSpectators()){
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
                    ArenaManager.playerLeaveArena(s);
                }
                List<Player> players = a.getPlayers();
                List<Player> listPlayers = new ArrayList<>();
                for(Player p : players){
                    listPlayers.add(p);
                }
                listPlayers.remove(listPlayers.indexOf(winner));
                for(Player p : listPlayers){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
                    ArenaManager.playerLeaveArena(p);
                }
                ArenaManager.playerLeaveArena(winner);
            }
        } catch (IndexOutOfBoundsException e){
            for(Player s : a.getSpectators()){
                ArenaManager.playerLeaveArena(s);
            }
        }
        a.resetArena();
        a.resetPlayersBlocks();
        a.setStatus("waiting");
        for(Location sign : a.getSigns()){
            a.updateSign(sign);
        }
    }
}
