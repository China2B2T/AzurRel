package org.china2b2t.azurmgr.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;

public class CommandKill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ((Damageable)sender).damage(32767.0f);
        return true;
    }
}
