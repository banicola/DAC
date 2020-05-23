package sr.dac.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import sr.dac.configs.ArenaManager;

public class PlayerLeaveServer  implements Listener {
    @EventHandler
    public static void onPlayerLeaveServer(PlayerQuitEvent e){
        String arena = ArenaManager.getPlayerArena(e.getPlayer());
        if(arena!=null){
            ArenaManager.playerLeaveArena(e.getPlayer());
        }
    }
}
