package fr.mrsuricate.zekaria.TimeIsMoney;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TimeisMoney implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer();
    }

}
