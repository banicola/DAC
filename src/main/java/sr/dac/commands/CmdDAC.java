package sr.dac.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sr.dac.main.Config;
import sr.dac.main.Lang;
import sr.dac.main.Main;

import java.awt.*;
import java.util.Collections;

public class CmdDAC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(command.getName().equalsIgnoreCase("dac")){
            if(args.length > 0 && args[0].contentEquals("reload")){
                if(sender.hasPermission("dac.reload")){
                    Config.reloadConfig();
                    Lang.reloadConfig();
                    Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.reloadConfig")));
                    if(sender instanceof Player){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.reloadConfig")));
                    }
                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.noPermission")));
                }
            } else {
                if(sender instanceof Player){
                    String topLayer = Main.f.getString("menu.title");
                    String editedLayer = removeChatColor(topLayer);
                    int lineNeeded = 45-editedLayer.length();
                    String newTopLayer = "&8&l"+String.join("", Collections.nCopies((int) Math.round(lineNeeded/2.0), "-"))+" "+topLayer+" "+"&8&l"+String.join("", Collections.nCopies((int) Math.round(lineNeeded/2.0), "-"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', newTopLayer));

                    TextComponent join = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.join")));
                    join.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac join" ));
                    TextComponent leave = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.leave")));
                    leave.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac leave" ));
                    TextComponent list = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.list")));
                    list.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac list" ));
                    sender.spigot().sendMessage(join);
                    sender.spigot().sendMessage(leave);
                    sender.spigot().sendMessage(list);

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l"+String.join("", Collections.nCopies(45, "-"))));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.playerOnly")));
                }
            }
            return true;
        }
        return false;
    }

    private String removeChatColor(String topLayer){
        if(!topLayer.contains("&")){
            return topLayer;
        } else {
            return removeChatColor(topLayer.substring(0, topLayer.indexOf("&"))+topLayer.substring(topLayer.indexOf("&") + 2));
        }
    }
}
