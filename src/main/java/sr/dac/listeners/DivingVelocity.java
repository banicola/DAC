package sr.dac.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import sr.dac.configs.ArenaManager;
import sr.dac.events.StartGame;
import sr.dac.main.Arena;

public class DivingVelocity implements Listener {

    @EventHandler
    public void checkPlayerVelocity(PlayerMoveEvent e){
        String a = ArenaManager.getPlayerArena(e.getPlayer());
        if(a!=null){
            Arena arena = ArenaManager.getArena(a);
            Player p = e.getPlayer();
            if(arena.getStatus()=="playing" && arena.getPlayers().get(arena.getDiver())==p){
                if(p.getLocation().getBlockY()<=arena.getPoolLocation().getKey().getBlockY()){
                    Location lowerLocation;
                    if(p.getLocation().getBlockY()==arena.getPoolLocation().getKey().getBlockY()) lowerLocation = p.getLocation().add(0,-1,0);
                    else lowerLocation = p.getLocation();
                    Block blockUnder = Bukkit.getServer().getWorld(p.getWorld().getName()).getBlockAt(lowerLocation);
                    boolean isWater = blockUnder.getType().equals(Material.WATER);
                    if(isWater){
                        p.sendMessage("You survived");
                        while(blockUnder.getType().equals(Material.WATER)){
                            blockUnder.setType(Material.WHITE_WOOL);
                            lowerLocation.add(0,-1,0);
                            blockUnder = Bukkit.getServer().getWorld(p.getWorld().getName()).getBlockAt(lowerLocation);
                        }
                    } else {
                        p.sendMessage("oops");
                        //arena.setPlayerLives(p, arena.getPlayerLives(p)-1);
                    }

                    Bukkit.getServer().getScheduler().cancelTask(arena.getCountdown());
                    p.teleport(arena.getLobbyLocation());
                    StartGame.nextDiver(arena);
                }

            }
        }
    }
}
