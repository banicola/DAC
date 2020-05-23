package sr.dac.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.utils.Countdown;

import java.util.List;

public class StartGame {
    public static void startGame(Arena a){
        a.setCountdown(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Countdown(Main.getPlugin().getConfig().getInt("countdownBeforeStart"), a), 0L, 20L));
    }

    public static void inGame(Arena a){
        a.setStatus("playing");
    }
}
