package sr.dac.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import sr.dac.configs.ArenaManager;
import sr.dac.events.EndGame;
import sr.dac.events.StartGame;
import sr.dac.main.Arena;

public class DivingVelocity implements Listener {

    @EventHandler
    public void checkPlayerVelocity(PlayerMoveEvent e){
        String a = ArenaManager.getPlayerArena(e.getPlayer());
        if(a!=null){
            Arena arena = ArenaManager.getArena(a);
            Player p = e.getPlayer();
            if(arena.getPlayers().size()==0){
                EndGame.gameIsDone(arena);
                return;
            }
            if(arena.getStatus()=="playing" && arena.getPlayers().get(arena.getDiver())==p){
                if(p.getLocation().getBlockY()<=arena.getPoolLocation().getKey().getBlockY()){
                    Location lowerLocation;

                    if(p.getLocation().getBlockY()==arena.getPoolLocation().getKey().getBlockY()) lowerLocation = p.getLocation().add(0,-1,0);
                    else lowerLocation = p.getLocation();
                    Block blockUnder = Bukkit.getServer().getWorld(p.getWorld().getName()).getBlockAt(lowerLocation);
                    boolean isWater = blockUnder.getType().equals(Material.WATER);
                    Material playerBlock = Material.WHITE_WOOL;
                    if(isWater&&Math.abs(p.getVelocity().getY())>0.1){
                        Block north = blockUnder.getRelative(BlockFace.NORTH);
                        Block east = blockUnder.getRelative(BlockFace.EAST);
                        Block south = blockUnder.getRelative(BlockFace.SOUTH);
                        Block west = blockUnder.getRelative(BlockFace.WEST);
                        if(!north.getType().equals(Material.WATER)&&!east.getType().equals(Material.WATER)&&!south.getType().equals(Material.WATER)&&!west.getType().equals(Material.WATER)){
                            System.out.println("dac !!!!");
                            arena.setPlayerLives(p, 1);
                            playerBlock = Material.EMERALD_BLOCK;
                            Firework fw = (Firework) blockUnder.getWorld().spawnEntity(blockUnder.getLocation().add(0,1,0), EntityType.FIREWORK);
                        }
                        while(blockUnder.getType().equals(Material.WATER)){
                            blockUnder.setType(playerBlock);
                            lowerLocation.add(0,-1,0);
                            blockUnder = Bukkit.getServer().getWorld(p.getWorld().getName()).getBlockAt(lowerLocation);
                        }
                    } else {
                        try{
                            arena.setPlayerLives(p, -1);
                        } catch (NullPointerException exception){
                            EndGame.gameIsDone(arena);
                        }
                    }
                    Bukkit.getServer().getScheduler().cancelTask(arena.getCountdown());
                    p.teleport(arena.getLobbyLocation());
                    StartGame.nextDiver(arena);
                }
            }
        }
    }
}
