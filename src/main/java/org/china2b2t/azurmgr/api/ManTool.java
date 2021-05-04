package org.china2b2t.azurmgr.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.plugin.Plugin;

public class ManTool {
    private static HashMap<Plugin, MgrTool> MgrList;

    /**
     * Register a management tool
     * 
     * @param plug
     * @param mgr
     */
    public void registerManager(Plugin plug, MgrTool mgr) {
        MgrList.put(plug, mgr);
    }

    /**
     * Look up the management tool which registered to this plugin
     * 
     * @param plug
     * @return
     */
    public MgrTool lookUpManager(Plugin plug) {
        if (!MgrList.containsKey(plug)) {
            return null;
        }
        return MgrList.get(plug);
    }

    /**
     * Pass the change
     * @param plugin
     * @param key
     * @param value
     * @return 0: ok | 1: not_same_type | 2: changed_nothing | 3: unknown_err
     */
    public static int passConfigModify(String plugin, String key, Object value) {
        MgrTool tol = MgrList.get(plugin);
        tol.setKey(key, value);
        return 0;
    }

    /**
     * Get Plugins List
     * 
     * @return Plugins List
     */
    public static List<String> getPlugins() {
        ArrayList<String> plugins = new ArrayList<>();
        Set<Plugin> keyPlugins = MgrList.keySet();
        for (Plugin plugin : keyPlugins){
            plugins.add(plugin.getName());
        }
        return plugins;
    }
}
