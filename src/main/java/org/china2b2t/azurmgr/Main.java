package org.china2b2t.azurmgr;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.command.CommandGenkey;
import org.china2b2t.azurmgr.command.CommandPluginmgr;
import org.china2b2t.azurmgr.http.Server;
import org.china2b2t.azurmgr.listener.Channel;

// LyoqCiAqIEkgV0lMTCBBTFdBWVMgVE8gQkxBTUUgTVlTRUxGCiAqIFdIWSBBTSBJIFNPIFNFTEZJU0gKICogV0hZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZWVlZCiAqLw==

public class Main extends JavaPlugin {
    public static JavaPlugin instance = null;

    @Override
    public void onLoad() {
        getLogger().info(ChatColor.AQUA + "China2B2T > Welcome back!");
        instance = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginCommand("pluginmgr").setExecutor(new CommandPluginmgr());
        getServer().getPluginCommand("genkey").setExecutor(new CommandGenkey());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "QueueMgr");

        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(this, "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
        // Should I get current list from BungeeCord?
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Channel());
        saveDefaultConfig();
        int apiPort = this.getConfig().getInt("port");
        boolean isApiEnabled = Server.startServer(apiPort);
        if(!isApiEnabled) {
            this.getLogger().log(Level.SEVERE, "Cannot enable API server!");
        }
    }

    public JavaPlugin getInstance() {
        return this;
    }
}