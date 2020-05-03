package fr.mrsuricate.zekaria.CoinFlip.utilz;

import java.util.HashMap;
import org.bukkit.entity.Player;

public class CoinManager {
    private HashMap<Player, CoinEntry> coins = new HashMap<>();

    public HashMap<Player, CoinEntry> getEntry() {
        return this.coins;
    }

    public void createEntry(Player p, double amount, boolean side) {
        CoinEntry entry = new CoinEntry(amount, side);
        this.coins.put(p, entry);
    }

    public void removeEntry(Player p) {
        this.coins.remove(p);
    }

    public boolean inEntry(Player p) {
        return this.coins.containsKey(p);
    }

    public String getSideConverted(Player p) {
        if (((CoinEntry)this.coins.get(p)).getSide())
            return "face";
        return "pile";
    }

    public boolean getBooleanConverted(String side) {
        if (side.equalsIgnoreCase("face") || side.equalsIgnoreCase("faces"))
            return true;
        return false;
    }
}
