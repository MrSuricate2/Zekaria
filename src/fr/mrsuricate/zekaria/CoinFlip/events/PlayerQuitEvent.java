package fr.mrsuricate.zekaria.CoinFlip.events;

import fr.mrsuricate.zekaria.CoinFlip.utilz.CoinEntry;
import fr.mrsuricate.zekaria.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {
    @EventHandler
    public void onQuitEvent(org.bukkit.event.player.PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Main.getInstance().getCoins().inEntry(p)) {
            Main.getEconomy().depositPlayer((OfflinePlayer)p, ((CoinEntry)Main.getInstance().getCoins().getEntry().get(p)).getAmount());
            Main.getInstance().getCoins().removeEntry(p);
            Main.getInstance().getMenuManager().updateInv();
        }
    }
}
