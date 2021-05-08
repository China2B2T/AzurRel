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
        if(!sender.hasPermission("china2b2t.admin")) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(");
            return false;
        }

        if(args.length != 1) {
            sender.sendMessage(prefix + "Cannot fetch arguments! Expected 1 but called " + args.length);
            return false;
        }
        
        String key = KeyGen.genKey();
        
        if(!new Main().getConfig().isBoolean("api-access." + args[0] + ".enabled")) {
            new Main().getConfig().set("api-access." + args[0] + ".enabled", true);
        }

        new Main().getConfig().set("api-access." + args[0] + ".key", key);
        sender.sendMessage(prefix + "Generated key for " + args[0] + " successfully: " + key);
        return true;
    }
}
