package org.china2b2t.azurmgr.remote;

import org.china2b2t.azurmgr.Main;

public class Validate {
    public static boolean validate(String name, String key) {
        // Cold down
        String path = "admin-info." + name;
        return (new Main().getConfig().isString(path + ".key") &&
                new Main().getConfig().isBoolean(path + ".enabled") &&
                key.equals(new Main().getConfig().getString(path + ".key")));
    }
}
