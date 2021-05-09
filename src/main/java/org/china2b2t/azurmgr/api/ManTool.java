package org.china2b2t.azurmgr.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

public class ManTool {
    private static HashMap<JavaPlugin, ITool> mgrList;

    /**
     * Register a management tool
     * 
     * @param plug
     * @param mgr
     */
    public static void registerManager(final JavaPlugin plug, final ITool mgr) {
        mgrList.put(plug, mgr);
    }

    /**
     * Look up the management tool which registered to this plugin
     * 
     * @param plug
     * @return
     */
    public static ITool lookUpManager(final JavaPlugin plug) {
        if (!mgrList.containsKey(plug)) {
            return null;
        }
        return mgrList.get(plug);
    }

    /**
     * Pass the change
     * @param plugin
     * @param key
     * @param value
     * @return 0: ok | 1: not_same_type | 2: changed_nothing | 3: unknown_err
     */
    public static int passConfigModify(final String plugin, final String key, final Object value) {
        final Set<JavaPlugin> st = mgrList.keySet();
        JavaPlugin pl = null;
        for (final JavaPlugin plugin2 : st) {
            if(plugin2.getName().equals(plugin)) {
                pl = plugin2;
                break;
            }
        }

        final ITool tol = mgrList.get(pl);
        tol.setKey(key, value);
        return 0;
    }

    /**
     * Get Plugins List
     * 
     * @return Plugins List
     */
    public static List<String> getPlugins() {
        final ArrayList<String> plugins = new ArrayList<>();
        final Set<JavaPlugin> keyPlugins = mgrList.keySet();
        for (final JavaPlugin plugin : keyPlugins){
            plugins.add(plugin.getName());
        }
        return plugins;
    }

    /**
     * Get ITool instance from string
     * 
     * @param name
     * @return
     */
    public static ITool getIToolFromString(String name) {
        final Set<JavaPlugin> plugins = mgrList.keySet();
        for(JavaPlugin plugin : plugins) {
            if(plugin.getName() == name) {
                return mgrList.get(plugin);
            }
        }
        return null;
    }

    public static List<String> getKeysFromPlugin(String plugin) {
        final Set<JavaPlugin> st = mgrList.keySet();
        JavaPlugin pl = null;
        for (final JavaPlugin plugin2 : st) {
            if(plugin2.getName().equals(plugin)) {
                pl = plugin2;
                break;
            }
        }

        final ITool tol = mgrList.get(pl);
        return tol.getKeys();
    }
}
