package org.china2b2t.azurmgr.config;

import org.china2b2t.azurmgr.Main;

public class Admin {
    public static boolean validate(String name, String key) {
        if(!Main.accConfig.isSet(name + ".key")) {
            return false;
        }
        return Main.accConfig.getBoolean(name + ".enabled") && key.equals(Main.accConfig.getString(name + ".key"));
    }
}
