package org.china2b2t.azurmgr.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.china2b2t.azurmgr.Main;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        if(Main.instance.getConfig().getBoolean("temporary-permission")) {
            if(e.getPlayer().isOp()) {
                e.getPlayer().setOp(false);
            }
        }
    }
}
