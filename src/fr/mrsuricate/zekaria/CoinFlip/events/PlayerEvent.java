package fr.mrsuricate.zekaria.CoinFlip.events;

import fr.mrsuricate.zekaria.CoinFlip.utilz.CoinEntry;
import fr.mrsuricate.zekaria.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerEvent implements Listener {
    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().getCoins().inEntry(p)) {
            Main.getEconomy().depositPlayer(p, Main.getInstance().getCoins().getEntry().get(p).getAmount());
            Main.getInstance().getCoins().removeEntry(p);
            Main.getInstance().getMenuManager().updateInv();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!Main.getInstance().getcf().contains(p.getName())){
            Main.getInstance().getcf().set(p.getName()+".win",0);
            Main.getInstance().getcf().set(p.getName()+".lose",0);
            try {
                Main.getInstance().getcf().save(Main.getInstance().cf);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
