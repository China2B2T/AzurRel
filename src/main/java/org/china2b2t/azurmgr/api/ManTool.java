package org.china2b2t.azurmgr.api;

import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public class ManTool {
    private HashMap<Plugin, MgrTool> MgrList;

    /**
     * Register a management tool
     * @param plug
     * @param mgr
     */
    public void registerManager(Plugin plug, MgrTool mgr) {
        MgrList.put(plug, mgr);
    }

    /**
     * Look up the management tool which registered to this plugin
     * @param plug
     * @return
     */
    public MgrTool lookUpManager(Plugin plug) {
        if(!MgrList.containsKey(plug)) {
            return null;
        }
        return MgrList.get(plug);
    }
}
