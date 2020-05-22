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
import sr.dac.configs.ArenaManager;
import sr.dac.configs.EditArena;
import sr.dac.main.Arena;
import sr.dac.main.Config;
import sr.dac.main.Lang;
import sr.dac.main.Main;
import sr.dac.utils.ChangeLog;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

public class DACCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("join")) {
            if (sender.hasPermission("dac.join")) {
                if (args.length < 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongJoinCmd")));
                else
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.devLock")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("leave")) {
            if (sender.hasPermission("dac.leave")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.devLock")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
            if (sender.hasPermission("dac.list")) {
                Set<String> arenas = ArenaManager.getArenas();
                if(arenas.size()==0) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("listArenas.empty")));
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("listArenas.title")));
                    for(String s : arenas){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f- &a"+s));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("event")) {
            if (sender.hasPermission("dac.event")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.devLock")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
            if (sender.hasPermission("dac.create")) {
                if (args.length < 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongCreateCmd")));
                else if (args.length>2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongCreateArenaName")));
                else{
                    try{
                        if(ArenaManager.createArena(args[1])){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("createArena.success").replace("%arena%", args[1])));
                            EditArena.openEditionGUI((Player) sender, args[1]);
                        }
                    } catch (KeyAlreadyExistsException e){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("createArena.arenaAlreadyExists").replace("%arena%", args[1])));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("remove")) {
            if (sender.hasPermission("dac.remove")) {
                if (args.length < 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongRemoveCmd")));
                else
                    try{
                        if(ArenaManager.removeArena(args[1])){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("removeArena.success").replace("%arena%", args[1])));
                        }
                    } catch (NoSuchElementException e){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("removeArena.arenaUnknown").replace("%arena%", args[1])));
                    }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("edit")) {
            if (sender.hasPermission("dac.edit")) {
                if (args.length < 2){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditCmd")));
                }
                else if (args.length==2){
                    if(sender instanceof Player) EditArena.openEditionGUI((Player) sender, args[1]);
                    else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.playerOnly")));
                } else if(args.length==3){
                    Arena arena = ArenaManager.getArena(args[1]);
                    if(args[2].equalsIgnoreCase("setlobby")){
                        if(arena==null){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.arenaUnknown")));
                        } else {
                            Player p = (Player) sender;
                            arena.setLobbyLocation(p.getLocation());
                            try {
                                ArenaManager.save(args[1], arena);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.successSetLobby")));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else if(args[2].equalsIgnoreCase("setdiving")){
                        if(arena==null){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.arenaUnknown")));
                        } else {
                            Player p = (Player) sender;
                            arena.setDivingLocation(p.getLocation());
                            try {
                                ArenaManager.save(args[1], arena);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("editArena.successSetDiving")));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else if(args[2].equalsIgnoreCase("setname")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetName")));
                    } else if(args[2].equalsIgnoreCase("setmin")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetMin")));
                    } else if(args[2].equalsIgnoreCase("setmax")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetMax")));
                    }
                } else {
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("dac.reload")) {
                Config.reloadConfig();
                Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&', Main.f.getString("debug.reloadConfig")));
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.reloadConfig")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("version")) {
            if (sender.hasPermission("dac.version")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.version").replace("%version%", Main.getPlugin().getDescription().getVersion())));
                BaseComponent changeLogs = null;
                for(String note : ChangeLog.changeLog()){
                    if(changeLogs == null) changeLogs = new TextComponent(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.changeLogHeader").replace("%version%", Main.getPlugin().getDescription().getVersion())+"\n"+note+"\n"));
                    else changeLogs.addExtra(new TextComponent(ChatColor.translateAlternateColorCodes('&',note+"\n")));
                }
                BaseComponent[] changeLog;
                TextComponent footer = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("debug.changeLogFooter")));
                if(changeLogs != null){
                    changeLog = new BaseComponent[]{changeLogs, footer};
                } else {
                    changeLog = new BaseComponent[]{new TextComponent("No changelog found"),footer};
                }
                TextComponent version = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("debug.changelog")));
                version.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, changeLog));
                version.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/"));

                sender.spigot().sendMessage(version);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.noPermission")));
            }
        } else if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")))) {
            if (sender instanceof Player) {
                String topLayer = Main.f.getString("menu.title");
                String editedLayer = removeChatColor(topLayer);
                int lineNeeded = 45 - editedLayer.length();
                String newTopLayer = "&8&l" + String.join("", Collections.nCopies((int) Math.round(lineNeeded / 2.0), "-")) + " " + topLayer + " " + "&8&l" + String.join("", Collections.nCopies((int) Math.round(lineNeeded / 2.0), "-"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', newTopLayer));

                TextComponent join = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.join")));
                join.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac join"));
                TextComponent leave = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.leave")));
                leave.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac leave"));
                TextComponent list = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.list")));
                list.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac list"));
                sender.spigot().sendMessage(join);
                sender.spigot().sendMessage(leave);
                sender.spigot().sendMessage(list);
                if (sender.hasPermission("dac.create")) {
                    TextComponent create = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.create")));
                    create.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac create"));
                    sender.spigot().sendMessage(create);
                }
                if (sender.hasPermission("dac.remove")) {
                    TextComponent remove = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.remove")));
                    remove.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac remove"));
                    sender.spigot().sendMessage(remove);
                }
                if (sender.hasPermission("dac.edit")) {
                    TextComponent edit = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.edit")));
                    edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac edit"));
                    sender.spigot().sendMessage(edit);
                }
                if (sender.hasPermission("dac.version")) {
                    TextComponent version = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.version")));
                    version.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac version"));
                    sender.spigot().sendMessage(version);
                }
                if (sender.hasPermission("dac.reload")) {
                    TextComponent reload = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.reload")));
                    reload.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac reload"));
                    sender.spigot().sendMessage(reload);
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + String.join("", Collections.nCopies(45, "-"))));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.playerOnly")));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("debug.unknownCommand")));
        }
        return true;
    }

    private String removeChatColor(String topLayer) {
        if (!topLayer.contains("&")) {
            return topLayer;
        } else {
            return removeChatColor(topLayer.substring(0, topLayer.indexOf("&")) + topLayer.substring(topLayer.indexOf("&") + 2));
        }
    }
}
