package fr.mrsuricate.zekaria.blockCommand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class blockCommand implements Listener {

    @EventHandler
    public void commandDisable(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if(!p.isOp()){
            if(msg.equalsIgnoreCase("/pl") || msg.equalsIgnoreCase("/plugins") || msg.equalsIgnoreCase("/ver") || msg.equalsIgnoreCase("/version") || msg.equalsIgnoreCase("/?") || msg.equalsIgnoreCase("/bukkit:help") || msg.equalsIgnoreCase("/help") || msg.equalsIgnoreCase("/about") || msg.equalsIgnoreCase("/bukkit:?") || msg.equalsIgnoreCase("/bukkit:about") || msg.equalsIgnoreCase("/bukkit:pl") || msg.equalsIgnoreCase("/bukkit:plugins") || msg.equalsIgnoreCase("/bukkit:ver") || msg.equalsIgnoreCase("/bukkit:version")){
                e.setCancelled(true);
                p.sendMessage("C'est pas bien de vouloir fuiner !");
            }
        }
    }

}
