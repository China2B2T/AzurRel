package org.china2b2t.azurmgr;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.command.CommandGenkey;
import org.china2b2t.azurmgr.command.CommandPluginmgr;

public class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        getLogger().info(ChatColor.AQUA + "China2B2T > Welcome back!");
    }

    @Override
    public void onEnable() {
        getServer().getPluginCommand("pluginmgr").setExecutor(new CommandPluginmgr());
        getServer().getPluginCommand("genkey").setExecutor(new CommandGenkey());
        saveDefaultConfig();
    }

    public JavaPlugin getInstance() {
        return this;
    }
}