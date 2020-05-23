package sr.dac.listeners;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.configs.ArenaManager;
import sr.dac.configs.EditArena;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.utils.ChangeLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickInventory implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        try{
            String inventoryName = e.getView().getTitle().substring(0,e.getView().getTitle().lastIndexOf(" "));
            Player p = (Player) e.getWhoClicked();
            if (inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiTitle")))) {
                Arena a = ArenaManager.getArena(e.getView().getTitle().substring(e.getView().getTitle().lastIndexOf(" ") + 1));
                if (a == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                } else {
                    if (e.getCurrentItem() != null) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiPoolSelectionTitle")))) {
                            ItemStack selector = new ItemStack(Material.GLASS);
                            ItemMeta selector_meta = selector.getItemMeta();
                            selector_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.selectionTool")));
                            ArrayList<String> selector_lore = new ArrayList<>();
                            selector_lore.add(a.getName());
                            selector_meta.setLore(selector_lore);
                            selector.setItemMeta(selector_meta);
                            e.getWhoClicked().getInventory().setItem(0, selector);
                            e.getView().close();
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiDivingSelectionTitle")))) {
                            TextComponent t1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.setClickPos")));
                            t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dac edit "+a.getName()+" setdiving"));
                            BaseComponent[] b = new BaseComponent[]{t1};;
                            p.spigot().sendMessage(b);
                            e.getView().close();
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiLobbySelectionTitle")))) {
                            TextComponent t1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.setClickPos")));
                            t1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dac edit "+a.getName()+" setlobby"));
                            BaseComponent[] b = new BaseComponent[]{t1};;
                            p.spigot().sendMessage(b);
                            e.getView().close();
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")))) {
                            e.getView().close();
                            EditArena.openPlayerLimitGUI(p, a, "min");
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))) {
                            e.getView().close();
                            EditArena.openPlayerLimitGUI(p, a, "max");
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")))) {
                            List<ItemStack> allStatus = Arrays.asList(e.getInventory().getItem(11),e.getInventory().getItem(12), e.getInventory().getItem(13), e.getInventory().getItem(14), e.getInventory().getItem(15));
                            boolean isReady=true;
                            for(ItemStack i : allStatus){
                                if(!i.getType().equals(Material.LIME_CONCRETE)) isReady= false;
                            }
                            if(isReady){
                                a.setOpen();
                                ItemStack block = e.getCurrentItem();
                                if(a.isOpen()){
                                    block.setType(Material.LIME_CONCRETE);
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isOpen").replace("%arena%",a.getName())));
                                } else {
                                    block.setType(Material.RED_CONCRETE);
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.isClosed").replace("%arena%",a.getName())));
                                }
                                e.getInventory().setItem(7, block);
                            }
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiNameEdit")))){
                            TextComponent cmd = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.nameCmdRedirect")));
                            cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac edit "+a.getName()+" setname "));
                            p.spigot().sendMessage(cmd);
                            e.getView().close();
                        }
                    }
                }
                e.setCancelled(true);
            } else if (inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle"))) || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))) {
                Arena a = ArenaManager.getArena(e.getView().getTitle().substring(e.getView().getTitle().lastIndexOf(" ") + 1));
                ItemStack playerCountItem = e.getInventory().getItem(4);
                ItemMeta playerCountItem_meta = playerCountItem.getItemMeta();
                int playerCount = Integer.parseInt((String) playerCountItem.getItemMeta().getLore().toArray()[0]);
                if (e.getCurrentItem() != null) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiSub"))) && playerCount>0) {
                        if(inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))){
                            if(playerCount-1<a.getMin_player()){
                                e.setCancelled(true);
                                return;
                            }
                        }
                        ArrayList<String> minPlayer_lore = new ArrayList<>();
                        minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+(playerCount-1)));
                        playerCountItem_meta.setLore(minPlayer_lore);
                        playerCountItem.setItemMeta(playerCountItem_meta);
                        e.getInventory().setItem(4, playerCountItem);
                    } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiAdd")))) {
                        ArrayList<String> minPlayer_lore = new ArrayList<>();
                        minPlayer_lore.add(ChatColor.translateAlternateColorCodes('&',""+(playerCount+1)));
                        playerCountItem_meta.setLore(minPlayer_lore);
                        playerCountItem.setItemMeta(playerCountItem_meta);
                        e.getInventory().setItem(4, playerCountItem);
                    } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiValidation")))) {
                        if(inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMinPlayerTitle")))){
                            if(playerCount>a.getMax_player()) a.setMax_player(playerCount);
                            a.setMin_player(playerCount);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMinPlayerValid").replace("%number%", ""+playerCount)));
                            e.getView().close();
                            EditArena.openEditionGUI(p, a.getName());
                        } else if(inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))){
                            if(playerCount<a.getMin_player()){
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMaxPlayerTooLow")));
                            } else {
                                a.setMax_player(playerCount);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.setMaxPlayerValid").replace("%number%", ""+playerCount)));
                                e.getView().close();
                                EditArena.openEditionGUI(p, a.getName());
                            }
                        }
                    }
                }
                e.setCancelled(true);
            }
        } catch (StringIndexOutOfBoundsException exception){
        }
    }
}
