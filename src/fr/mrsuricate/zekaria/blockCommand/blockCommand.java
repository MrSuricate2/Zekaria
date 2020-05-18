package fr.mrsuricate.zekaria.blockCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class blockCommand implements Listener {

    @EventHandler
    public void commandDisable(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String pname = e.getPlayer().getDisplayName();
        String msg = e.getMessage();
        if(!p.isOp()){
            if(msg.equalsIgnoreCase("/pl") || msg.equalsIgnoreCase("/plugins") || msg.equalsIgnoreCase("/ver") || msg.equalsIgnoreCase("/version") || msg.equalsIgnoreCase("/?") || msg.equalsIgnoreCase("/bukkit:help") || msg.equalsIgnoreCase("/help") || msg.equalsIgnoreCase("/about") || msg.equalsIgnoreCase("/bukkit:?") || msg.equalsIgnoreCase("/bukkit:about") || msg.equalsIgnoreCase("/bukkit:pl") || msg.equalsIgnoreCase("/bukkit:plugins") || msg.equalsIgnoreCase("/bukkit:ver") || msg.equalsIgnoreCase("/bukkit:version") || msg.equalsIgnoreCase("//calc") || msg.equalsIgnoreCase("//eval") || msg.equalsIgnoreCase("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}") || msg.equalsIgnoreCase("//eval for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}")  || msg.equalsIgnoreCase("/mine")){
                e.setCancelled(true);
                p.sendMessage("C'est pas bien de vouloir fuiner !");
            }
            if(msg.equalsIgnoreCase("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}") || msg.equalsIgnoreCase("//eval for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}") || msg.equalsIgnoreCase("//calc") || msg.equalsIgnoreCase("//eval")){
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(players.hasPermission("mod.receive.use")){
                        players.sendMessage("");
                        players.sendMessage("§4§lAttention " +pname+ " à essayer de faire crash le serveur !");
                        players.sendMessage("");
                    }
                }
            }
            if(p.getWorld().getName().equals("world_nether")){
                if(msg.equalsIgnoreCase("/rtp")){
                    e.setCancelled(true);
                    p.sendMessage("§cImpossible de random TP dans le nether !");
                }
            }
            if(msg.equalsIgnoreCase("/mine")){
                e.setCancelled(true);
            }
        }
    }

}
