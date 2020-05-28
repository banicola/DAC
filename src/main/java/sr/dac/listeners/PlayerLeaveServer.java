package sr.dac.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import sr.dac.configs.ArenaManager;

import java.util.NoSuchElementException;

public class PlayerLeaveServer  implements Listener {

    @EventHandler
    public static void onPlayerLeaveServer(PlayerQuitEvent e){
        try{
            ArenaManager.playerLeaveArena(e.getPlayer());
        } catch (NoSuchElementException ignored){}
    }
}
