package org.china2b2t.azurmgr.listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.china2b2t.azurmgr.Main;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        if(Main.instance.getConfig().getBoolean("disable-join-quit-message")) {
            e.setQuitMessage(null);
        }
        if(Main.instance.getConfig().getBoolean("temporary-permission")) {
            if(e.getPlayer().isOp()) {
                e.getPlayer().setOp(false);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Main.instance.getConfig().getBoolean("disable-join-quit-message")) {
            e.setJoinMessage(null);
        }
    }

    @EventHandler
    public void  asyncChat(AsyncPlayerChatEvent e) {
        if(e.getMessage().charAt(0) == '>') {
            e.setMessage(ChatColor.GREEN + e.getMessage());

        }
    }
}
