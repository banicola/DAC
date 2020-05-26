package sr.dac.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Config;
import sr.dac.main.Main;
import sr.dac.main.Menu;
import sr.dac.utils.PlayerMenuUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectBlockMenu extends Menu {

    public SelectBlockMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return "Select your block !";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void interactMenu(InventoryClickEvent e) {
        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',"&4Fermer"))){
            e.getWhoClicked().closeInventory();
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',"&3Précédent"))){
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',"&2Suivant"))){
        } else {
            Arena arena = ArenaManager.getArena(ArenaManager.getPlayerArena((Player) e.getWhoClicked()));
            arena.setPlayerMaterial((Player) e.getWhoClicked(), e.getCurrentItem().getType());
            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("game.blockSelected").replace("%block%", e.getCurrentItem().getType().name())));
            e.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent e) {
        Arena arena = ArenaManager.getArena(ArenaManager.getPlayerArena((Player) e.getPlayer()));
        if(arena!=null){
            if(arena.getPlayerMaterial((Player) e.getPlayer())==null){
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }, 1L);
            }
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack emptyCase = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyCase_meta = emptyCase.getItemMeta();
        emptyCase_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&'," "));
        emptyCase.setItemMeta(emptyCase_meta);
        int[] emptyPositions = {1,2,3,4,5,6,7,8,9,17,18,26,27,35,37,38,39,40,41,42,43};
        for(int i : emptyPositions){
            inventory.setItem(i,emptyCase);
        }

        ItemStack returnButton = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta returnButton_meta = returnButton.getItemMeta();
        returnButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&4Fermer"));
        returnButton.setItemMeta(returnButton_meta);
        inventory.setItem(0,returnButton);

        ItemStack previousButton = new ItemStack(Material.BLUE_CONCRETE, 1);
        ItemMeta previousButton_meta = previousButton.getItemMeta();
        previousButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&3Précédent"));
        previousButton.setItemMeta(previousButton_meta);
        inventory.setItem(36,previousButton);

        ItemStack nextButton = new ItemStack(Material.GREEN_CONCRETE, 1);
        ItemMeta nextButton_meta = nextButton.getItemMeta();
        nextButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&2Suivant"));
        nextButton.setItemMeta(nextButton_meta);
        inventory.setItem(44,nextButton);

        List<ItemStack> availableBlocks = new ArrayList<>();
        for(String m : Config.blocksConfig.getKeys(false)){
            if(Material.getMaterial(m.toUpperCase())!=null){
                if(Config.blocksConfig.getBoolean(m)){
                    availableBlocks.add(new ItemStack(Material.getMaterial(m.toUpperCase())));
                }
            }
        }
        int position = 10;
        for(ItemStack i : availableBlocks){
            if(position==17||position==26){
                position+=2;
            } else if (position==35){
                break;
            } else {
                inventory.setItem(position,i);
                position++;
            }
        }
    }
}
