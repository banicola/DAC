package sr.dac.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.NoSuchElementException;
import java.util.Set;

public class Signs implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        if(e.getLine(0).equalsIgnoreCase("[DAC]")){
            String a = e.getLine(1);
            if(a.length()>0){
                Arena arena = ArenaManager.getArena(a);
                if(arena!=null){
                    if (e.getPlayer().hasPermission("dac.sign")) {
                        e.setCancelled(true);
                        arena.addSign(e.getBlock().getLocation());
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.signCreated")));
                    } else {
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
                    }
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                }
            } else {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.signMissingArena")));
            }
        }
    }

    @EventHandler
    public void onSignRemove(BlockBreakEvent e){
        if(e.getBlock().getState() instanceof Sign){
            for(String a : ArenaManager.getArenas()){
                Arena arena = ArenaManager.getArena(a);
                if(arena.getSigns().size()>0 && arena.getSigns().contains(e.getBlock().getLocation())){
                    if(e.getPlayer().hasPermission("dac.sign")){
                        arena.removeSign(e.getBlock().getLocation());
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.signRemoved")));
                    } else {
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e){
        if(e.getClickedBlock()==null) return;
        if(e.getClickedBlock().getState() instanceof Sign){
            Set<String> arenas = ArenaManager.getArenas();
            for(String a : arenas){
                Arena arena = ArenaManager.getArena(a);
                if(arena!=null){
                    if(arena.getSigns().contains(e.getClickedBlock().getLocation())){
                        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                            e.getPlayer().performCommand("dac join "+arena.getName());
                        } else if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getPlayer().isSneaking() && e.getPlayer().hasPermission("dac.sign")){
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
