package org.china2b2t.azurmgr.http.handlers.query;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.JSONObject;

import java.util.Set;

public class WhitelistQuery extends Query {
    public WhitelistQuery() {
        super(false);
    }

    @Override
    public StringBuilder getContent(JSONObject json) {
        StringBuilder outSb = new StringBuilder();

        outSb.append("[");
        Set<OfflinePlayer> whSet = Bukkit.getWhitelistedPlayers();

        for (var p : whSet) {
            outSb.append(p.getName() + ",");
        }

        outSb.deleteCharAt(outSb.length() - 1);
        outSb.append("]");

        return outSb;
    }
}
