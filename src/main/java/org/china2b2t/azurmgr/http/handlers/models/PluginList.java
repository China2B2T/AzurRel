package org.china2b2t.azurmgr.http.handlers.models;

import java.util.Arrays;
import java.util.List;

public class PluginList {
    public final int status;
    public List<String> list = Arrays.asList("undefined");

    /**
     * Constructor for PluginList
     * @param status
     * @param plugins
     */
    public PluginList(int status, List<String> plugins) {
        this.status = status;
        this.list = plugins;
    }
}