package sr.dac.events;

import org.bukkit.entity.Player;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;

public class EndGame {

    public static void gameIsDone(Arena a){
        Player winner = a.getPlayers().get(0);
        winner.sendMessage("You won !");
        ArenaManager.playerLeaveArena(winner);
        a.resetArena();
    }
}
