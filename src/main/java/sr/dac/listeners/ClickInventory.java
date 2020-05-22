package sr.dac.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import sr.dac.main.Main;

public class ClickInventory implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiTitle")))) {
            e.setCancelled(true);
        }
    }
}
