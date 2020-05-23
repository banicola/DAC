package sr.dac.configs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.main.Arena;
import sr.dac.main.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditArena {
    public static void openEditionGUI(Player p, String a){
        Arena arena = ArenaManager.getArena(a);
        if(p.getInventory().getItem(0)!=null && p.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")))){
            p.getInventory().setItem(0, new ItemStack(Material.AIR));
        }
        if(arena==null){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("arena.arenaUnknown")));
        } else {
            Inventory editGui = Bukkit.createInventory(p, 18, ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiTitle")+" "+a));
            ItemStack nameSelection = new ItemStack(Material.NAME_TAG);
            ItemMeta nameSelection_meta = nameSelection.getItemMeta();
            nameSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiNameEdit")));
            ArrayList<String> name_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiNameEditLore")){
                name_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            name_lore.add(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiNameEditActual").replace("%arena%",arena.getName())));
            nameSelection_meta.setLore(name_lore);
            nameSelection.setItemMeta(nameSelection_meta);

            /**
             * divingSelection
             */
            ItemStack divingSelection = new ItemStack(Material.FEATHER);
            ItemMeta divingSelection_meta = divingSelection.getItemMeta();
            divingSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiDivingSelectionTitle")));
            ArrayList<String> diving_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiDivingLore")){
                diving_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            divingSelection_meta.setLore(diving_lore);
            divingSelection.setItemMeta(divingSelection_meta);

            diving_lore = new ArrayList<>();
            ItemMeta divingSelectionStatus_meta;
            ItemStack divingSelectionStatus;
            if(arena.getDivingLocation()==null){
                divingSelectionStatus = new ItemStack(Material.RED_CONCRETE);
                divingSelectionStatus_meta = divingSelectionStatus.getItemMeta();
                divingSelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                diving_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiNoSetup")));
            } else {
                divingSelectionStatus = new ItemStack(Material.LIME_CONCRETE);
                divingSelectionStatus_meta = divingSelectionStatus.getItemMeta();
                divingSelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                diving_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos")).replace("%x%",""+arena.getDivingLocation().getBlockX()).replace("%z%",""+arena.getDivingLocation().getBlockZ()));
            }
            divingSelectionStatus_meta.setLore(diving_lore);
            divingSelectionStatus.setItemMeta(divingSelectionStatus_meta);

            /**
             * lobbySelection
             */
            ItemStack lobbySelection = new ItemStack(Material.SPRUCE_SLAB);
            ItemMeta lobbySelection_meta = lobbySelection.getItemMeta();
            lobbySelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiLobbySelectionTitle")));
            ArrayList<String> lobby_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiLobbyLore")){
                lobby_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            lobbySelection_meta.setLore(lobby_lore);
            lobbySelection.setItemMeta(lobbySelection_meta);

            lobby_lore = new ArrayList<>();
            ItemMeta lobbySelectionStatus_meta;
            ItemStack lobbySelectionStatus;
            if(arena.getLobbyLocation()==null){
                lobbySelectionStatus = new ItemStack(Material.RED_CONCRETE);
                lobbySelectionStatus_meta = lobbySelectionStatus.getItemMeta();
                lobbySelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                lobby_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiNoSetup")));
            } else {
                lobbySelectionStatus = new ItemStack(Material.LIME_CONCRETE);
                lobbySelectionStatus_meta = lobbySelectionStatus.getItemMeta();
                lobbySelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                lobby_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos")).replace("%x%",""+arena.getLobbyLocation().getBlockX()).replace("%z%",""+arena.getLobbyLocation().getBlockZ()));
            }
            lobbySelectionStatus_meta.setLore(lobby_lore);
            lobbySelectionStatus.setItemMeta(lobbySelectionStatus_meta);

            /**
             * poolSelection
             */
            ItemStack poolSelection = new ItemStack(Material.WATER_BUCKET);
            ItemMeta poolSelection_meta = poolSelection.getItemMeta();
            poolSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiPoolSelectionTitle")));
            ArrayList<String> poolSelection_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiPoolSelectionLore")){
                poolSelection_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            poolSelection_meta.setLore(poolSelection_lore);
            poolSelection.setItemMeta(poolSelection_meta);

            ArrayList<String> psm_lore = new ArrayList<>();
            ItemMeta poolSelectionStatus_meta;
            ItemStack poolSelectionStatus;
            if(arena.getPoolLocation().getKey()==null || arena.getPoolLocation().getValue()==null){
                poolSelectionStatus = new ItemStack(Material.RED_CONCRETE);
                poolSelectionStatus_meta = poolSelectionStatus.getItemMeta();
                poolSelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                if(arena.getPoolLocation().getKey()==null && arena.getPoolLocation().getValue()==null) psm_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiNoSetup")));
                else if(arena.getPoolLocation().getKey()==null) psm_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos").replace("%x%",""+arena.getPoolLocation().getValue().getBlockX()).replace("%z%",""+arena.getPoolLocation().getValue().getBlockZ())));
                else if(arena.getPoolLocation().getValue()==null) psm_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos").replace("%x%",""+arena.getPoolLocation().getKey().getBlockX()).replace("%z%",""+arena.getPoolLocation().getKey().getBlockZ())));
            } else {
                poolSelectionStatus = new ItemStack(Material.LIME_CONCRETE);
                poolSelectionStatus_meta = poolSelectionStatus.getItemMeta();
                poolSelectionStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                psm_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos")).replace("%x%",""+arena.getPoolLocation().getKey().getBlockX()).replace("%z%",""+arena.getPoolLocation().getKey().getBlockZ()));
                psm_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiSelectionPos")).replace("%x%",""+arena.getPoolLocation().getValue().getBlockX()).replace("%z%",""+arena.getPoolLocation().getValue().getBlockZ()));
            }
            poolSelectionStatus_meta.setLore(psm_lore);
            poolSelectionStatus.setItemMeta(poolSelectionStatus_meta);

            /**
             * minPlayerSelection
             */
            ItemStack minPlayerSelection = new ItemStack(Material.SNOWBALL);
            ItemMeta minPlayerSelection_meta = minPlayerSelection.getItemMeta();
            minPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")));
            ArrayList<String> minPlayer_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiMinPlayerLore")){
                minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            minPlayerSelection_meta.setLore(minPlayer_lore);
            minPlayerSelection.setItemMeta(minPlayerSelection_meta);

            minPlayer_lore = new ArrayList<>();
            ItemMeta minPlayerStatus_meta;
            ItemStack minPlayerStatus;
            if(arena.getMin_player()<=0){
                minPlayerStatus = new ItemStack(Material.RED_CONCRETE);
                minPlayerStatus_meta = minPlayerStatus.getItemMeta();
                minPlayerStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiNoSetup")));
            } else {
                minPlayerStatus = new ItemStack(Material.LIME_CONCRETE);
                minPlayerStatus_meta = minPlayerStatus.getItemMeta();
                minPlayerStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiPlayerCount")).replace("%number%",""+arena.getMin_player()));
            }
            minPlayerStatus_meta.setLore(minPlayer_lore);
            minPlayerStatus.setItemMeta(minPlayerStatus_meta);

            /**
             * maxPlayerSelection
             */
            ItemStack maxPlayerSelection = new ItemStack(Material.SLIME_BALL);
            ItemMeta maxPlayerSelection_meta = maxPlayerSelection.getItemMeta();
            maxPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")));
            ArrayList<String> maxPlayer_lore = new ArrayList<>();
            for(String s : Main.f.getStringList("editArena.guiMaxPlayerLore")){
                maxPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',s));
            }
            maxPlayerSelection_meta.setLore(maxPlayer_lore);
            maxPlayerSelection.setItemMeta(maxPlayerSelection_meta);

            maxPlayer_lore = new ArrayList<>();
            ItemMeta maxPlayerStatus_meta;
            ItemStack maxPlayerStatus;
            if(arena.getMax_player()<=0 || arena.getMax_player()<arena.getMin_player()){
                maxPlayerStatus = new ItemStack(Material.RED_CONCRETE);
                maxPlayerStatus_meta = maxPlayerStatus.getItemMeta();
                maxPlayerStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                maxPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiNoSetup")));
            } else {
                maxPlayerStatus = new ItemStack(Material.LIME_CONCRETE);
                maxPlayerStatus_meta = maxPlayerStatus.getItemMeta();
                maxPlayerStatus_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiStatusTitle")));
                maxPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',Main.f.getString("editArena.guiPlayerCount")).replace("%number%",""+arena.getMax_player()));
            }
            maxPlayerStatus_meta.setLore(maxPlayer_lore);
            maxPlayerStatus.setItemMeta(maxPlayerStatus_meta);

            /**
             * openArenaSelection
             */
            List<ItemStack> allStatus = Arrays.asList(divingSelectionStatus,lobbySelectionStatus, poolSelectionStatus, minPlayerStatus, maxPlayerStatus);
            boolean isReady=true;
            for(ItemStack i : allStatus){
                if(!i.getType().equals(Material.GREEN_CONCRETE)) isReady= false;
            }
            ItemMeta openArenaSelection_meta;
            ItemStack openArenaSelection;
            if(arena.isOpen()){
                openArenaSelection = new ItemStack(Material.LIME_CONCRETE);
                openArenaSelection_meta = openArenaSelection.getItemMeta();
                openArenaSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")));
            }else{
                openArenaSelection = new ItemStack(Material.RED_CONCRETE);
                openArenaSelection_meta = openArenaSelection.getItemMeta();
                openArenaSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")));
            }
            //openArenaSelection_meta.setLore(maxPlayer_lore);
            openArenaSelection.setItemMeta(openArenaSelection_meta);

            /**
             * Set the gui display
             */
            editGui.setItem(1, nameSelection);
            editGui.setItem(2, divingSelection);
            editGui.setItem(3, lobbySelection);
            editGui.setItem(4, poolSelection);
            editGui.setItem(5, minPlayerSelection);
            editGui.setItem(6, maxPlayerSelection);
            editGui.setItem(7, openArenaSelection);
            editGui.setItem(11, divingSelectionStatus);
            editGui.setItem(12, lobbySelectionStatus);
            editGui.setItem(13, poolSelectionStatus);
            editGui.setItem(14, minPlayerStatus);
            editGui.setItem(15, maxPlayerStatus);

            p.openInventory(editGui);
        }
    }
    public static void openPlayerLimitGUI(Player p, Arena a, String type){
        String title = "";
        if(type=="min") title = Main.f.getString("editArena.guiMinPlayerTitle");
        else title = Main.f.getString("editArena.guiMaxPlayerTitle");
        Inventory playerLimitGui = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', title+" "+a.getName()));

        if(type=="min"){
            ItemStack minPlayerSelection = new ItemStack(Material.SNOWBALL);
            ItemMeta minPlayerSelection_meta = minPlayerSelection.getItemMeta();
            minPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")));
            ArrayList<String> minPlayer_lore = new ArrayList<>();
            minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+a.getMin_player()));
            minPlayerSelection_meta.setLore(minPlayer_lore);
            minPlayerSelection.setItemMeta(minPlayerSelection_meta);

            playerLimitGui.setItem(4, minPlayerSelection);
        } else {
            ItemStack maxPlayerSelection = new ItemStack(Material.SLIME_BALL);
            ItemMeta maxPlayerSelection_meta = maxPlayerSelection.getItemMeta();
            maxPlayerSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")));
            ArrayList<String> maxPlayer_lore = new ArrayList<>();
            maxPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+a.getMax_player()));
            maxPlayerSelection_meta.setLore(maxPlayer_lore);
            maxPlayerSelection.setItemMeta(maxPlayerSelection_meta);

            playerLimitGui.setItem(4, maxPlayerSelection);
        }

        ItemStack sub = new ItemStack(Material.RED_CONCRETE);
        ItemMeta sub_meta = sub.getItemMeta();
        sub_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiSub")));
        sub.setItemMeta(sub_meta);
        playerLimitGui.setItem(12, sub);

        ItemStack add = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta add_meta = add.getItemMeta();
        add_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiAdd")));
        add.setItemMeta(add_meta);
        playerLimitGui.setItem(14, add);

        ItemStack validation = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta validation_meta = validation.getItemMeta();
        validation_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiValidation")));
        validation.setItemMeta(validation_meta);
        playerLimitGui.setItem(22, validation);

        p.openInventory(playerLimitGui);
    }
}
