package sr.dac.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import sr.dac.configs.ArenaManager;

public class OnFallDamage implements Listener {
    @EventHandler
    public void onFallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player p = (Player) event.getEntity();

            if(ArenaManager.getPlayerArena(p)!=null && event.getCause() == EntityDamageEvent.DamageCause.FALL){
                event.setCancelled(true);
            }
            if(ArenaManager.getPlayerArena(p)!=null && p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL){
                event.setCancelled(true);
            }
        }
    }
}
