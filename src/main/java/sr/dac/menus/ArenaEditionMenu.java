package sr.dac.menus;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.main.Menu;
import sr.dac.utils.PlayerMenuUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArenaEditionMenu extends Menu {
    Arena arena;
    public ArenaEditionMenu(PlayerMenuUtil playerMenuUtil, Arena arena) {
        super(playerMenuUtil);
        this.arena = arena;
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiTitle")+" "+arena.getName());
    }

    @Override
    public int getSlots() {
        return 18;
    }

    @Override
    public void interactMenu(InventoryClickEvent e) {
        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")))) {
            List<ItemStack> allStatus = Arrays.asList(e.getInventory().getItem(11),e.getInventory().getItem(12), e.getInventory().getItem(13), e.getInventory().getItem(14), e.getInventory().getItem(15));
            boolean isReady=true;
            for(ItemStack i : allStatus){
                if(!i.getType().equals(Material.LIME_CONCRETE)) isReady= false;
            }
            if(isReady){
                arena.setOpen();
                ItemStack block = e.getCurrentItem();
                if(arena.isOpen()){
                    block.setType(Material.LIME_CONCRETE);
                    playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isOpen").replace("%arena%",arena.getName())));
                } else {
                    block.setType(Material.RED_CONCRETE);
                    playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isClosed").replace("%arena%",arena.getName())));
                }
                e.getInventory().setItem(7, block);
            }
        } else {
            if(!arena.isOpen()){
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiPoolSelectionTitle")))) {
                    ItemStack selector = new ItemStack(Material.GLASS);
                    ItemMeta selector_meta = selector.getItemMeta();
                    selector_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")));
                    ArrayList<String> selector_lore = new ArrayList<>();
                    selector_lore.add(arena.getName());
                    selector_meta.setLore(selector_lore);
                    selector.setItemMeta(selector_meta);
                    e.getWhoClicked().getInventory().setItem(0, selector);
                    e.getView().close();
                    arena.setPoolLocation(new AbstractMap.SimpleEntry<>(null, null));
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiDivingSelectionTitle")))) {
                    TextComponent t1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.setClickPos")));
                    t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dac edit "+arena.getName()+" setdiving"));
                    BaseComponent[] b = new BaseComponent[]{t1};;
                    playerMenuUtil.getP().spigot().sendMessage(b);
                    e.getView().close();
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiLobbySelectionTitle")))) {
                    TextComponent t1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.setClickPos")));
                    t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dac edit "+arena.getName()+" setlobby"));
                    BaseComponent[] b = new BaseComponent[]{t1};;
                    playerMenuUtil.getP().spigot().sendMessage(b);
                    e.getView().close();
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")))) {
                    e.getView().close();
                    new PlayerLimitMenu(Main.getPlayerMenuUtil((Player) playerMenuUtil.getP()), arena, "min").open();
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))) {
                    e.getView().close();
                    new PlayerLimitMenu(Main.getPlayerMenuUtil((Player) playerMenuUtil.getP()), arena, "max").open();
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")))) {
                    List<ItemStack> allStatus = Arrays.asList(e.getInventory().getItem(11),e.getInventory().getItem(12), e.getInventory().getItem(13), e.getInventory().getItem(14), e.getInventory().getItem(15));
                    boolean isReady=true;
                    for(ItemStack i : allStatus){
                        if(!i.getType().equals(Material.LIME_CONCRETE)) isReady= false;
                    }
                    if(isReady){
                        arena.setOpen();
                        ItemStack block = e.getCurrentItem();
                        if(arena.isOpen()){
                            block.setType(Material.LIME_CONCRETE);
                            playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isOpen").replace("%arena%",arena.getName())));
                        } else {
                            block.setType(Material.RED_CONCRETE);
                            playerMenuUtil.getP().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isClosed").replace("%arena%",arena.getName())));
                        }
                        e.getInventory().setItem(7, block);
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiNameEdit")))){
                    TextComponent cmd = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.nameCmdRedirect")));
                    cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac edit "+arena.getName()+" setname "));
                    playerMenuUtil.getP().spigot().sendMessage(cmd);
                    e.getView().close();
                }
            } else {
                playerMenuUtil.getP().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.arenaOpenError")));
            }
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {
        /**
         * nameSelection
         */
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
        inventory.setItem(1, nameSelection);

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
        inventory.setItem(2, divingSelection);
        inventory.setItem(11, divingSelectionStatus);

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
        inventory.setItem(3, lobbySelection);
        inventory.setItem(12, lobbySelectionStatus);

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
        inventory.setItem(4, poolSelection);
        inventory.setItem(13, poolSelectionStatus);

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
        inventory.setItem(5, minPlayerSelection);
        inventory.setItem(14, minPlayerStatus);

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
        inventory.setItem(6, maxPlayerSelection);
        inventory.setItem(15, maxPlayerStatus);

        /**
         * openArenaSelection
         */
        ItemMeta openArenaSelection_meta;
        ItemStack openArenaSelection;
        if(arena.isOpen()){
            openArenaSelection = new ItemStack(Material.LIME_CONCRETE);
        }else{
            openArenaSelection = new ItemStack(Material.RED_CONCRETE);
        }
        openArenaSelection_meta = openArenaSelection.getItemMeta();
        openArenaSelection_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")));
        openArenaSelection.setItemMeta(openArenaSelection_meta);
        inventory.setItem(7, openArenaSelection);

    }
}
