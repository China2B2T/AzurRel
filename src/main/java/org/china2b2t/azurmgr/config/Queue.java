package org.china2b2t.azurmgr.config;

import org.china2b2t.azurmgr.Main;

public class Queue {
    /**
     * Check if the user has prior queue permission
     * 
     * @param uuid
     * @return
     */
    public static boolean isPrior(String uuid) {
        if (!Main.priorConfig.isSet(uuid)) {
            return false;
        }
        if (Main.priorConfig.isLong(uuid)) {
            if (Main.priorConfig.getLong(uuid) >= System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Renew one's prior queue permission
     * 
     * @param uuid
     * @param period
     */
    public static void promotePrior(String uuid, long period) {
        if (!Main.priorConfig.isSet(uuid)) {
            return;
        }
        if (Main.priorConfig.isLong(uuid)) {
            long expire = Main.priorConfig.getLong(uuid);
            expire += period;
            Main.priorConfig.set(uuid, expire);
        } else {
            Main.priorConfig.set(uuid, (System.currentTimeMillis() + period));
        }
    }
}
