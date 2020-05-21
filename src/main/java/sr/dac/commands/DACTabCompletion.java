package sr.dac.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DACTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        if(args.length == 1){
            List<String> cmds = new ArrayList<>(Arrays.asList("join","leave","list"));
            if(sender.hasPermission("event")) cmds.add("event");
            if(sender.hasPermission("create")) cmds.add("create");
            if(sender.hasPermission("delete")) cmds.add("delete");
            if(sender.hasPermission("version")) cmds.add("version");
            if(sender.hasPermission("reload")) cmds.add("reload");
            List<String> cmdsFiltred = new ArrayList<>();
            if (args[0].equalsIgnoreCase("")) cmdsFiltred = cmds;
            else {
                for(String s : cmds){
                    if(s.startsWith(args[0],0)) cmdsFiltred.add(s);
                }
            }
            return cmdsFiltred;
        }
        return new ArrayList<>();
    }
}
