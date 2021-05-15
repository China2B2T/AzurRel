package org.china2b2t.azurmgr.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.china2b2t.azurmgr.Main;
import org.china2b2t.azurmgr.utils.BungeeUtils;

public class Channel implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase("queue:event")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("enter")) {
            String playerName = in.readUTF();
            String thisServer = in.readUTF();
            if(Main.instance.getConfig().isSet("prior_val." + playerName) && Main.instance.getConfig().getLong("prior_val." + playerName) <= System.currentTimeMillis()) {
                BungeeUtils.connect(playerName, thisServer);
            }
        }
    }
}
