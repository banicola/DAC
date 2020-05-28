package sr.dac.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sr.dac.main.Arena;
import sr.dac.main.Config;
import sr.dac.main.Main;
import sr.dac.utils.CountdownDive;
import sr.dac.utils.CountdownStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartGame {

    public static void startGame(Arena a){
        a.setCountdown(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new CountdownStart(Main.getPlugin().getConfig().getInt("countdownBeforeStart"), a), 0L, 20L));
    }

    public static void inGame(Arena a){
        Bukkit.getScheduler().cancelTask(a.getCountdown());
        a.resetArena();
        a.setStatus("playing");
        for(Location sign : a.getSigns()){
            a.updateSign(sign);
        }
        nextDiver(a);
    }

    private static void letsJump(Arena a, int diverNum){
        Player diver = a.getPlayers().get(diverNum);
        a.setCountdown(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new CountdownDive(diver, a), 0L, 20L));
        diver.teleport(a.getDivingLocation());
    }

    public static void nextDiver(Arena a){
        int playersLeft = 0;
        for(Player p:a.getPlayers()){
            if(a.getPlayerLives(p)>=0){
                playersLeft++;
            }
        }
        if(playersLeft==1&&!a.getStatus().equals("ending")){
            EndGame.gameIsDone(a);
        } else {
            int diver = a.nextDiver(a.getDiver()+1);
            a.setDiver(diver);
            Player nextDiverAlert;
            nextDiverAlert = a.getPlayers().get(a.nextDiver(a.getDiver()+1));
            nextDiverAlert.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.nextPlayerAlert")));
            letsJump(a, diver);
        }
    }

    public static void selectRandomBlock(Player p, Arena a){
        List<ItemStack> availableBlocks = new ArrayList<>();
        for(String m : Config.blocksConfig.getKeys(false)){
            if(Material.getMaterial(m.toUpperCase())!=null){
                if(Config.blocksConfig.getBoolean(m)){
                    availableBlocks.add(new ItemStack(Material.getMaterial(m.toUpperCase())));
                }
            }
        }
        Random rand = new Random();
        ItemStack block = availableBlocks.get(rand.nextInt(availableBlocks.size()));
        a.setPlayerMaterial(p, block.getType());
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.randomBlockSelected").replace("%block%",block.getType().name())));
    }
}
