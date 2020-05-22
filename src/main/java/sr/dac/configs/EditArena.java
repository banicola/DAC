package sr.dac.configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import sr.dac.main.Main;

public class EditArena {
    public static void openEditionGUI(Player p, String arena){
        if(ArenaManager.getArena(arena)==null){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.arenaUnknown")));
        } else{
            Inventory editGui = Bukkit.createInventory(p, 18, ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiTitle")));

            p.openInventory(editGui);
        }
    }
}
