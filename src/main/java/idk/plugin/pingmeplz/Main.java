package idk.plugin.pingmeplz;

import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;

public class Main extends PluginBase implements Listener {

    private Config c;

    public void onEnable() {
        saveDefaultConfig();
        c = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMessage(PlayerChatEvent e) {
        if (!e.isCancelled() && e.getMessage().contains("@")) {
            if (e.getMessage().contains("@here") && c.getBoolean("allowHerePing")) {
                PlaySoundPacket pk = new PlaySoundPacket();
                pk.name = c.getString("mentionSound");
                pk.volume = 1;
                pk.pitch = 1;

                for (Player p : getServer().getOnlinePlayers().values()) {
                    pk.x = (int) p.x;
                    pk.y = (int) p.y;
                    pk.z = (int) p.z;
                    p.dataPacket(pk);
                }
            }

            for (Player p : getServer().getOnlinePlayers().values()) {
                if (e.getMessage().contains("@" + p.getName())) {
                    PlaySoundPacket pk = new PlaySoundPacket();
                    pk.name = c.getString("mentionSound");
                    pk.volume = 1;
                    pk.pitch = 1;
                    pk.x = (int) p.x;
                    pk.y = (int) p.y;
                    pk.z = (int) p.z;
                    p.dataPacket(pk);
                }
            }
        }
    }
}
