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
            return true;
        }
        if(args.length != 0) {
            sender.sendMessage(prefix + "Too many arguments! Expected 0 but called " + args.length);
            return true;
        }
        if(!Main.getPlugin(Main.class).getConfig().isBoolean("admin-info." + sender.getName() + ".enabled")) {
            Main.getPlugin(Main.class).getConfig().set("admin-info." + sender.getName() + ".enabled", true);
        }
        Main.getPlugin(Main.class).getConfig().set("admin-info." + sender.getName() + ".key", KeyGen.genKey());
        return true;
    }
}
