package sr.dac.listeners;

import javafx.util.Pair;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import sr.dac.configs.ArenaManager;
import sr.dac.configs.EditArena;
import sr.dac.main.Arena;
import sr.dac.main.Main;

public class BlockPlace implements Listener {
    @EventHandler
    public static void getBlockPlaceEvent(BlockPlaceEvent e) {
        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")))) {
            Arena a = ArenaManager.getArena(e.getItemInHand().getItemMeta().getLore().get(0));
            if (a != null) {
                ItemStack i = e.getItemInHand();
                if (i.getAmount()==1) {
                    Pair<Location, Location> poolLocation = new Pair(e.getBlockPlaced().getLocation(), a.getPoolLocation().getValue());
                    a.setPoolLocation(poolLocation);
                    i.setAmount(2);
                    e.getPlayer().getInventory().setItem(0,i);
                } else {
                    Pair<Location, Location> poolLocation = new Pair(a.getPoolLocation().getKey(), e.getBlockPlaced().getLocation());
                    a.setPoolLocation(poolLocation);
                    EditArena.openEditionGUI((Player) e.getPlayer(), a.getName());
                }
            }
            e.setCancelled(true);
        }
    }
}
