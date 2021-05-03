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
        // Cold down
        String path = "admin-info." + playerName;
        if(new Main().getConfig().isString(path + ".key") && new Main().getConfig().isBoolean(path + ".enabled")) {
            if(key == new Main().getConfig().getString(path + ".key")) {
                return true;
            }
            return false;
        }
        return false;
    }
}
