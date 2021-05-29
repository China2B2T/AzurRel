package org.china2b2t.azurmgr;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.command.CommandAzurload;
import org.china2b2t.azurmgr.command.CommandGenkey;
import org.china2b2t.azurmgr.command.CommandPluginmgr;
import org.china2b2t.azurmgr.http.Server;

public class Main extends JavaPlugin {
    public static JavaPlugin instance = null;
    public static FileConfiguration priorConfig;
    public static FileConfiguration roConfig;
    public static FileConfiguration accConfig;

    /**
     * Load a configuration from file
     * 
     * @param plugin
     * @param fileName
     * @return
     */
    public static FileConfiguration load(JavaPlugin plugin,String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    
    /**
     * Save default configuration from jar
     * 
     * @param plugin
     * @param fileName
     */
    public static void saveDefaultConfig(JavaPlugin plugin,String fileName) {
        plugin.saveResource(fileName, false);
    }

    @Override
    public void onLoad() {
        getLogger().info(ChatColor.AQUA + "China2B2T > Welcome back!");
        instance = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginCommand("pluginmgr").setExecutor(new CommandPluginmgr());
        getServer().getPluginCommand("genkey").setExecutor(new CommandGenkey());
        getServer().getPluginCommand("azurload").setExecutor(new CommandAzurload());
        // getServer().getMessenger().registerOutgoingPluginChannel(this, "QueueMgr");
        
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(this, "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
        // Should I get current list from BungeeCord?
        // getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        File defConfig = new File(this.getDataFolder(), "config.yml");
        if(!defConfig.exists()) {
            saveDefaultConfig();
        }

        File queueConfig = new File(this.getDataFolder(), "prior.yml");
        if(!queueConfig.exists()) {
            saveDefaultConfig(this, "prior.yml");
        }

        File accessConfig = new File(this.getDataFolder(), "access.yml");
        if(!accessConfig.exists()) {
            saveDefaultConfig(this, "access.yml");
        }
        
        int apiPort = this.getConfig().getInt("port");
        boolean isApiEnabled = Server.startServer(apiPort);
        if(!isApiEnabled) {
            this.getLogger().log(Level.SEVERE, "Cannot enable API server!");
        }

        roConfig = this.getConfig();
        priorConfig = load(this, "prior.yml");
        accConfig = load(this, "access.yml");

        // SuperHuang233 rejected
        // new Timed();
    }

    public JavaPlugin getInstance() {
        return this;
    }

    public static void reload() {
        instance.reloadConfig();
    }

    /**
     * Save configurations
     */
    public static void save() {
        try {
            priorConfig.save("prior.yml");
            accConfig.save("access.yml");
        } catch(IOException e) {
            Main.instance.getLogger().log(Level.SEVERE, "Could not save configurations");
            e.printStackTrace();
        }

    }
}