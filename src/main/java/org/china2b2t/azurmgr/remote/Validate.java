package org.china2b2t.azurmgr.remote;

import org.china2b2t.azurmgr.Main;

public class Validate {
    public static boolean validate(String name, String key) {
        // Cold down
        String path = "api-access." + name;
        return (Main.instance.getConfig().isSet(path + ".key") &&
                Main.instance.getConfig().getBoolean(path + ".enabled") &&
                key.equals(Main.instance.getConfig().getString(path + ".key")));
    }
}
