package sr.dac.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import sr.dac.main.Main;
import sr.dac.main.Menu;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        if(e.getClickedInventory()==null) return;

        InventoryHolder holder = e.getClickedInventory().getHolder();

        if(holder instanceof Menu){
            e.setCancelled(true);

            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getType().equals(Material.AIR)) return;
            if(e.getCurrentItem().getType().equals(Material.getMaterial(Main.getPlugin().getConfig().getString("emptySlotsMenu").toUpperCase()))) return;

            Menu menu = (Menu) holder;
            menu.interactMenu(e);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {

        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Menu){
            Menu menu = (Menu) holder;
            menu.closeMenu(e);
        }
    }
}
