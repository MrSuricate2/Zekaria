package fr.mrsuricate.zekaria.CoinFlip.utilz;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class StatsManager extends StatsEntry {
    private HashMap<Player, StatsEntry> stats = new HashMap<>();

    private StatsEntry entry;

    public boolean inEntry(Player p) {
        return this.stats.containsKey(p);
    }

    public void createEntry(Player p) {
        this.entry = this.stats.getOrDefault(p, new StatsManager());
        this.stats.put(p, this.entry);
    }

    public int getWinStats(Player p) {
        return ((StatsEntry)this.stats.get(p)).getWinStats();
    }

    public int getLoseStats(Player p) {
        return ((StatsEntry)this.stats.get(p)).getLoseStats();
    }

    public void incrementWin(Player p) {
        this.entry.incrementWin();
        this.stats.put(p, this.entry);
    }

    public void incrementLose(Player p) {
        this.entry.incrementLose();
        this.stats.put(p, this.entry);
    }

    public Player getWinner(Player first, Player second) {
        if (Math.random() > 0.5D) {
            incrementWin(first);
            return first;
        }
        return second;
    }

    public void toString(Player p) {
        ArrayList<String> string = new ArrayList<>();
        p.sendMessage(Chat.color("&e&l&n" + p.getName()) + " &e&l&nStats");
        p.sendMessage(Chat.color(""));
        p.sendMessage(Chat.color("&6&l* &eVictoire quotidienne: &f" + getWinStats(p)));
        p.sendMessage(Chat.color("&6&l* &ePerdu quotidien: &f" + getLoseStats(p)));
        p.sendMessage(Chat.color(""));
        p.sendMessage(Chat.color("&7&o(( Les statistiques sont rtout les jours!! ))"));
    }
}
