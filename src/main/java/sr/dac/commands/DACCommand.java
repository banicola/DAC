package sr.dac.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sr.dac.main.Config;
import sr.dac.main.Lang;
import sr.dac.main.Main;

import java.util.Collections;

public class DACCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length > 0 && args[0].equalsIgnoreCase("join")){
            if(sender.hasPermission("dac.join")){
                if(args.length<2)sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("wrongCommands.wrongJoinCmd")));
                else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("leave")){
            if(sender.hasPermission("dac.leave")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("list")){
            if(sender.hasPermission("dac.list")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("event")){
            if(sender.hasPermission("dac.event")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("create")){
            if(sender.hasPermission("dac.create")){
                if(args.length<2)sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("wrongCommands.wrongCreateCmd")));
                else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("delete")){
            if(sender.hasPermission("dac.delete")){
                if(args.length<2)sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("wrongCommands.wrongDeleteCmd")));
                else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.devLock")));
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if(args.length > 0 && args[0].equalsIgnoreCase("reload")){
            if(sender.hasPermission("dac.reload")){
                Config.reloadConfig();
                Lang.reloadConfig();
                Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.reloadConfig")));
                if(sender instanceof Player){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.reloadConfig")));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("version")){
            if(sender.hasPermission("dac.version")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.version").replace("%version%", Main.getPlugin().getDescription().getVersion())));
                BaseComponent[] patchNote = {new TextComponent("Patchnote")};
                TextComponent version = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.patchnote")));
                version.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, patchNote));
                sender.spigot().sendMessage(version);
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.noPermission")));
            }
        } else if(args.length==0 || (args.length == 1 && (args[0].equalsIgnoreCase("help")||args[0].equalsIgnoreCase("?")))) {
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
                if(sender.hasPermission("dac.create")){
                    TextComponent create = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.create")));
                    create.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac create" ));
                    sender.spigot().sendMessage(create);
                }
                if(sender.hasPermission("dac.delete")){
                    TextComponent delete = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.delete")));
                    delete.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac delete" ));
                    sender.spigot().sendMessage(delete);
                }
                if(sender.hasPermission("dac.version")){
                    TextComponent version = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.version")));
                    version.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac version" ));
                    sender.spigot().sendMessage(version);
                }
                if(sender.hasPermission("dac.reload")){
                    TextComponent reload = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("menu.reload")));
                    reload.setClickEvent(new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/dac reload" ));
                    sender.spigot().sendMessage(reload);
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l"+String.join("", Collections.nCopies(45, "-"))));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.playerOnly")));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.unknownCommand")));
        }
        return true;
    }

    private String removeChatColor(String topLayer){
        if(!topLayer.contains("&")){
            return topLayer;
        } else {
            return removeChatColor(topLayer.substring(0, topLayer.indexOf("&"))+topLayer.substring(topLayer.indexOf("&") + 2));
        }
    }
}
