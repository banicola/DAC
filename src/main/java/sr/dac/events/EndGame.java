package sr.dac.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EndGame {

    public static void gameIsDone(Arena a){
        a.setCountdown(0);
        a.setStatus("ending");
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
                List<Player> spectators = a.getSpectators();
                List<Player> listSpectators = new ArrayList<>(spectators);
                listSpectators.remove(winner);
                for(Player s : listSpectators){
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
                    ArenaManager.playerLeaveArena(s);
                }
                List<Player> players = a.getPlayers();
                List<Player> listPlayers = new ArrayList<>(players);
                listPlayers.remove(winner);
                for(Player p : listPlayers){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
                    ArenaManager.playerLeaveArena(p);
                }
                try{
                    ArenaManager.playerLeaveArena(winner);
                } catch(NoSuchElementException ignore){}
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
