package fr.mrsuricate.zekaria.TimeIsMoney;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class TimeisMoney implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String p = e.getPlayer().getName();
        if(Main.getInstance().getTimeIsMoneyConfig().contains(p)){
            Main.getInstance().onlinePlayer.put(p,0);
            new runnable(p).runTaskTimer(Main.getInstance(),0L, 20L);
        } else {
            int a = 0;
            Main.getInstance().getTimeIsMoneyConfig().set(p, a);
            Main.getInstance().onlinePlayer.put(p,0);
            try{
                Main.getInstance().getTimeIsMoneyConfig().save(Main.getInstance().TimeIsMoney);
            } catch (IOException er){
                er.printStackTrace();
            }
            new runnable(p).runTaskTimer(Main.getInstance(),0L, 20L);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        String p = e.getPlayer().getName();
        Main.getInstance().onlinePlayer.remove(p);
    }


}
