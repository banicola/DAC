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
import sr.dac.events.StartGame;
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

    private int start = 0;
    private int end = 21;

    private int blocksListSize = 0;

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.title"));
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void interactMenu(InventoryClickEvent e) {
        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.exit")))){
            e.getWhoClicked().closeInventory();
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.previous")))){
            if(start>0){
                start-=21;
                end-=21;
                setMenuItems();
            }
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.next")))){
            start+=21;
            end +=21;
            setMenuItems();
        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.random")))){
            StartGame.selectRandomBlock((Player) e.getWhoClicked(),ArenaManager.getArena(ArenaManager.getPlayerArena((Player) e.getWhoClicked())));
            e.getWhoClicked().closeInventory();
        }else {
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
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), this::open, 1L);
            }
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack emptyCase = new ItemStack(Material.getMaterial(Main.getPlugin().getConfig().getString("emptySlotsMenu").toUpperCase()), 1);
        ItemMeta emptyCase_meta = emptyCase.getItemMeta();
        emptyCase_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&'," "));
        emptyCase.setItemMeta(emptyCase_meta);
        int[] emptyPositions = {1,2,3,4,5,6,7,8,9,17,18,26,27,35,37,38,39,41,42,43};
        for(int i : emptyPositions){
            inventory.setItem(i,emptyCase);
        }

        ItemStack exitButton = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta exitButton_meta = exitButton.getItemMeta();
        exitButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.exit")));
        exitButton.setItemMeta(exitButton_meta);
        inventory.setItem(0,exitButton);

        ItemStack previousButton = new ItemStack(Material.BLUE_CONCRETE, 1);
        ItemMeta previousButton_meta = previousButton.getItemMeta();
        previousButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.previous")));
        previousButton.setItemMeta(previousButton_meta);
        if(start>0){
            inventory.setItem(36,previousButton);
        } else{
            inventory.setItem(36,emptyCase);
        }

        ItemStack randomSelect = new ItemStack(Material.ENDER_PEARL);
        ItemMeta randomSelect_meta = randomSelect.getItemMeta();
        randomSelect_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.random")));
        randomSelect.setItemMeta(randomSelect_meta);
        inventory.setItem(40, randomSelect);

        ItemStack nextButton = new ItemStack(Material.GREEN_CONCRETE, 1);
        ItemMeta nextButton_meta = nextButton.getItemMeta();
        nextButton_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Main.f.getString("blockSelection.next")));
        nextButton.setItemMeta(nextButton_meta);

        List<ItemStack> blocks2Display = blocksToDisplay(start,end);

        if(blocks2Display.size()==21 && blocksListSize>end+1) inventory.setItem(44,nextButton);
        else inventory.setItem(44,emptyCase);

        int position = 10;
        for(ItemStack i : blocks2Display){
            if(position==17||position==26){
                position+=2;
            } else if (position==35){
                break;
            }
            inventory.setItem(position,i);
            position++;
        }
    }

    private List<ItemStack> blocksToDisplay(int start, int end){
        List<ItemStack> availableBlocks = new ArrayList<>();
        for(String m : Config.blocksConfig.getKeys(false)){
            if(Material.getMaterial(m.toUpperCase())!=null){
                if(Config.blocksConfig.getBoolean(m)){
                    if(Main.getPlugin().getConfig().getBoolean("perBlockPermission")){
                        if(playerMenuUtil.getP().hasPermission("dac.blocks."+m.toUpperCase())){
                            availableBlocks.add(new ItemStack(Material.getMaterial(m.toUpperCase())));
                        }
                    } else {
                        availableBlocks.add(new ItemStack(Material.getMaterial(m.toUpperCase())));
                    }
                }
            }
        }
        blocksListSize = availableBlocks.size();
        if(availableBlocks.size()>21){
            try{
                return availableBlocks.subList(start, end);
            } catch (IndexOutOfBoundsException exception) {
                availableBlocks = availableBlocks.subList(start, availableBlocks.size());
                while(availableBlocks.size()<21){
                    availableBlocks.add(new ItemStack(Material.AIR));
                }
                return availableBlocks;
            }
        } else {
            return availableBlocks;
        }
    }
}
