package fr.mrsuricate.zekaria.TimeIsMoney;

import fr.mrsuricate.zekaria.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class TimeisMoney implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String p = e.getPlayer().getDisplayName();
        if(Main.getInstance().getTimeIsMoneyConfig().contains(p)){
            new runnable(p).runTaskTimer(Main.getInstance(),0L, 20L);
        } else {
            short a = 0;
            Main.getInstance().getTimeIsMoneyConfig().set(p, a);
            try{
                Main.getInstance().getTimeIsMoneyConfig().save(Main.getInstance().TimeIsMoney);
            } catch (IOException er){
                er.printStackTrace();
            }
            new runnable(p).runTaskTimer(Main.getInstance(),0L, 20L);
        }
    }
}
