package org.china2b2t.azurmgr.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandGenkey implements CommandExecutor {
    private String prefix = ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String args[]) {
        if(!sender.hasPermission("china2b2t.admin")) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(");
            return true;
        }
        if(args.length != 0) {
            sender.sendMessage(prefix + "Too many arguments! Expected 0 but called " + args.length);
            return true;
        }
        return true;
    }
}
