package fr.mrsuricate.zekaria.CoinFlip.utilz;

import fr.mrsuricate.zekaria.Main;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class BroadcastManager {
    private HashMap<Player, Boolean> broadcast = new HashMap<>();

    public boolean inEntry(Player p) {
        return this.broadcast.containsKey(p);
    }

    public void createEntry(Player p) {
        this.broadcast.put(p, Boolean.valueOf(true));
    }

    public void removeEntry(Player p) {
        this.broadcast.remove(p);
    }

    public String toString(Player p) {
        if (inEntry(p))
            return Main.getInstance().getConfig().getString("Messages.ToggleON");
        return Main.getInstance().getConfig().getString("Messages.ToggleOFF");
    }
}
