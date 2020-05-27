package sr.dac.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.main.Menu;
import sr.dac.utils.PlayerMenuUtil;

import java.util.ArrayList;

public class PlayerLimitMenu extends Menu {
    Arena arena;
    String type;
    public PlayerLimitMenu(PlayerMenuUtil playerMenuUtil, Arena arena, String type) {
        super(playerMenuUtil);
        this.arena = arena;
        this.type = type;
    }

    @Override
    public String getMenuName() {
        String title = "";
        if(type=="min") title = Main.f.getString("editArena.guiMinPlayerTitle");
        else title = Main.f.getString("editArena.guiMaxPlayerTitle");
        return ChatColor.translateAlternateColorCodes('&', title+" "+arena.getName());
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void interactMenu(InventoryClickEvent e) {
        ItemStack playerCountItem = e.getInventory().getItem(4);
        ItemMeta playerCountItem_meta = playerCountItem.getItemMeta();
        int playerCount = Integer.parseInt((String) playerCountItem.getItemMeta().getLore().toArray()[0]);
        if (e.getCurrentItem() != null) {
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiSub"))) && playerCount>0) {
                if(type.equalsIgnoreCase("min")){
                    if(playerCount-1<1){
                        return;
                    }
                }
                ArrayList<String> minPlayer_lore = new ArrayList<>();
                minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+(playerCount-1)));
                playerCountItem_meta.setLore(minPlayer_lore);
                playerCountItem.setItemMeta(playerCountItem_meta);
                if(playerCount-1<=0) playerCountItem.setAmount(1);
                else playerCountItem.setAmount(playerCount-1);
                e.getInventory().setItem(4, playerCountItem);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiAdd")))) {
                ArrayList<String> minPlayer_lore = new ArrayList<>();
                minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+(playerCount+1)));
                playerCountItem_meta.setLore(minPlayer_lore);
                playerCountItem.setItemMeta(playerCountItem_meta);
                playerCountItem.setAmount(playerCount+1);
                e.getInventory().setItem(4, playerCountItem);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiValidation")))) {
                if(type.equalsIgnoreCase("min")){
                    if(playerCount>arena.getMax_player()) arena.setMax_player(playerCount);
                    arena.setMin_player(playerCount);
                    playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMinPlayerValid").replace("%number%", ""+playerCount)));
                    e.getView().close();
                    new ArenaEditionMenu(Main.getPlayerMenuUtil((Player) playerMenuUtil.getP()), arena).open();
                } else if(type.equalsIgnoreCase("max")){
                    if(playerCount<arena.getMin_player()){
                        playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMaxPlayerTooLow")));
                    } else {
                        arena.setMax_player(playerCount);
                        playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMaxPlayerValid").replace("%number%", ""+playerCount)));
                        e.getView().close();
                        new ArenaEditionMenu(Main.getPlayerMenuUtil((Player) playerMenuUtil.getP()), arena).open();
                    }
                }
            }
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {
        if(type=="min"){
            ItemStack minPlayerSelection = new ItemStack(Material.SNOWBALL);
            ItemMeta minPlayerSelection_meta = minPlayerSelection.getItemMeta();
            minPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")));
            ArrayList<String> minPlayer_lore = new ArrayList<>();
            minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+arena.getMin_player()));
            minPlayerSelection_meta.setLore(minPlayer_lore);
            minPlayerSelection.setItemMeta(minPlayerSelection_meta);
            if(arena.getMin_player()==0) minPlayerSelection.setAmount(1);
            else minPlayerSelection.setAmount(arena.getMin_player());

            inventory.setItem(4, minPlayerSelection);
        } else {
            ItemStack maxPlayerSelection = new ItemStack(Material.SLIME_BALL);
            ItemMeta maxPlayerSelection_meta = maxPlayerSelection.getItemMeta();
            maxPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")));
            ArrayList<String> maxPlayer_lore = new ArrayList<>();
            maxPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+arena.getMax_player()));
            maxPlayerSelection_meta.setLore(maxPlayer_lore);
            maxPlayerSelection.setItemMeta(maxPlayerSelection_meta);
            if(arena.getMax_player()==0) maxPlayerSelection.setAmount(1);
            else maxPlayerSelection.setAmount(arena.getMax_player());

            inventory.setItem(4, maxPlayerSelection);
        }

        ItemStack sub = new ItemStack(Material.RED_CONCRETE);
        ItemMeta sub_meta = sub.getItemMeta();
        sub_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiSub")));
        sub.setItemMeta(sub_meta);
        inventory.setItem(12, sub);

        ItemStack add = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta add_meta = add.getItemMeta();
        add_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiAdd")));
        add.setItemMeta(add_meta);
        inventory.setItem(14, add);

        ItemStack validation = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta validation_meta = validation.getItemMeta();
        validation_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiValidation")));
        validation.setItemMeta(validation_meta);
        inventory.setItem(22, validation);
    }
}
