package fr.mrsuricate.zekaria.ChatSide;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(Main.chatlock && !e.getPlayer().hasPermission("cl.bypass")){
            e.setCancelled(true);
            Bukkit.broadcastMessage("§8[§4Lock Chat§8] §6Le chat à été désactiver");
        }
    }
}
