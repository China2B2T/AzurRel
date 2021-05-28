package org.china2b2t.azurmgr.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.china2b2t.azurmgr.KeyGen;
import org.china2b2t.azurmgr.Main;

public class CommandGenkey implements CommandExecutor {
    private String prefix = ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String args[]) {
        // Should use email to display gravatar?
        if(!sender.hasPermission("china2b2t.admin")) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(");
            return false;
        }

        if(args.length != 0) {
            sender.sendMessage(prefix + "Cannot fetch arguments! Expected 0 but called " + args.length);
            return false;
        }
        
        String key = KeyGen.genKey();
        String name = sender.getName();
        
        if(!Main.accConfig.isBoolean(name + ".enabled")) {
            Main.accConfig.set(name + ".enabled", true);
        }

        Main.accConfig.set(name + ".key", key);
        sender.sendMessage(prefix + "Generated key for " + name + " successfully: " + key);

        Main.save();
        return true;
    }
}
