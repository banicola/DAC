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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sr.dac.configs.ArenaManager;
import sr.dac.configs.EditArena;
import sr.dac.main.Arena;
import sr.dac.main.Main;
import sr.dac.utils.ChangeLog;

import java.util.ArrayList;

public class ClickInventory implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        try{
            String inventoryName = e.getView().getTitle().substring(0,e.getView().getTitle().lastIndexOf(" "));
            Player p = (Player) e.getWhoClicked();
            if (inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiTitle")))) {
                Arena a = ArenaManager.getArena(e.getView().getTitle().substring(e.getView().getTitle().lastIndexOf(" ") + 1));
                if (a == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.arenaUnknown")));
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
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiMaxPlayerTitle")))) {
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.f.getString("editArena.guiOpenArenaTitle")))) {
                        }
                    }
                }
                e.setCancelled(true);
            }
        } catch (StringIndexOutOfBoundsException exception){
        }
    }
}
