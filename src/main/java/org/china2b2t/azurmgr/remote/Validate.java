package org.china2b2t.azurmgr.remote;

import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.azurmgr.Main;

public class Validate {
    public JavaPlugin ins;

    public void init() {
        // TODO init
        ins = new Main().getInstance();
    }

    public static boolean validate(String playerName, String key) {
        if(Main.getPlugin(Main.class).getConfig().isString("admin-info." + playerName + ".key") && Main.getPlugin(Main.class).getConfig().isBoolean("admin-info." + playerName + ".enabled")) {
            if(key == Main.getPlugin(Main.class).getConfig().getString("admin-info." + playerName + ".key")) {
                return true;
            }
            return false;
        }
        return false;
    }
}
