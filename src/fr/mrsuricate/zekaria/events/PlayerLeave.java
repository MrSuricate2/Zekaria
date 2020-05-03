package fr.mrsuricate.zekaria.events;

import fr.mrsuricate.zekaria.Main;
import fr.mrsuricate.zekaria.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onQUit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(PlayerManager.isInModerationMod(p)){
            PlayerManager.getFromPlayer(p).destroy();
        }
    }
}
