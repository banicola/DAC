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
import sr.dac.main.Arena;
import sr.dac.main.Config;
import sr.dac.main.Main;
import sr.dac.menus.ArenaEditionMenu;
import sr.dac.menus.SelectBlockMenu;
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
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongJoinCmd")));
                } else {
                    Arena a = ArenaManager.getArena(args[1]);
                    if (a == null){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                    } else {
                        try{
                            ArenaManager.playerJoinArena((Player) sender, args[1]);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.joinArena").replace("%arena%", args[1])));
                        } catch (NoSuchElementException e){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                        } catch (KeyAlreadyExistsException e){
                            if(e.getMessage().equalsIgnoreCase("same")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.alreadyJoin")));
                            if(e.getMessage().equalsIgnoreCase("other")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.alreadyInOtherArena")));
                        } catch (Exception e){
                            if(e.getMessage()!=null){
                                if(e.getMessage().equalsIgnoreCase("closed")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaClosed")));
                                if(e.getMessage().equalsIgnoreCase("playing")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaAlreadyPlaying")));
                                if(e.getMessage().equalsIgnoreCase("full")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaFull")));
                            } else {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("leave")) {
            if (sender.hasPermission("dac.leave")) {
                try{
                    ArenaManager.playerLeaveArena((Player) sender);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.leaveArena")));
                } catch (NoSuchElementException e){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.notInArena")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
            if (sender.hasPermission("dac.list")) {
                Set<String> arenas = ArenaManager.getArenas();
                if (arenas.size() == 0)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("listArenas.empty")));
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("listArenas.title")));
                    for (String s : arenas) {
                        if (ArenaManager.getArena(s).isOpen())
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f- &a" + s));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("block")) {
            if(ArenaManager.getPlayerArena((Player) sender)!=null){
                if (sender.hasPermission("dac.block")) {
                    new SelectBlockMenu(Main.getPlayerMenuUtil((Player) sender)).open();
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
                }
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("spectate")) {
            if (sender.hasPermission("dac.spectate")) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongSpectateCmd")));
                } else {
                    Arena a = ArenaManager.getArena(args[1]);
                    if (a == null){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                    } else{
                        try{
                            ArenaManager.playerSpectateArena((Player) sender, args[1]);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.spectateArena").replace("%arena%", args[1])));
                        } catch (NoSuchElementException e){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                        } catch (KeyAlreadyExistsException e){
                            if(e.getMessage().equalsIgnoreCase("spectator")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.alreadySpectatorOtherArena")));
                            if(e.getMessage().equalsIgnoreCase("inGame")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.alreadyInOtherArena")));
                            if(e.getMessage().equalsIgnoreCase("spectate")) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.alreadySpectator")));
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("event")) {
            if (sender.hasPermission("dac.event")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.devLock")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
            if (sender.hasPermission("dac.create")) {
                if (args.length < 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongCreateCmd")));
                else if (args.length > 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongCreateArenaName")));
                else {
                    try {
                        if (ArenaManager.createArena(args[1])) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("createArena.success").replace("%arena%", args[1])));
                            new ArenaEditionMenu(Main.getPlayerMenuUtil((Player) sender), ArenaManager.getArena(args[1])).open();
                        }
                    } catch (KeyAlreadyExistsException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("createArena.arenaAlreadyExists").replace("%arena%", args[1])));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("remove")) {
            if (sender.hasPermission("dac.remove")) {
                if (args.length < 2)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongRemoveCmd")));
                else
                    try {
                        if (ArenaManager.removeArena(args[1])) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("removeArena.success").replace("%arena%", args[1])));
                        }
                    } catch (NoSuchElementException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                    }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("edit")) {
            if (sender.hasPermission("dac.edit")) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditCmd")));
                } else if (args.length == 2) {
                    Arena arena = ArenaManager.getArena(args[1]);
                    if(arena==null){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " +Main.f.getString("arena.arenaUnknown")));
                    }
                    else if (sender instanceof Player) new ArenaEditionMenu(Main.getPlayerMenuUtil((Player) sender), arena).open();
                    else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.playerOnly")));
                } else if (args.length == 3) {
                    Arena arena = ArenaManager.getArena(args[1]);
                    if (args[2].equalsIgnoreCase("setlobby")) {
                        if (arena == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                        } else {
                            Player p = (Player) sender;
                            arena.setLobbyLocation(p.getLocation());
                            try {
                                ArenaManager.save(args[1], arena);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.successSetLobby")));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else if (args[2].equalsIgnoreCase("setdiving")) {
                        if (arena == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                        } else {
                            Player p = (Player) sender;
                            arena.setDivingLocation(p.getLocation());
                            try {
                                ArenaManager.save(args[1], arena);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.successSetDiving")));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else if (args[2].equalsIgnoreCase("setname")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetName")));
                    } else if (args[2].equalsIgnoreCase("setmin")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetMin")));
                    } else if (args[2].equalsIgnoreCase("setmax")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("wrongCommands.wrongEditSetMax")));
                    }
                } else if (args.length == 4) {
                    Arena arena = ArenaManager.getArena(args[1]);
                    if (args[2].equalsIgnoreCase("setname")) {
                        if (arena == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("arena.arenaUnknown")));
                        } else {
                            Player p = (Player) sender;
                            if (args[3].equalsIgnoreCase(args[1])) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.sameName")));
                                return true;
                            }
                            if (ArenaManager.getArena(args[3]) != null) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.arenaAlreadyExists")));
                                return true;
                            }
                            arena.setName(args[3]);
                            ArenaManager.renameArena(args[1], arena);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("editArena.newNameSet").replace("%name%", args[3])));
                        }
                    }
                } else {

                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("dac.reload")) {
                Config.reloadConfig();
                Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&', Main.f.getString("global.reloadConfig")));
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.reloadConfig")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
            }
        } else if (args.length > 0 && args[0].equalsIgnoreCase("version")) {
            if (sender.hasPermission("dac.version")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.version").replace("%version%", Main.getPlugin().getDescription().getVersion())));
                BaseComponent changeLogs = null;
                for (String note : ChangeLog.changeLog()) {
                    if (changeLogs == null)
                        changeLogs = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("global.changeLogHeader").replace("%version%", Main.getPlugin().getDescription().getVersion()) + "\n" + note + "\n"));
                    else
                        changeLogs.addExtra(new TextComponent(ChatColor.translateAlternateColorCodes('&', note + "\n")));
                }
                BaseComponent[] changeLog;
                TextComponent footer = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("global.changeLogFooter")));
                if (changeLogs != null) {
                    changeLog = new BaseComponent[]{changeLogs, footer};
                } else {
                    changeLog = new BaseComponent[]{new TextComponent("No changelog found"), footer};
                }
                TextComponent version = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("global.changelog")));
                version.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, changeLog));
                version.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/"));

                sender.spigot().sendMessage(version);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.noPermission")));
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
                TextComponent spectate = new TextComponent(ChatColor.translateAlternateColorCodes('&', Main.f.getString("menu.spectate")));
                spectate.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dac spectate"));
                sender.spigot().sendMessage(join);
                sender.spigot().sendMessage(leave);
                sender.spigot().sendMessage(list);
                sender.spigot().sendMessage(spectate);
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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.playerOnly")));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.f.getString("name") + " " + Main.f.getString("global.unknownCommand")));
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
