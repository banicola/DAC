package sr.dac.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sr.dac.main.Config;
import sr.dac.main.Lang;
import sr.dac.main.Main;

public class CmdDAC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(command.getName().equalsIgnoreCase("dac") && args.length > 0 && args[0].contentEquals("reload")){
            if(sender.hasPermission("dac.reload")){
                Config.reloadConfig();
                Lang.reloadConfig();
                Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
                Bukkit.getPluginManager().enablePlugin(Main.getPlugin());
                Main.getPlugin().getLogger().info(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.reloadConfig")));
                if(sender instanceof Player){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("name")+" "+Main.f.getString("debug.reloadConfig")));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.f.getString("debug.noPermission")));
            }
            return true;
        }
        return false;
    }
}
