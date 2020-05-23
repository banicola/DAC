package sr.dac.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import sr.dac.main.Main;

public class DropItem implements Listener {

    @EventHandler
    public static void onDropItemEvent(PlayerDropItemEvent e){
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")))){
            e.setCancelled(true);
        }
    }
}
