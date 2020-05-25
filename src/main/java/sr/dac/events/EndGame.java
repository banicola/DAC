package sr.dac.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;

public class EndGame {

    public static void gameIsDone(Arena a){
        Player winner = a.getPlayers().get(0);

        winner.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
        for(Player s : a.getSpectators()){
            ArenaManager.playerLeaveArena(s);
            s.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.playerWon").replace("%player%", winner.getName()).replace("%lives%",""+a.getPlayerLives(winner))));
        }
        ArenaManager.playerLeaveArena(winner);
        a.setStatus("waiting");
    }
}
