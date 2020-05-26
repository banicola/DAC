package sr.dac.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.utils.CountdownDive;
import sr.dac.utils.CountdownStart;

public class StartGame {

    public static void startGame(Arena a){
        a.setCountdown(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new CountdownStart(Main.getPlugin().getConfig().getInt("countdownBeforeStart"), a), 0L, 20L));
    }

    public static void inGame(Arena a){
        Bukkit.getScheduler().cancelTask(a.getCountdown());
        a.resetArena();
        a.setStatus("playing");
        nextDiver(a);
    }

    private static void letsJump(Arena a, int diverNum){
        Player diver = a.getPlayers().get(diverNum);
        a.setCountdown(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new CountdownDive(diver, a), 0L, 20L));
        diver.teleport(a.getDivingLocation());
    }

    public static void nextDiver(Arena a){
        Bukkit.getScheduler().cancelTask(a.getCountdown());
        if(a.getPlayers().size()==1){
            EndGame.gameIsDone(a);
        } else {
            a.nextDiver();
            Player nextDiverAlert;
            try{
                nextDiverAlert = a.getPlayers().get(a.getDiver()+1);
            } catch (IndexOutOfBoundsException exception){
                nextDiverAlert = a.getPlayers().get(0);
            }
            nextDiverAlert.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.nextPlayerAlert")));
            letsJump(a, a.getDiver());
        }
    }
}
