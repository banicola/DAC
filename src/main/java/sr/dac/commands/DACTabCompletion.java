package sr.dac.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import sr.dac.configs.ArenaManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DACTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> cmds = new ArrayList<>(Arrays.asList("join", "leave", "list"));
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
            if(args[0].equalsIgnoreCase("join") || (args[0].equalsIgnoreCase("remove") && sender.hasPermission("dac.remove")) || (args[0].equalsIgnoreCase("edit") && sender.hasPermission("dac.edit"))){
                List<String> cmds = new ArrayList<>(ArenaManager.getArenas());
                List<String> cmdsFiltred = new ArrayList<>();
                if (args[1].equalsIgnoreCase("")) cmdsFiltred = cmds;
                else {
                    for (String s : cmds) {
                        if (s.startsWith(args[1], 0)) cmdsFiltred.add(s);
                    }
                }
                return cmdsFiltred;
            }
        }
        return new ArrayList<>();
    }
}
