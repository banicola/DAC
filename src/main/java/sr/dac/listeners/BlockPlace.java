package sr.dac.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.menus.ArenaEditionMenu;

import java.util.AbstractMap;

public class BlockPlace implements Listener {
    @EventHandler
    public static void getBlockPlaceEvent(BlockPlaceEvent e) {
        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")))) {
            Arena a = ArenaManager.getArena(e.getItemInHand().getItemMeta().getLore().get(0));
            if (a != null) {
                ItemStack i = e.getItemInHand();
                if (i.getAmount()==1) {
                    AbstractMap.SimpleEntry<Location, Location> poolLocation = new AbstractMap.SimpleEntry<>(e.getBlockPlaced().getLocation(), a.getPoolLocation().getValue());
                    a.setPoolLocation(poolLocation);
                    i.setAmount(2);
                    e.getPlayer().getInventory().setItem(0,i);
                    e.getPlayer().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.poolSelectionMade").replace("%num%", ""+1)
                            .replace("%x%", ""+e.getBlockPlaced().getLocation().getBlockX()).replace("%z%", ""+e.getBlockPlaced().getLocation().getBlockZ())));
                } else {
                    if(e.getBlockPlaced().getLocation().getBlockY()==a.getPoolLocation().getKey().getBlockY()){
                        AbstractMap.SimpleEntry<Location, Location> poolLocation = new AbstractMap.SimpleEntry<>(a.getPoolLocation().getKey(), e.getBlockPlaced().getLocation());
                        a.setPoolLocation(poolLocation);
                        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        e.getPlayer().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.poolSelectionMade").replace("%num%", ""+2)
                                .replace("%x%", ""+e.getBlockPlaced().getLocation().getBlockX()).replace("%z%", ""+e.getBlockPlaced().getLocation().getBlockZ())));
                        new ArenaEditionMenu(Main.getPlayerMenuUtil(e.getPlayer()), a).open();
                    } else {
                        e.getPlayer().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.poolSelectionYError")));
                    }
                }
            }
            e.setCancelled(true);
        }
    }
}
