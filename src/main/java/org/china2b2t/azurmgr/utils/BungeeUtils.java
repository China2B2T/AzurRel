package org.china2b2t.azurmgr.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.china2b2t.azurmgr.Main;

public class BungeeUtils {
    public static boolean connect(String player, String server) {

        try {
            Player pl = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);

            out.writeUTF("ConnectOther");
            out.writeUTF(player);
            out.writeUTF(server); // 目标主机

            pl.sendPluginMessage(Main.instance, "BungeeCord", byteArray.toByteArray());

        } catch (Exception ex) {
            Main.instance.getLogger().warning("Cannot spawn \"" + player + "\" to \"" + server + "\".");
            return false;
        }

        return true;
    }
}
