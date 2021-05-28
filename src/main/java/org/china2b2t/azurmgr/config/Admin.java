package org.china2b2t.azurmgr.config;

import org.china2b2t.azurmgr.Main;

public class Admin {
    public static boolean validate(String name, String key) {
        return (Main.accConfig.isSet(name + ".key") &&
                Main.accConfig.getBoolean(name + ".enabled") &&
                key.equals(Main.roConfig.getString(name + ".key")));
    }
}
