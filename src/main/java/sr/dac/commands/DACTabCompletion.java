package sr.dac.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import sr.dac.configs.ArenaManager;
import sr.dac.main.Arena;

import java.util.*;

public class DACTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> cmds = new ArrayList<>(Arrays.asList("join", "leave", "list", "spectate", "block"));
            if (sender.hasPermission("dac.event")) cmds.add("event");
            if (sender.hasPermission("dac.create")) cmds.add("create");
            if (sender.hasPermission("dac.remove")) cmds.add("remove");
            if (sender.hasPermission("dac.edit")) cmds.add("edit");
            if (sender.hasPermission("dac.version")) cmds.add("version");
            if (sender.hasPermission("dac.reload")) cmds.add("reload");
            List<String> cmdsFiltred = new ArrayList<>();
            if (args[0].equalsIgnoreCase("")) cmdsFiltred = cmds;
            else {
                for (String s : cmds) {
                    if (s.startsWith(args[0], 0)) cmdsFiltred.add(s);
                }
            }
            return cmdsFiltred;
        }
        if(args.length == 2){
            if((args[0].equalsIgnoreCase("join")&& sender.hasPermission("dac.join")) || (args[0].equalsIgnoreCase("spectate")&& sender.hasPermission("dac.spectate")) || (args[0].equalsIgnoreCase("remove") && sender.hasPermission("dac.remove")) || (args[0].equalsIgnoreCase("edit") && sender.hasPermission("dac.edit"))){
                Set<String> arenas = ArenaManager.getArenas();
                Set<String> available = new HashSet();
                for(String a : arenas){
                    if(ArenaManager.getArena(a).isOpen()) available.add(a);
                }
                List<String> cmdsFiltred = new ArrayList<>();
                if (args[1].equalsIgnoreCase("")) cmdsFiltred = new ArrayList<>(available);
                else {
                    for (String s : available) {
                        if (s.startsWith(args[1], 0)) cmdsFiltred.add(s);
                    }
                }
                return cmdsFiltred;
            }
        }
        if(args.length == 3){
            if((args[0].equalsIgnoreCase("edit") && sender.hasPermission("dac.edit"))){
                List<String> cmds = new ArrayList<>(Arrays.asList("setname", "setdiving", "setlobby", "setmin", "setmax"));
                List<String> cmdsFiltred = new ArrayList<>();
                if (args[2].equalsIgnoreCase("")) cmdsFiltred = cmds;
                else {
                    for (String s : cmds) {
                        if (s.startsWith(args[2], 0)) cmdsFiltred.add(s);
                    }
                }
                return cmdsFiltred;
            }
        }
        return new ArrayList<>();
    }
}
